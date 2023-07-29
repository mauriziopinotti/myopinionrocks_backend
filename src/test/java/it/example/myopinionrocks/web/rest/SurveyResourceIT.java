package it.example.myopinionrocks.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.example.myopinionrocks.IntegrationTest;
import it.example.myopinionrocks.domain.Survey;
import it.example.myopinionrocks.repository.SurveyRepository;
import it.example.myopinionrocks.service.dto.SurveyDTO;
import it.example.myopinionrocks.service.mapper.SurveyMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link SurveyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SurveyResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/surveys";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyMapper surveyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSurveyMockMvc;

    private Survey survey;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Survey createEntity(EntityManager em) {
        Survey survey = new Survey().title(DEFAULT_TITLE);
        return survey;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Survey createUpdatedEntity(EntityManager em) {
        Survey survey = new Survey().title(UPDATED_TITLE);
        return survey;
    }

    @BeforeEach
    public void initTest() {
        survey = createEntity(em);
    }

    @Test
    @Transactional
    void createSurvey() throws Exception {
        int databaseSizeBeforeCreate = surveyRepository.findAll().size();
        // Create the Survey
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);
        restSurveyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyDTO)))
            .andExpect(status().isCreated());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeCreate + 1);
        Survey testSurvey = surveyList.get(surveyList.size() - 1);
        assertThat(testSurvey.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void createSurveyWithExistingId() throws Exception {
        // Create the Survey with an existing ID
        survey.setId(1L);
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        int databaseSizeBeforeCreate = surveyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSurveyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyRepository.findAll().size();
        // set the field null
        survey.setTitle(null);

        // Create the Survey, which fails.
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        restSurveyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyDTO)))
            .andExpect(status().isBadRequest());

        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSurveys() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get all the surveyList
        restSurveyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(survey.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @Test
    @Transactional
    void getSurvey() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        // Get the survey
        restSurveyMockMvc
            .perform(get(ENTITY_API_URL_ID, survey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(survey.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    void getNonExistingSurvey() throws Exception {
        // Get the survey
        restSurveyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSurvey() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();

        // Update the survey
        Survey updatedSurvey = surveyRepository.findById(survey.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSurvey are not directly saved in db
        em.detach(updatedSurvey);
        updatedSurvey.title(UPDATED_TITLE);
        SurveyDTO surveyDTO = surveyMapper.toDto(updatedSurvey);

        restSurveyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, surveyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
        Survey testSurvey = surveyList.get(surveyList.size() - 1);
        assertThat(testSurvey.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void putNonExistingSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // Create the Survey
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, surveyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // Create the Survey
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // Create the Survey
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSurveyWithPatch() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();

        // Update the survey using partial update
        Survey partialUpdatedSurvey = new Survey();
        partialUpdatedSurvey.setId(survey.getId());

        partialUpdatedSurvey.title(UPDATED_TITLE);

        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurvey.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurvey))
            )
            .andExpect(status().isOk());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
        Survey testSurvey = surveyList.get(surveyList.size() - 1);
        assertThat(testSurvey.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void fullUpdateSurveyWithPatch() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();

        // Update the survey using partial update
        Survey partialUpdatedSurvey = new Survey();
        partialUpdatedSurvey.setId(survey.getId());

        partialUpdatedSurvey.title(UPDATED_TITLE);

        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurvey.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurvey))
            )
            .andExpect(status().isOk());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
        Survey testSurvey = surveyList.get(surveyList.size() - 1);
        assertThat(testSurvey.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void patchNonExistingSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // Create the Survey
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, surveyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // Create the Survey
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSurvey() throws Exception {
        int databaseSizeBeforeUpdate = surveyRepository.findAll().size();
        survey.setId(count.incrementAndGet());

        // Create the Survey
        SurveyDTO surveyDTO = surveyMapper.toDto(survey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(surveyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Survey in the database
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSurvey() throws Exception {
        // Initialize the database
        surveyRepository.saveAndFlush(survey);

        int databaseSizeBeforeDelete = surveyRepository.findAll().size();

        // Delete the survey
        restSurveyMockMvc
            .perform(delete(ENTITY_API_URL_ID, survey.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Survey> surveyList = surveyRepository.findAll();
        assertThat(surveyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
