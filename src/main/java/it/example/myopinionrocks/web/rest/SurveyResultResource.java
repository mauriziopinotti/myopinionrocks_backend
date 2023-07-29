package it.example.myopinionrocks.web.rest;

import it.example.myopinionrocks.repository.SurveyResultRepository;
import it.example.myopinionrocks.service.SurveyResultService;
import it.example.myopinionrocks.service.dto.SurveyResultDTO;
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
 * REST controller for managing {@link it.example.myopinionrocks.domain.SurveyResult}.
 */
@RestController
@RequestMapping("/api")
public class SurveyResultResource {

    private final Logger log = LoggerFactory.getLogger(SurveyResultResource.class);

    private static final String ENTITY_NAME = "surveyResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SurveyResultService surveyResultService;

    private final SurveyResultRepository surveyResultRepository;

    public SurveyResultResource(SurveyResultService surveyResultService, SurveyResultRepository surveyResultRepository) {
        this.surveyResultService = surveyResultService;
        this.surveyResultRepository = surveyResultRepository;
    }

    /**
     * {@code POST  /survey-results} : Create a new surveyResult.
     *
     * @param surveyResultDTO the surveyResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new surveyResultDTO, or with status {@code 400 (Bad Request)} if the surveyResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/survey-results")
    public ResponseEntity<SurveyResultDTO> createSurveyResult(@Valid @RequestBody SurveyResultDTO surveyResultDTO)
        throws URISyntaxException {
        log.debug("REST request to save SurveyResult : {}", surveyResultDTO);
        if (surveyResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new surveyResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SurveyResultDTO result = surveyResultService.save(surveyResultDTO);
        return ResponseEntity
            .created(new URI("/api/survey-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /survey-results/:id} : Updates an existing surveyResult.
     *
     * @param id the id of the surveyResultDTO to save.
     * @param surveyResultDTO the surveyResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated surveyResultDTO,
     * or with status {@code 400 (Bad Request)} if the surveyResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the surveyResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/survey-results/{id}")
    public ResponseEntity<SurveyResultDTO> updateSurveyResult(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SurveyResultDTO surveyResultDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SurveyResult : {}, {}", id, surveyResultDTO);
        if (surveyResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, surveyResultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!surveyResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SurveyResultDTO result = surveyResultService.update(surveyResultDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, surveyResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /survey-results/:id} : Partial updates given fields of an existing surveyResult, field will ignore if it is null
     *
     * @param id the id of the surveyResultDTO to save.
     * @param surveyResultDTO the surveyResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated surveyResultDTO,
     * or with status {@code 400 (Bad Request)} if the surveyResultDTO is not valid,
     * or with status {@code 404 (Not Found)} if the surveyResultDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the surveyResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/survey-results/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SurveyResultDTO> partialUpdateSurveyResult(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SurveyResultDTO surveyResultDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SurveyResult partially : {}, {}", id, surveyResultDTO);
        if (surveyResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, surveyResultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!surveyResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SurveyResultDTO> result = surveyResultService.partialUpdate(surveyResultDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, surveyResultDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /survey-results} : get all the surveyResults.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of surveyResults in body.
     */
    @GetMapping("/survey-results")
    public List<SurveyResultDTO> getAllSurveyResults() {
        log.debug("REST request to get all SurveyResults");
        return surveyResultService.findAll();
    }

    /**
     * {@code GET  /survey-results/:id} : get the "id" surveyResult.
     *
     * @param id the id of the surveyResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the surveyResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/survey-results/{id}")
    public ResponseEntity<SurveyResultDTO> getSurveyResult(@PathVariable Long id) {
        log.debug("REST request to get SurveyResult : {}", id);
        Optional<SurveyResultDTO> surveyResultDTO = surveyResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(surveyResultDTO);
    }

    /**
     * {@code DELETE  /survey-results/:id} : delete the "id" surveyResult.
     *
     * @param id the id of the surveyResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/survey-results/{id}")
    public ResponseEntity<Void> deleteSurveyResult(@PathVariable Long id) {
        log.debug("REST request to delete SurveyResult : {}", id);
        surveyResultService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
