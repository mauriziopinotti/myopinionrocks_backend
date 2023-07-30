package it.example.myopinionrocks.web.rest;

import it.example.myopinionrocks.repository.SurveyQuestionRepository;
import it.example.myopinionrocks.service.SurveyQuestionService;
import it.example.myopinionrocks.service.dto.SurveyQuestionDTO;
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
 * REST controller for managing {@link it.example.myopinionrocks.domain.SurveyQuestion}.
 */
@RestController
@RequestMapping("/api")
public class SurveyQuestionResource {

    private final Logger log = LoggerFactory.getLogger(SurveyQuestionResource.class);

    private static final String ENTITY_NAME = "surveyQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SurveyQuestionService surveyQuestionService;

    private final SurveyQuestionRepository surveyQuestionRepository;

    public SurveyQuestionResource(SurveyQuestionService surveyQuestionService, SurveyQuestionRepository surveyQuestionRepository) {
        this.surveyQuestionService = surveyQuestionService;
        this.surveyQuestionRepository = surveyQuestionRepository;
    }

    /**
     * {@code POST  /survey-questions} : Create a new surveyQuestion.
     *
     * @param surveyQuestionDTO the surveyQuestionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new surveyQuestionDTO, or with status {@code 400 (Bad Request)} if the surveyQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/survey-questions")
    public ResponseEntity<SurveyQuestionDTO> createSurveyQuestion(@Valid @RequestBody SurveyQuestionDTO surveyQuestionDTO)
        throws URISyntaxException {
        log.debug("REST request to save SurveyQuestion : {}", surveyQuestionDTO);
        if (surveyQuestionDTO.getId() != null) {
            throw new BadRequestAlertException("A new surveyQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SurveyQuestionDTO result = surveyQuestionService.save(surveyQuestionDTO);
        return ResponseEntity
            .created(new URI("/api/survey-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /survey-questions/:id} : Updates an existing surveyQuestion.
     *
     * @param id the id of the surveyQuestionDTO to save.
     * @param surveyQuestionDTO the surveyQuestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated surveyQuestionDTO,
     * or with status {@code 400 (Bad Request)} if the surveyQuestionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the surveyQuestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/survey-questions/{id}")
    public ResponseEntity<SurveyQuestionDTO> updateSurveyQuestion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SurveyQuestionDTO surveyQuestionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SurveyQuestion : {}, {}", id, surveyQuestionDTO);
        if (surveyQuestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, surveyQuestionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!surveyQuestionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SurveyQuestionDTO result = surveyQuestionService.update(surveyQuestionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, surveyQuestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /survey-questions/:id} : Partial updates given fields of an existing surveyQuestion, field will ignore if it is null
     *
     * @param id the id of the surveyQuestionDTO to save.
     * @param surveyQuestionDTO the surveyQuestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated surveyQuestionDTO,
     * or with status {@code 400 (Bad Request)} if the surveyQuestionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the surveyQuestionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the surveyQuestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/survey-questions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SurveyQuestionDTO> partialUpdateSurveyQuestion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SurveyQuestionDTO surveyQuestionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SurveyQuestion partially : {}, {}", id, surveyQuestionDTO);
        if (surveyQuestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, surveyQuestionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!surveyQuestionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SurveyQuestionDTO> result = surveyQuestionService.partialUpdate(surveyQuestionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, surveyQuestionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /survey-questions} : get all the surveyQuestions.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of surveyQuestions in body.
     */
    @GetMapping("/survey-questions")
    public List<SurveyQuestionDTO> getAllSurveyQuestions(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all SurveyQuestions");
        return surveyQuestionService.findAll();
    }

    /**
     * {@code GET  /survey-questions/:id} : get the "id" surveyQuestion.
     *
     * @param id the id of the surveyQuestionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the surveyQuestionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/survey-questions/{id}")
    public ResponseEntity<SurveyQuestionDTO> getSurveyQuestion(@PathVariable Long id) {
        log.debug("REST request to get SurveyQuestion : {}", id);
        Optional<SurveyQuestionDTO> surveyQuestionDTO = surveyQuestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(surveyQuestionDTO);
    }

    /**
     * {@code DELETE  /survey-questions/:id} : delete the "id" surveyQuestion.
     *
     * @param id the id of the surveyQuestionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/survey-questions/{id}")
    public ResponseEntity<Void> deleteSurveyQuestion(@PathVariable Long id) {
        log.debug("REST request to delete SurveyQuestion : {}", id);
        surveyQuestionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
