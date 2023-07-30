package it.example.myopinionrocks.service;

import it.example.myopinionrocks.domain.User;
import it.example.myopinionrocks.service.dto.SurveyResultDTO;
import it.example.myopinionrocks.service.dto.SurveyResultSubmitDTO;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link it.example.myopinionrocks.domain.SurveyResult}.
 */
public interface SurveyResultService {
    /**
     * Save a surveyResult.
     *
     * @param surveyResultDTO the entity to save.
     * @return the persisted entity.
     */
    SurveyResultDTO save(SurveyResultDTO surveyResultDTO);

    /**
     * Updates a surveyResult.
     *
     * @param surveyResultDTO the entity to update.
     * @return the persisted entity.
     */
    SurveyResultDTO update(SurveyResultDTO surveyResultDTO);

    /**
     * Partially updates a surveyResult.
     *
     * @param surveyResultDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SurveyResultDTO> partialUpdate(SurveyResultDTO surveyResultDTO);

    /**
     * Get all the surveyResults.
     *
     * @return the list of entities.
     */
    List<SurveyResultDTO> findAll();

    /**
     * Get all the surveyResults with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SurveyResultDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" surveyResult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SurveyResultDTO> findOne(Long id);

    /**
     * Delete the "id" surveyResult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    SurveyResultDTO save(@Nullable User loggedUser, SurveyResultSubmitDTO surveyResultDTO);
}
