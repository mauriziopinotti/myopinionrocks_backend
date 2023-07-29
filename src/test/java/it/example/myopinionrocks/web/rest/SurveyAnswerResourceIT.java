package it.example.myopinionrocks.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.example.myopinionrocks.IntegrationTest;
import it.example.myopinionrocks.domain.SurveyAnswer;
import it.example.myopinionrocks.repository.SurveyAnswerRepository;
import it.example.myopinionrocks.service.dto.SurveyAnswerDTO;
import it.example.myopinionrocks.service.mapper.SurveyAnswerMapper;
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
 * Integration tests for the {@link SurveyAnswerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SurveyAnswerResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/survey-answers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SurveyAnswerRepository surveyAnswerRepository;

    @Autowired
    private SurveyAnswerMapper surveyAnswerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSurveyAnswerMockMvc;

    private SurveyAnswer surveyAnswer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SurveyAnswer createEntity(EntityManager em) {
        SurveyAnswer surveyAnswer = new SurveyAnswer().title(DEFAULT_TITLE);
        return surveyAnswer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SurveyAnswer createUpdatedEntity(EntityManager em) {
        SurveyAnswer surveyAnswer = new SurveyAnswer().title(UPDATED_TITLE);
        return surveyAnswer;
    }

    @BeforeEach
    public void initTest() {
        surveyAnswer = createEntity(em);
    }

    @Test
    @Transactional
    void createSurveyAnswer() throws Exception {
        int databaseSizeBeforeCreate = surveyAnswerRepository.findAll().size();
        // Create the SurveyAnswer
        SurveyAnswerDTO surveyAnswerDTO = surveyAnswerMapper.toDto(surveyAnswer);
        restSurveyAnswerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyAnswerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        SurveyAnswer testSurveyAnswer = surveyAnswerList.get(surveyAnswerList.size() - 1);
        assertThat(testSurveyAnswer.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void createSurveyAnswerWithExistingId() throws Exception {
        // Create the SurveyAnswer with an existing ID
        surveyAnswer.setId(1L);
        SurveyAnswerDTO surveyAnswerDTO = surveyAnswerMapper.toDto(surveyAnswer);

        int databaseSizeBeforeCreate = surveyAnswerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSurveyAnswerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyAnswerRepository.findAll().size();
        // set the field null
        surveyAnswer.setTitle(null);

        // Create the SurveyAnswer, which fails.
        SurveyAnswerDTO surveyAnswerDTO = surveyAnswerMapper.toDto(surveyAnswer);

        restSurveyAnswerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSurveyAnswers() throws Exception {
        // Initialize the database
        surveyAnswerRepository.saveAndFlush(surveyAnswer);

        // Get all the surveyAnswerList
        restSurveyAnswerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(surveyAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @Test
    @Transactional
    void getSurveyAnswer() throws Exception {
        // Initialize the database
        surveyAnswerRepository.saveAndFlush(surveyAnswer);

        // Get the surveyAnswer
        restSurveyAnswerMockMvc
            .perform(get(ENTITY_API_URL_ID, surveyAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(surveyAnswer.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    void getNonExistingSurveyAnswer() throws Exception {
        // Get the surveyAnswer
        restSurveyAnswerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSurveyAnswer() throws Exception {
        // Initialize the database
        surveyAnswerRepository.saveAndFlush(surveyAnswer);

        int databaseSizeBeforeUpdate = surveyAnswerRepository.findAll().size();

        // Update the surveyAnswer
        SurveyAnswer updatedSurveyAnswer = surveyAnswerRepository.findById(surveyAnswer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSurveyAnswer are not directly saved in db
        em.detach(updatedSurveyAnswer);
        updatedSurveyAnswer.title(UPDATED_TITLE);
        SurveyAnswerDTO surveyAnswerDTO = surveyAnswerMapper.toDto(updatedSurveyAnswer);

        restSurveyAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, surveyAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyAnswerDTO))
            )
            .andExpect(status().isOk());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeUpdate);
        SurveyAnswer testSurveyAnswer = surveyAnswerList.get(surveyAnswerList.size() - 1);
        assertThat(testSurveyAnswer.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void putNonExistingSurveyAnswer() throws Exception {
        int databaseSizeBeforeUpdate = surveyAnswerRepository.findAll().size();
        surveyAnswer.setId(count.incrementAndGet());

        // Create the SurveyAnswer
        SurveyAnswerDTO surveyAnswerDTO = surveyAnswerMapper.toDto(surveyAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, surveyAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSurveyAnswer() throws Exception {
        int databaseSizeBeforeUpdate = surveyAnswerRepository.findAll().size();
        surveyAnswer.setId(count.incrementAndGet());

        // Create the SurveyAnswer
        SurveyAnswerDTO surveyAnswerDTO = surveyAnswerMapper.toDto(surveyAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSurveyAnswer() throws Exception {
        int databaseSizeBeforeUpdate = surveyAnswerRepository.findAll().size();
        surveyAnswer.setId(count.incrementAndGet());

        // Create the SurveyAnswer
        SurveyAnswerDTO surveyAnswerDTO = surveyAnswerMapper.toDto(surveyAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyAnswerMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyAnswerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSurveyAnswerWithPatch() throws Exception {
        // Initialize the database
        surveyAnswerRepository.saveAndFlush(surveyAnswer);

        int databaseSizeBeforeUpdate = surveyAnswerRepository.findAll().size();

        // Update the surveyAnswer using partial update
        SurveyAnswer partialUpdatedSurveyAnswer = new SurveyAnswer();
        partialUpdatedSurveyAnswer.setId(surveyAnswer.getId());

        restSurveyAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurveyAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurveyAnswer))
            )
            .andExpect(status().isOk());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeUpdate);
        SurveyAnswer testSurveyAnswer = surveyAnswerList.get(surveyAnswerList.size() - 1);
        assertThat(testSurveyAnswer.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void fullUpdateSurveyAnswerWithPatch() throws Exception {
        // Initialize the database
        surveyAnswerRepository.saveAndFlush(surveyAnswer);

        int databaseSizeBeforeUpdate = surveyAnswerRepository.findAll().size();

        // Update the surveyAnswer using partial update
        SurveyAnswer partialUpdatedSurveyAnswer = new SurveyAnswer();
        partialUpdatedSurveyAnswer.setId(surveyAnswer.getId());

        partialUpdatedSurveyAnswer.title(UPDATED_TITLE);

        restSurveyAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurveyAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurveyAnswer))
            )
            .andExpect(status().isOk());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeUpdate);
        SurveyAnswer testSurveyAnswer = surveyAnswerList.get(surveyAnswerList.size() - 1);
        assertThat(testSurveyAnswer.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void patchNonExistingSurveyAnswer() throws Exception {
        int databaseSizeBeforeUpdate = surveyAnswerRepository.findAll().size();
        surveyAnswer.setId(count.incrementAndGet());

        // Create the SurveyAnswer
        SurveyAnswerDTO surveyAnswerDTO = surveyAnswerMapper.toDto(surveyAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, surveyAnswerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveyAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSurveyAnswer() throws Exception {
        int databaseSizeBeforeUpdate = surveyAnswerRepository.findAll().size();
        surveyAnswer.setId(count.incrementAndGet());

        // Create the SurveyAnswer
        SurveyAnswerDTO surveyAnswerDTO = surveyAnswerMapper.toDto(surveyAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveyAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSurveyAnswer() throws Exception {
        int databaseSizeBeforeUpdate = surveyAnswerRepository.findAll().size();
        surveyAnswer.setId(count.incrementAndGet());

        // Create the SurveyAnswer
        SurveyAnswerDTO surveyAnswerDTO = surveyAnswerMapper.toDto(surveyAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveyAnswerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSurveyAnswer() throws Exception {
        // Initialize the database
        surveyAnswerRepository.saveAndFlush(surveyAnswer);

        int databaseSizeBeforeDelete = surveyAnswerRepository.findAll().size();

        // Delete the surveyAnswer
        restSurveyAnswerMockMvc
            .perform(delete(ENTITY_API_URL_ID, surveyAnswer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
