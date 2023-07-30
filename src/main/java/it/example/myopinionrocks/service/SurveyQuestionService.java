package it.example.myopinionrocks.service;

import it.example.myopinionrocks.service.dto.SurveyQuestionDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link it.example.myopinionrocks.domain.SurveyQuestion}.
 */
public interface SurveyQuestionService {
    /**
     * Save a surveyQuestion.
     *
     * @param surveyQuestionDTO the entity to save.
     * @return the persisted entity.
     */
    SurveyQuestionDTO save(SurveyQuestionDTO surveyQuestionDTO);

    /**
     * Updates a surveyQuestion.
     *
     * @param surveyQuestionDTO the entity to update.
     * @return the persisted entity.
     */
    SurveyQuestionDTO update(SurveyQuestionDTO surveyQuestionDTO);

    /**
     * Partially updates a surveyQuestion.
     *
     * @param surveyQuestionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SurveyQuestionDTO> partialUpdate(SurveyQuestionDTO surveyQuestionDTO);

    /**
     * Get all the surveyQuestions.
     *
     * @return the list of entities.
     */
    List<SurveyQuestionDTO> findAll();

    /**
     * Get all the surveyQuestions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SurveyQuestionDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" surveyQuestion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SurveyQuestionDTO> findOne(Long id);

    /**
     * Delete the "id" surveyQuestion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
