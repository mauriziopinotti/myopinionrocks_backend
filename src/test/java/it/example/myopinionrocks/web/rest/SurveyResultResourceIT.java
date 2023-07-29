package it.example.myopinionrocks.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.example.myopinionrocks.IntegrationTest;
import it.example.myopinionrocks.domain.SurveyResult;
import it.example.myopinionrocks.repository.SurveyResultRepository;
import it.example.myopinionrocks.service.dto.SurveyResultDTO;
import it.example.myopinionrocks.service.mapper.SurveyResultMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SurveyResultResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SurveyResultResourceIT {

    private static final Instant DEFAULT_DATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/survey-results";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SurveyResultRepository surveyResultRepository;

    @Autowired
    private SurveyResultMapper surveyResultMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSurveyResultMockMvc;

    private SurveyResult surveyResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SurveyResult createEntity(EntityManager em) {
        SurveyResult surveyResult = new SurveyResult().datetime(DEFAULT_DATETIME);
        return surveyResult;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SurveyResult createUpdatedEntity(EntityManager em) {
        SurveyResult surveyResult = new SurveyResult().datetime(UPDATED_DATETIME);
        return surveyResult;
    }

    @BeforeEach
    public void initTest() {
        surveyResult = createEntity(em);
    }

    @Test
    @Transactional
    void createSurveyResult() throws Exception {
        int databaseSizeBeforeCreate = surveyResultRepository.findAll().size();
        // Create the SurveyResult
        SurveyResultDTO surveyResultDTO = surveyResultMapper.toDto(surveyResult);
        restSurveyResultMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyResultDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeCreate + 1);
        SurveyResult testSurveyResult = surveyResultList.get(surveyResultList.size() - 1);
        assertThat(testSurveyResult.getDatetime()).isEqualTo(DEFAULT_DATETIME);
    }

    @Test
    @Transactional
    void createSurveyResultWithExistingId() throws Exception {
        // Create the SurveyResult with an existing ID
        surveyResult.setId(1L);
        SurveyResultDTO surveyResultDTO = surveyResultMapper.toDto(surveyResult);

        int databaseSizeBeforeCreate = surveyResultRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSurveyResultMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDatetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyResultRepository.findAll().size();
        // set the field null
        surveyResult.setDatetime(null);

        // Create the SurveyResult, which fails.
        SurveyResultDTO surveyResultDTO = surveyResultMapper.toDto(surveyResult);

        restSurveyResultMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyResultDTO))
            )
            .andExpect(status().isBadRequest());

        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSurveyResults() throws Exception {
        // Initialize the database
        surveyResultRepository.saveAndFlush(surveyResult);

        // Get all the surveyResultList
        restSurveyResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(surveyResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].datetime").value(hasItem(DEFAULT_DATETIME.toString())));
    }

    @Test
    @Transactional
    void getSurveyResult() throws Exception {
        // Initialize the database
        surveyResultRepository.saveAndFlush(surveyResult);

        // Get the surveyResult
        restSurveyResultMockMvc
            .perform(get(ENTITY_API_URL_ID, surveyResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(surveyResult.getId().intValue()))
            .andExpect(jsonPath("$.datetime").value(DEFAULT_DATETIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSurveyResult() throws Exception {
        // Get the surveyResult
        restSurveyResultMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSurveyResult() throws Exception {
        // Initialize the database
        surveyResultRepository.saveAndFlush(surveyResult);

        int databaseSizeBeforeUpdate = surveyResultRepository.findAll().size();

        // Update the surveyResult
        SurveyResult updatedSurveyResult = surveyResultRepository.findById(surveyResult.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSurveyResult are not directly saved in db
        em.detach(updatedSurveyResult);
        updatedSurveyResult.datetime(UPDATED_DATETIME);
        SurveyResultDTO surveyResultDTO = surveyResultMapper.toDto(updatedSurveyResult);

        restSurveyResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, surveyResultDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyResultDTO))
            )
            .andExpect(status().isOk());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeUpdate);
        SurveyResult testSurveyResult = surveyResultList.get(surveyResultList.size() - 1);
        assertThat(testSurveyResult.getDatetime()).isEqualTo(UPDATED_DATETIME);
    }

    @Test
    @Transactional
    void putNonExistingSurveyResult() throws Exception {
        int databaseSizeBeforeUpdate = surveyResultRepository.findAll().size();
        surveyResult.setId(count.incrementAndGet());

        // Create the SurveyResult
        SurveyResultDTO surveyResultDTO = surveyResultMapper.toDto(surveyResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, surveyResultDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSurveyResult() throws Exception {
        int databaseSizeBeforeUpdate = surveyResultRepository.findAll().size();
        surveyResult.setId(count.incrementAndGet());

        // Create the SurveyResult
        SurveyResultDTO surveyResultDTO = surveyResultMapper.toDto(surveyResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSurveyResult() throws Exception {
        int databaseSizeBeforeUpdate = surveyResultRepository.findAll().size();
        surveyResult.setId(count.incrementAndGet());

        // Create the SurveyResult
        SurveyResultDTO surveyResultDTO = surveyResultMapper.toDto(surveyResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyResultMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyResultDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSurveyResultWithPatch() throws Exception {
        // Initialize the database
        surveyResultRepository.saveAndFlush(surveyResult);

        int databaseSizeBeforeUpdate = surveyResultRepository.findAll().size();

        // Update the surveyResult using partial update
        SurveyResult partialUpdatedSurveyResult = new SurveyResult();
        partialUpdatedSurveyResult.setId(surveyResult.getId());

        restSurveyResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurveyResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurveyResult))
            )
            .andExpect(status().isOk());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeUpdate);
        SurveyResult testSurveyResult = surveyResultList.get(surveyResultList.size() - 1);
        assertThat(testSurveyResult.getDatetime()).isEqualTo(DEFAULT_DATETIME);
    }

    @Test
    @Transactional
    void fullUpdateSurveyResultWithPatch() throws Exception {
        // Initialize the database
        surveyResultRepository.saveAndFlush(surveyResult);

        int databaseSizeBeforeUpdate = surveyResultRepository.findAll().size();

        // Update the surveyResult using partial update
        SurveyResult partialUpdatedSurveyResult = new SurveyResult();
        partialUpdatedSurveyResult.setId(surveyResult.getId());

        partialUpdatedSurveyResult.datetime(UPDATED_DATETIME);

        restSurveyResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurveyResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurveyResult))
            )
            .andExpect(status().isOk());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeUpdate);
        SurveyResult testSurveyResult = surveyResultList.get(surveyResultList.size() - 1);
        assertThat(testSurveyResult.getDatetime()).isEqualTo(UPDATED_DATETIME);
    }

    @Test
    @Transactional
    void patchNonExistingSurveyResult() throws Exception {
        int databaseSizeBeforeUpdate = surveyResultRepository.findAll().size();
        surveyResult.setId(count.incrementAndGet());

        // Create the SurveyResult
        SurveyResultDTO surveyResultDTO = surveyResultMapper.toDto(surveyResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, surveyResultDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveyResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSurveyResult() throws Exception {
        int databaseSizeBeforeUpdate = surveyResultRepository.findAll().size();
        surveyResult.setId(count.incrementAndGet());

        // Create the SurveyResult
        SurveyResultDTO surveyResultDTO = surveyResultMapper.toDto(surveyResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveyResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSurveyResult() throws Exception {
        int databaseSizeBeforeUpdate = surveyResultRepository.findAll().size();
        surveyResult.setId(count.incrementAndGet());

        // Create the SurveyResult
        SurveyResultDTO surveyResultDTO = surveyResultMapper.toDto(surveyResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyResultMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveyResultDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSurveyResult() throws Exception {
        // Initialize the database
        surveyResultRepository.saveAndFlush(surveyResult);

        int databaseSizeBeforeDelete = surveyResultRepository.findAll().size();

        // Delete the surveyResult
        restSurveyResultMockMvc
            .perform(delete(ENTITY_API_URL_ID, surveyResult.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
