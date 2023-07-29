package it.example.myopinionrocks.service.impl;

import it.example.myopinionrocks.domain.Survey;
import it.example.myopinionrocks.repository.SurveyRepository;
import it.example.myopinionrocks.service.SurveyService;
import it.example.myopinionrocks.service.dto.SurveyDTO;
import it.example.myopinionrocks.service.mapper.SurveyMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public SurveyServiceImpl(SurveyRepository surveyRepository, SurveyMapper surveyMapper) {
        this.surveyRepository = surveyRepository;
        this.surveyMapper = surveyMapper;
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
}
