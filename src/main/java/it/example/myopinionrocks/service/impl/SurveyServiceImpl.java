package it.example.myopinionrocks.service.impl;

import it.example.myopinionrocks.domain.*;
import it.example.myopinionrocks.repository.SurveyRepository;
import it.example.myopinionrocks.repository.SurveyResultRepository;
import it.example.myopinionrocks.repository.UserRepository;
import it.example.myopinionrocks.service.SurveyService;
import it.example.myopinionrocks.service.dto.SurveyDTO;
import it.example.myopinionrocks.service.mapper.SurveyMapper;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Survey}.
 */
@Service
@Transactional
public class SurveyServiceImpl implements SurveyService {

    private final Logger log = LoggerFactory.getLogger(SurveyServiceImpl.class);

    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;
    private final UserRepository userRepository;
    private final SurveyResultRepository surveyResultRepository;

    public SurveyServiceImpl(SurveyRepository surveyRepository, SurveyMapper surveyMapper, UserRepository userRepository, SurveyResultRepository surveyResultRepository) {
        this.surveyRepository = surveyRepository;
        this.surveyMapper = surveyMapper;
        this.userRepository = userRepository;
        this.surveyResultRepository = surveyResultRepository;
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

    @Override
    @Transactional(readOnly = true)
    public Optional<SurveyDTO> findOne(Long id) {
        log.debug("Request to get Survey : {}", id);
        return surveyRepository.findById(id).map(surveyMapper::toDto);
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
        Optional<Survey> survey = Optional.empty();
        if (loggedUser != null) {
            // Logged user, pick a new (not already submitted) survey
            survey = surveyRepository.findOneRandom(loggedUser.getId());
        } else {
            // Anonymous user, pick a random survey
            survey = surveyRepository.findOneRandom();
        }

        // Add result count
        survey.ifPresent(s -> {
            // Get all results of this survey
            List<SurveyResult> results = surveyResultRepository.findAllBySurvey(s);

            // Count answers
            Map<SurveyAnswer, Long> answersCount = new HashMap<>();
            for (SurveyResult result : results) {
                long count = answersCount.getOrDefault(result.getSurveyAnswer(), 0L);
                answersCount.put(result.getSurveyAnswer(), count + 1);
            }

            for (SurveyQuestion q : s.getSurveyQuestions()) {
                for (SurveyAnswer a : q.getSurveyAnswers()) {
                    a.setResultCount(answersCount.getOrDefault(a, 0L));
                }
            }
        });

        return survey.map(surveyMapper::toDto);
    }
}
