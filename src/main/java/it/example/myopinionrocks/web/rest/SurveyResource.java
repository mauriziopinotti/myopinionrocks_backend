package it.example.myopinionrocks.web.rest;

import it.example.myopinionrocks.repository.SurveyRepository;
import it.example.myopinionrocks.service.SurveyService;
import it.example.myopinionrocks.service.dto.SurveyDTO;
import it.example.myopinionrocks.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link it.example.myopinionrocks.domain.Survey}.
 */
@RestController
@RequestMapping("/api")
public class SurveyResource {

    private final Logger log = LoggerFactory.getLogger(SurveyResource.class);

    private static final String ENTITY_NAME = "survey";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SurveyService surveyService;

    private final SurveyRepository surveyRepository;

    public SurveyResource(SurveyService surveyService, SurveyRepository surveyRepository) {
        this.surveyService = surveyService;
        this.surveyRepository = surveyRepository;
    }

    /**
     * {@code POST  /surveys} : Create a new survey.
     *
     * @param surveyDTO the surveyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new surveyDTO, or with status {@code 400 (Bad Request)} if the survey has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/surveys")
    public ResponseEntity<SurveyDTO> createSurvey(@Valid @RequestBody SurveyDTO surveyDTO) throws URISyntaxException {
        log.debug("REST request to save Survey : {}", surveyDTO);
        if (surveyDTO.getId() != null) {
            throw new BadRequestAlertException("A new survey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SurveyDTO result = surveyService.save(surveyDTO);
        return ResponseEntity
            .created(new URI("/api/surveys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /surveys/:id} : Updates an existing survey.
     *
     * @param id the id of the surveyDTO to save.
     * @param surveyDTO the surveyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated surveyDTO,
     * or with status {@code 400 (Bad Request)} if the surveyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the surveyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/surveys/{id}")
    public ResponseEntity<SurveyDTO> updateSurvey(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SurveyDTO surveyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Survey : {}, {}", id, surveyDTO);
        if (surveyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, surveyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!surveyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SurveyDTO result = surveyService.update(surveyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, surveyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /surveys/:id} : Partial updates given fields of an existing survey, field will ignore if it is null
     *
     * @param id the id of the surveyDTO to save.
     * @param surveyDTO the surveyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated surveyDTO,
     * or with status {@code 400 (Bad Request)} if the surveyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the surveyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the surveyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/surveys/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SurveyDTO> partialUpdateSurvey(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SurveyDTO surveyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Survey partially : {}, {}", id, surveyDTO);
        if (surveyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, surveyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!surveyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SurveyDTO> result = surveyService.partialUpdate(surveyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, surveyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /surveys} : get all the surveys.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of surveys in body.
     */
    @GetMapping("/surveys")
    public List<SurveyDTO> getAllSurveys() {
        log.debug("REST request to get all Surveys");
        return surveyService.findAll();
    }

    /**
     * {@code GET  /surveys/:id} : get the "id" survey.
     *
     * @param id the id of the surveyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the surveyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/surveys/{id}")
    public ResponseEntity<SurveyDTO> getSurvey(@PathVariable Long id) {
        log.debug("REST request to get Survey : {}", id);
        Optional<SurveyDTO> surveyDTO = surveyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(surveyDTO);
    }

    /**
     * {@code DELETE  /surveys/:id} : delete the "id" survey.
     *
     * @param id the id of the surveyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/surveys/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        log.debug("REST request to delete Survey : {}", id);
        surveyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
