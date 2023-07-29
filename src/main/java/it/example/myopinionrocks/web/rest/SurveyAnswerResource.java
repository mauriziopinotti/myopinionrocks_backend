package it.example.myopinionrocks.web.rest;

import it.example.myopinionrocks.repository.SurveyAnswerRepository;
import it.example.myopinionrocks.service.SurveyAnswerService;
import it.example.myopinionrocks.service.dto.SurveyAnswerDTO;
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
 * REST controller for managing {@link it.example.myopinionrocks.domain.SurveyAnswer}.
 */
@RestController
@RequestMapping("/api")
public class SurveyAnswerResource {

    private final Logger log = LoggerFactory.getLogger(SurveyAnswerResource.class);

    private static final String ENTITY_NAME = "surveyAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SurveyAnswerService surveyAnswerService;

    private final SurveyAnswerRepository surveyAnswerRepository;

    public SurveyAnswerResource(SurveyAnswerService surveyAnswerService, SurveyAnswerRepository surveyAnswerRepository) {
        this.surveyAnswerService = surveyAnswerService;
        this.surveyAnswerRepository = surveyAnswerRepository;
    }

    /**
     * {@code POST  /survey-answers} : Create a new surveyAnswer.
     *
     * @param surveyAnswerDTO the surveyAnswerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new surveyAnswerDTO, or with status {@code 400 (Bad Request)} if the surveyAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/survey-answers")
    public ResponseEntity<SurveyAnswerDTO> createSurveyAnswer(@Valid @RequestBody SurveyAnswerDTO surveyAnswerDTO)
        throws URISyntaxException {
        log.debug("REST request to save SurveyAnswer : {}", surveyAnswerDTO);
        if (surveyAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new surveyAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SurveyAnswerDTO result = surveyAnswerService.save(surveyAnswerDTO);
        return ResponseEntity
            .created(new URI("/api/survey-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /survey-answers/:id} : Updates an existing surveyAnswer.
     *
     * @param id the id of the surveyAnswerDTO to save.
     * @param surveyAnswerDTO the surveyAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated surveyAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the surveyAnswerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the surveyAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/survey-answers/{id}")
    public ResponseEntity<SurveyAnswerDTO> updateSurveyAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SurveyAnswerDTO surveyAnswerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SurveyAnswer : {}, {}", id, surveyAnswerDTO);
        if (surveyAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, surveyAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!surveyAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SurveyAnswerDTO result = surveyAnswerService.update(surveyAnswerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, surveyAnswerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /survey-answers/:id} : Partial updates given fields of an existing surveyAnswer, field will ignore if it is null
     *
     * @param id the id of the surveyAnswerDTO to save.
     * @param surveyAnswerDTO the surveyAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated surveyAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the surveyAnswerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the surveyAnswerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the surveyAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/survey-answers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SurveyAnswerDTO> partialUpdateSurveyAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SurveyAnswerDTO surveyAnswerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SurveyAnswer partially : {}, {}", id, surveyAnswerDTO);
        if (surveyAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, surveyAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!surveyAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SurveyAnswerDTO> result = surveyAnswerService.partialUpdate(surveyAnswerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, surveyAnswerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /survey-answers} : get all the surveyAnswers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of surveyAnswers in body.
     */
    @GetMapping("/survey-answers")
    public List<SurveyAnswerDTO> getAllSurveyAnswers() {
        log.debug("REST request to get all SurveyAnswers");
        return surveyAnswerService.findAll();
    }

    /**
     * {@code GET  /survey-answers/:id} : get the "id" surveyAnswer.
     *
     * @param id the id of the surveyAnswerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the surveyAnswerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/survey-answers/{id}")
    public ResponseEntity<SurveyAnswerDTO> getSurveyAnswer(@PathVariable Long id) {
        log.debug("REST request to get SurveyAnswer : {}", id);
        Optional<SurveyAnswerDTO> surveyAnswerDTO = surveyAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(surveyAnswerDTO);
    }

    /**
     * {@code DELETE  /survey-answers/:id} : delete the "id" surveyAnswer.
     *
     * @param id the id of the surveyAnswerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/survey-answers/{id}")
    public ResponseEntity<Void> deleteSurveyAnswer(@PathVariable Long id) {
        log.debug("REST request to delete SurveyAnswer : {}", id);
        surveyAnswerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
