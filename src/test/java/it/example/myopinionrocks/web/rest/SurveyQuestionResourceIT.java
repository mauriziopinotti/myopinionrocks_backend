package it.example.myopinionrocks.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.example.myopinionrocks.IntegrationTest;
import it.example.myopinionrocks.domain.SurveyQuestion;
import it.example.myopinionrocks.repository.SurveyQuestionRepository;
import it.example.myopinionrocks.service.SurveyQuestionService;
import it.example.myopinionrocks.service.dto.SurveyQuestionDTO;
import it.example.myopinionrocks.service.mapper.SurveyQuestionMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SurveyQuestionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SurveyQuestionResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/survey-questions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SurveyQuestionRepository surveyQuestionRepository;

    @Mock
    private SurveyQuestionRepository surveyQuestionRepositoryMock;

    @Autowired
    private SurveyQuestionMapper surveyQuestionMapper;

    @Mock
    private SurveyQuestionService surveyQuestionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSurveyQuestionMockMvc;

    private SurveyQuestion surveyQuestion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SurveyQuestion createEntity(EntityManager em) {
        SurveyQuestion surveyQuestion = new SurveyQuestion().title(DEFAULT_TITLE);
        return surveyQuestion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SurveyQuestion createUpdatedEntity(EntityManager em) {
        SurveyQuestion surveyQuestion = new SurveyQuestion().title(UPDATED_TITLE);
        return surveyQuestion;
    }

    @BeforeEach
    public void initTest() {
        surveyQuestion = createEntity(em);
    }

    @Test
    @Transactional
    void createSurveyQuestion() throws Exception {
        int databaseSizeBeforeCreate = surveyQuestionRepository.findAll().size();
        // Create the SurveyQuestion
        SurveyQuestionDTO surveyQuestionDTO = surveyQuestionMapper.toDto(surveyQuestion);
        restSurveyQuestionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyQuestionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        SurveyQuestion testSurveyQuestion = surveyQuestionList.get(surveyQuestionList.size() - 1);
        assertThat(testSurveyQuestion.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void createSurveyQuestionWithExistingId() throws Exception {
        // Create the SurveyQuestion with an existing ID
        surveyQuestion.setId(1L);
        SurveyQuestionDTO surveyQuestionDTO = surveyQuestionMapper.toDto(surveyQuestion);

        int databaseSizeBeforeCreate = surveyQuestionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSurveyQuestionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyQuestionRepository.findAll().size();
        // set the field null
        surveyQuestion.setTitle(null);

        // Create the SurveyQuestion, which fails.
        SurveyQuestionDTO surveyQuestionDTO = surveyQuestionMapper.toDto(surveyQuestion);

        restSurveyQuestionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSurveyQuestions() throws Exception {
        // Initialize the database
        surveyQuestionRepository.saveAndFlush(surveyQuestion);

        // Get all the surveyQuestionList
        restSurveyQuestionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(surveyQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSurveyQuestionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(surveyQuestionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSurveyQuestionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(surveyQuestionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSurveyQuestionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(surveyQuestionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSurveyQuestionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(surveyQuestionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSurveyQuestion() throws Exception {
        // Initialize the database
        surveyQuestionRepository.saveAndFlush(surveyQuestion);

        // Get the surveyQuestion
        restSurveyQuestionMockMvc
            .perform(get(ENTITY_API_URL_ID, surveyQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(surveyQuestion.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    void getNonExistingSurveyQuestion() throws Exception {
        // Get the surveyQuestion
        restSurveyQuestionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSurveyQuestion() throws Exception {
        // Initialize the database
        surveyQuestionRepository.saveAndFlush(surveyQuestion);

        int databaseSizeBeforeUpdate = surveyQuestionRepository.findAll().size();

        // Update the surveyQuestion
        SurveyQuestion updatedSurveyQuestion = surveyQuestionRepository.findById(surveyQuestion.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSurveyQuestion are not directly saved in db
        em.detach(updatedSurveyQuestion);
        updatedSurveyQuestion.title(UPDATED_TITLE);
        SurveyQuestionDTO surveyQuestionDTO = surveyQuestionMapper.toDto(updatedSurveyQuestion);

        restSurveyQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, surveyQuestionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyQuestionDTO))
            )
            .andExpect(status().isOk());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeUpdate);
        SurveyQuestion testSurveyQuestion = surveyQuestionList.get(surveyQuestionList.size() - 1);
        assertThat(testSurveyQuestion.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void putNonExistingSurveyQuestion() throws Exception {
        int databaseSizeBeforeUpdate = surveyQuestionRepository.findAll().size();
        surveyQuestion.setId(count.incrementAndGet());

        // Create the SurveyQuestion
        SurveyQuestionDTO surveyQuestionDTO = surveyQuestionMapper.toDto(surveyQuestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, surveyQuestionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSurveyQuestion() throws Exception {
        int databaseSizeBeforeUpdate = surveyQuestionRepository.findAll().size();
        surveyQuestion.setId(count.incrementAndGet());

        // Create the SurveyQuestion
        SurveyQuestionDTO surveyQuestionDTO = surveyQuestionMapper.toDto(surveyQuestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(surveyQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSurveyQuestion() throws Exception {
        int databaseSizeBeforeUpdate = surveyQuestionRepository.findAll().size();
        surveyQuestion.setId(count.incrementAndGet());

        // Create the SurveyQuestion
        SurveyQuestionDTO surveyQuestionDTO = surveyQuestionMapper.toDto(surveyQuestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyQuestionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(surveyQuestionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSurveyQuestionWithPatch() throws Exception {
        // Initialize the database
        surveyQuestionRepository.saveAndFlush(surveyQuestion);

        int databaseSizeBeforeUpdate = surveyQuestionRepository.findAll().size();

        // Update the surveyQuestion using partial update
        SurveyQuestion partialUpdatedSurveyQuestion = new SurveyQuestion();
        partialUpdatedSurveyQuestion.setId(surveyQuestion.getId());

        partialUpdatedSurveyQuestion.title(UPDATED_TITLE);

        restSurveyQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurveyQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurveyQuestion))
            )
            .andExpect(status().isOk());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeUpdate);
        SurveyQuestion testSurveyQuestion = surveyQuestionList.get(surveyQuestionList.size() - 1);
        assertThat(testSurveyQuestion.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void fullUpdateSurveyQuestionWithPatch() throws Exception {
        // Initialize the database
        surveyQuestionRepository.saveAndFlush(surveyQuestion);

        int databaseSizeBeforeUpdate = surveyQuestionRepository.findAll().size();

        // Update the surveyQuestion using partial update
        SurveyQuestion partialUpdatedSurveyQuestion = new SurveyQuestion();
        partialUpdatedSurveyQuestion.setId(surveyQuestion.getId());

        partialUpdatedSurveyQuestion.title(UPDATED_TITLE);

        restSurveyQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSurveyQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSurveyQuestion))
            )
            .andExpect(status().isOk());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeUpdate);
        SurveyQuestion testSurveyQuestion = surveyQuestionList.get(surveyQuestionList.size() - 1);
        assertThat(testSurveyQuestion.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void patchNonExistingSurveyQuestion() throws Exception {
        int databaseSizeBeforeUpdate = surveyQuestionRepository.findAll().size();
        surveyQuestion.setId(count.incrementAndGet());

        // Create the SurveyQuestion
        SurveyQuestionDTO surveyQuestionDTO = surveyQuestionMapper.toDto(surveyQuestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, surveyQuestionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveyQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSurveyQuestion() throws Exception {
        int databaseSizeBeforeUpdate = surveyQuestionRepository.findAll().size();
        surveyQuestion.setId(count.incrementAndGet());

        // Create the SurveyQuestion
        SurveyQuestionDTO surveyQuestionDTO = surveyQuestionMapper.toDto(surveyQuestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveyQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSurveyQuestion() throws Exception {
        int databaseSizeBeforeUpdate = surveyQuestionRepository.findAll().size();
        surveyQuestion.setId(count.incrementAndGet());

        // Create the SurveyQuestion
        SurveyQuestionDTO surveyQuestionDTO = surveyQuestionMapper.toDto(surveyQuestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSurveyQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(surveyQuestionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSurveyQuestion() throws Exception {
        // Initialize the database
        surveyQuestionRepository.saveAndFlush(surveyQuestion);

        int databaseSizeBeforeDelete = surveyQuestionRepository.findAll().size();

        // Delete the surveyQuestion
        restSurveyQuestionMockMvc
            .perform(delete(ENTITY_API_URL_ID, surveyQuestion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
