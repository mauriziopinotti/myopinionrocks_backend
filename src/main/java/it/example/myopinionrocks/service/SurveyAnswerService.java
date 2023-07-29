package it.example.myopinionrocks.service;

import it.example.myopinionrocks.service.dto.SurveyAnswerDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link it.example.myopinionrocks.domain.SurveyAnswer}.
 */
public interface SurveyAnswerService {
    /**
     * Save a surveyAnswer.
     *
     * @param surveyAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    SurveyAnswerDTO save(SurveyAnswerDTO surveyAnswerDTO);

    /**
     * Updates a surveyAnswer.
     *
     * @param surveyAnswerDTO the entity to update.
     * @return the persisted entity.
     */
    SurveyAnswerDTO update(SurveyAnswerDTO surveyAnswerDTO);

    /**
     * Partially updates a surveyAnswer.
     *
     * @param surveyAnswerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SurveyAnswerDTO> partialUpdate(SurveyAnswerDTO surveyAnswerDTO);

    /**
     * Get all the surveyAnswers.
     *
     * @return the list of entities.
     */
    List<SurveyAnswerDTO> findAll();

    /**
     * Get the "id" surveyAnswer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SurveyAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" surveyAnswer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
