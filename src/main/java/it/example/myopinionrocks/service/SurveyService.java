package it.example.myopinionrocks.service;

import it.example.myopinionrocks.domain.User;
import it.example.myopinionrocks.service.dto.SurveyDTO;

import java.util.List;
import java.util.Optional;

import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link it.example.myopinionrocks.domain.Survey}.
 */
public interface SurveyService {
    /**
     * Save a survey.
     *
     * @param surveyDTO the entity to save.
     * @return the persisted entity.
     */
    SurveyDTO save(SurveyDTO surveyDTO);

    /**
     * Updates a survey.
     *
     * @param surveyDTO the entity to update.
     * @return the persisted entity.
     */
    SurveyDTO update(SurveyDTO surveyDTO);

    /**
     * Partially updates a survey.
     *
     * @param surveyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SurveyDTO> partialUpdate(SurveyDTO surveyDTO);

    /**
     * Get all the surveys.
     *
     * @return the list of entities.
     */
    List<SurveyDTO> findAll();

    /**
     * Get all the surveys with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SurveyDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" survey.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SurveyDTO> findOne(Long id);

    /**
     * Delete the "id" survey.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<SurveyDTO> findOneForUser(@Nullable User loggedUser);
}
