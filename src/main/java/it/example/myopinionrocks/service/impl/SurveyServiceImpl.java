package it.example.myopinionrocks.service.impl;

import it.example.myopinionrocks.domain.Survey;
import it.example.myopinionrocks.domain.SurveyAnswer;
import it.example.myopinionrocks.domain.SurveyQuestion;
import it.example.myopinionrocks.domain.User;
import it.example.myopinionrocks.repository.SurveyRepository;
import it.example.myopinionrocks.repository.SurveyResultQuestionAnswerRepository;
import it.example.myopinionrocks.service.SurveyService;
import it.example.myopinionrocks.service.dto.SurveyDTO;
import it.example.myopinionrocks.service.mapper.SurveyMapper;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Survey}.
 */
@Service
@Transactional
public class SurveyServiceImpl implements SurveyService {

    private final Logger log = LoggerFactory.getLogger(SurveyServiceImpl.class);

    private final SurveyRepository surveyRepository;

    private final SurveyMapper surveyMapper;
    private final SurveyResultQuestionAnswerRepository surveyResultQuestionAnswerRepository;

    public SurveyServiceImpl(SurveyRepository surveyRepository, SurveyMapper surveyMapper,
                             SurveyResultQuestionAnswerRepository surveyResultQuestionAnswerRepository) {
        this.surveyRepository = surveyRepository;
        this.surveyMapper = surveyMapper;
        this.surveyResultQuestionAnswerRepository = surveyResultQuestionAnswerRepository;
    }

    @Override
    public SurveyDTO save(SurveyDTO surveyDTO) {
        log.debug("Request to save Survey : {}", surveyDTO);
        Survey survey = surveyMapper.toEntity(surveyDTO);
        survey = surveyRepository.save(survey);
        return surveyMapper.toDto(survey);
    }

    @Override
    public SurveyDTO update(SurveyDTO surveyDTO) {
        log.debug("Request to update Survey : {}", surveyDTO);
        Survey survey = surveyMapper.toEntity(surveyDTO);
        survey = surveyRepository.save(survey);
        return surveyMapper.toDto(survey);
    }

    @Override
    public Optional<SurveyDTO> partialUpdate(SurveyDTO surveyDTO) {
        log.debug("Request to partially update Survey : {}", surveyDTO);

        return surveyRepository
            .findById(surveyDTO.getId())
            .map(existingSurvey -> {
                surveyMapper.partialUpdate(existingSurvey, surveyDTO);

                return existingSurvey;
            })
            .map(surveyRepository::save)
            .map(surveyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SurveyDTO> findAll() {
        log.debug("Request to get all Surveys");
        return surveyRepository.findAll().stream().map(surveyMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<SurveyDTO> findAllWithEagerRelationships(Pageable pageable) {
        return surveyRepository.findAllWithEagerRelationships(pageable).map(surveyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SurveyDTO> findOne(Long id) {
        log.debug("Request to get Survey : {}", id);
        return surveyRepository.findOneWithEagerRelationships(id).map(surveyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Survey : {}", id);
        surveyRepository.deleteById(id);
    }

    @Override
    public Optional<SurveyDTO> findOneForUser(@Nullable User loggedUser) {
        log.debug("Request to findOneForUser : {}", loggedUser);

        // Get the survey
        Optional<Survey> survey;
        if (loggedUser != null) {
            // Logged user, pick a new (not already submitted) survey
            survey = surveyRepository.findOneRandom(loggedUser.getId());
        } else {
            // Anonymous user, pick a random survey
            survey = surveyRepository.findOneRandom();
        }

        // Add previous submissions count
        survey.ifPresent(s -> {
            Map<Long, Map<Long, Long>> previousSubmissionsCount = new HashMap<>();
            for (SurveyQuestion q : s.getSurveyQuestions()) {
                previousSubmissionsCount.put(q.getId(), new HashMap<>());
                for (SurveyAnswer a : q.getSurveyAnswers()) {
                    long count = surveyResultQuestionAnswerRepository.countBySurveyAndQuestionAndAnswer(s, q.getId(), a.getId());
                    previousSubmissionsCount.get(q.getId()).put(a.getId(), count);
                }
            }
            s.setPreviousSubmissionsCount(previousSubmissionsCount);
        });

        return survey.map(surveyMapper::toDto);
    }
}
