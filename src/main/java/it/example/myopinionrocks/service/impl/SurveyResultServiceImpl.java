package it.example.myopinionrocks.service.impl;

import it.example.myopinionrocks.domain.Survey;
import it.example.myopinionrocks.domain.SurveyResult;
import it.example.myopinionrocks.domain.User;
import it.example.myopinionrocks.repository.SurveyAnswerRepository;
import it.example.myopinionrocks.repository.SurveyQuestionRepository;
import it.example.myopinionrocks.repository.SurveyRepository;
import it.example.myopinionrocks.repository.SurveyResultRepository;
import it.example.myopinionrocks.service.SurveyResultService;
import it.example.myopinionrocks.service.dto.SurveyResultDTO;
import it.example.myopinionrocks.service.dto.SurveyResultSubmitDTO;
import it.example.myopinionrocks.service.mapper.SurveyResultMapper;
import it.example.myopinionrocks.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final SurveyQuestionRepository surveyQuestionRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;

    public SurveyResultServiceImpl(SurveyResultRepository surveyResultRepository, SurveyResultMapper surveyResultMapper,
                                   SurveyRepository surveyRepository,
                                   SurveyQuestionRepository surveyQuestionRepository,
                                   SurveyAnswerRepository surveyAnswerRepository) {
        this.surveyResultRepository = surveyResultRepository;
        this.surveyResultMapper = surveyResultMapper;
        this.surveyRepository = surveyRepository;
        this.surveyQuestionRepository = surveyQuestionRepository;
        this.surveyAnswerRepository = surveyAnswerRepository;
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

    @Override
    @Transactional(readOnly = true)
    public Optional<SurveyResultDTO> findOne(Long id) {
        log.debug("Request to get SurveyResult : {}", id);
        return surveyResultRepository.findById(id).map(surveyResultMapper::toDto);
    }

    @Override
    public void save(@Nullable User loggedUser, SurveyResultSubmitDTO surveyResultDTO) {
        // Check that the user didn't already submit this survey
        if (loggedUser != null &&
                surveyResultRepository.countByUserIdAndSurveyId(loggedUser.getId(), surveyResultDTO.getSurveyId()) > 0) {
            throw new BadRequestAlertException(
                    "Survey " + surveyResultDTO.getSurveyId() + " already submitted by user " + loggedUser.getLogin(),
                    SurveyResult.ENTITY_NAME, "surveyResultAlreadySubmitted");
        }

        // TODO: better error handling
        Survey survey = surveyRepository.findOneById(surveyResultDTO.getSurveyId()).orElseThrow();
        Instant now = Instant.now();

        // Save answers
        for (Map.Entry<Long, Long> entry : surveyResultDTO.getSurveyAnswers().entrySet()) {
            Long questionId = entry.getKey();
            Long answerId = entry.getValue();

            SurveyResult result = new SurveyResult();

            // Set user, survey and time
            result.setUser(loggedUser);
            result.setSurvey(survey);
            result.setDatetime(now);

            // TODO: check that answers and questions match
            result.setSurveyQuestion(surveyQuestionRepository.findOneById(questionId).orElseThrow());
            result.setSurveyAnswer(surveyAnswerRepository.findOneById(answerId).orElseThrow());

            surveyResultRepository.save(result);
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SurveyResult : {}", id);
        surveyResultRepository.deleteById(id);
    }
}
