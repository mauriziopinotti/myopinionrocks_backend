package it.example.myopinionrocks.service.impl;

import it.example.myopinionrocks.domain.*;
import it.example.myopinionrocks.repository.SurveyRepository;
import it.example.myopinionrocks.repository.SurveyResultQuestionAnswerRepository;
import it.example.myopinionrocks.repository.SurveyResultRepository;
import it.example.myopinionrocks.service.SurveyResultService;
import it.example.myopinionrocks.service.dto.SurveyResultDTO;
import it.example.myopinionrocks.service.dto.SurveyResultSubmitDTO;
import it.example.myopinionrocks.service.mapper.SurveyResultMapper;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import it.example.myopinionrocks.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SurveyResult}.
 */
@Service
@Transactional
public class SurveyResultServiceImpl implements SurveyResultService {

    private final Logger log = LoggerFactory.getLogger(SurveyResultServiceImpl.class);

    private final SurveyResultRepository surveyResultRepository;

    private final SurveyResultMapper surveyResultMapper;
    private final SurveyRepository surveyRepository;
    private final SurveyResultQuestionAnswerRepository surveyResultQuestionAnswerRepository;

    public SurveyResultServiceImpl(SurveyResultRepository surveyResultRepository, SurveyResultMapper surveyResultMapper,
                                   SurveyRepository surveyRepository, SurveyResultQuestionAnswerRepository surveyResultQuestionAnswerRepository) {
        this.surveyResultRepository = surveyResultRepository;
        this.surveyResultMapper = surveyResultMapper;
        this.surveyRepository = surveyRepository;
        this.surveyResultQuestionAnswerRepository = surveyResultQuestionAnswerRepository;
    }

    @Override
    public SurveyResultDTO save(SurveyResultDTO surveyResultDTO) {
        log.debug("Request to save SurveyResult : {}", surveyResultDTO);
        SurveyResult surveyResult = surveyResultMapper.toEntity(surveyResultDTO);
        surveyResult = surveyResultRepository.save(surveyResult);
        return surveyResultMapper.toDto(surveyResult);
    }

    @Override
    public SurveyResultDTO update(SurveyResultDTO surveyResultDTO) {
        log.debug("Request to update SurveyResult : {}", surveyResultDTO);
        SurveyResult surveyResult = surveyResultMapper.toEntity(surveyResultDTO);
        surveyResult = surveyResultRepository.save(surveyResult);
        return surveyResultMapper.toDto(surveyResult);
    }

    @Override
    public Optional<SurveyResultDTO> partialUpdate(SurveyResultDTO surveyResultDTO) {
        log.debug("Request to partially update SurveyResult : {}", surveyResultDTO);

        return surveyResultRepository
            .findById(surveyResultDTO.getId())
            .map(existingSurveyResult -> {
                surveyResultMapper.partialUpdate(existingSurveyResult, surveyResultDTO);

                return existingSurveyResult;
            })
            .map(surveyResultRepository::save)
            .map(surveyResultMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SurveyResultDTO> findAll() {
        log.debug("Request to get all SurveyResults");
        return surveyResultRepository.findAll().stream().map(surveyResultMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<SurveyResultDTO> findAllWithEagerRelationships(Pageable pageable) {
        return surveyResultRepository.findAllWithEagerRelationships(pageable).map(surveyResultMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SurveyResultDTO> findOne(Long id) {
        log.debug("Request to get SurveyResult : {}", id);
        return surveyResultRepository.findOneWithEagerRelationships(id).map(surveyResultMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SurveyResult : {}", id);
        surveyResultRepository.deleteById(id);
    }

    @Override
    @Transactional
    public SurveyResultDTO save(@Nullable User loggedUser, SurveyResultSubmitDTO surveyResultDTO) {
        // Check that the user didn't already submit this survey
        if (loggedUser != null &&
            surveyResultRepository.countByUserIdAndSurveyId(loggedUser.getId(), surveyResultDTO.getSurveyId()) > 0) {
            throw new BadRequestAlertException(
                "Survey " + surveyResultDTO.getSurveyId() + " already submitted by user " + loggedUser.getLogin(),
                "survey-result", "surveyResultAlreadySubmitted");
        }

        // Save submission
        // TODO: better error handling
        Survey survey = surveyRepository.findOneById(surveyResultDTO.getSurveyId()).orElseThrow();
        SurveyResult result = new SurveyResult();
        result.setUser(loggedUser);
        result.setSurvey(survey);
        result.setDatetime(Instant.now());
        surveyResultRepository.save(result);

        // Save answers
        // TODO: check that answers and questions match
        Set<SurveyQuestionAnswerResult> answers = new HashSet<>();
        for (Map.Entry<Long, Long> entry : surveyResultDTO.getSurveyAnswers().entrySet()) {
            answers.add(new SurveyQuestionAnswerResult(result.getId(), entry.getKey(), entry.getValue()));
        }
        surveyResultQuestionAnswerRepository.saveAll(answers);

        return surveyResultMapper.toDto(result);
    }
}
