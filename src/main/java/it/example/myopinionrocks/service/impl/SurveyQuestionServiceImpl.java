package it.example.myopinionrocks.service.impl;

import it.example.myopinionrocks.domain.SurveyQuestion;
import it.example.myopinionrocks.repository.SurveyQuestionRepository;
import it.example.myopinionrocks.service.SurveyQuestionService;
import it.example.myopinionrocks.service.dto.SurveyQuestionDTO;
import it.example.myopinionrocks.service.mapper.SurveyQuestionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SurveyQuestion}.
 */
@Service
@Transactional
public class SurveyQuestionServiceImpl implements SurveyQuestionService {

    private final Logger log = LoggerFactory.getLogger(SurveyQuestionServiceImpl.class);

    private final SurveyQuestionRepository surveyQuestionRepository;

    private final SurveyQuestionMapper surveyQuestionMapper;

    public SurveyQuestionServiceImpl(SurveyQuestionRepository surveyQuestionRepository, SurveyQuestionMapper surveyQuestionMapper) {
        this.surveyQuestionRepository = surveyQuestionRepository;
        this.surveyQuestionMapper = surveyQuestionMapper;
    }

    @Override
    public SurveyQuestionDTO save(SurveyQuestionDTO surveyQuestionDTO) {
        log.debug("Request to save SurveyQuestion : {}", surveyQuestionDTO);
        SurveyQuestion surveyQuestion = surveyQuestionMapper.toEntity(surveyQuestionDTO);
        surveyQuestion = surveyQuestionRepository.save(surveyQuestion);
        return surveyQuestionMapper.toDto(surveyQuestion);
    }

    @Override
    public SurveyQuestionDTO update(SurveyQuestionDTO surveyQuestionDTO) {
        log.debug("Request to update SurveyQuestion : {}", surveyQuestionDTO);
        SurveyQuestion surveyQuestion = surveyQuestionMapper.toEntity(surveyQuestionDTO);
        surveyQuestion = surveyQuestionRepository.save(surveyQuestion);
        return surveyQuestionMapper.toDto(surveyQuestion);
    }

    @Override
    public Optional<SurveyQuestionDTO> partialUpdate(SurveyQuestionDTO surveyQuestionDTO) {
        log.debug("Request to partially update SurveyQuestion : {}", surveyQuestionDTO);

        return surveyQuestionRepository
            .findById(surveyQuestionDTO.getId())
            .map(existingSurveyQuestion -> {
                surveyQuestionMapper.partialUpdate(existingSurveyQuestion, surveyQuestionDTO);

                return existingSurveyQuestion;
            })
            .map(surveyQuestionRepository::save)
            .map(surveyQuestionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SurveyQuestionDTO> findAll() {
        log.debug("Request to get all SurveyQuestions");
        return surveyQuestionRepository
            .findAll()
            .stream()
            .map(surveyQuestionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SurveyQuestionDTO> findOne(Long id) {
        log.debug("Request to get SurveyQuestion : {}", id);
        return surveyQuestionRepository.findById(id).map(surveyQuestionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SurveyQuestion : {}", id);
        surveyQuestionRepository.deleteById(id);
    }
}
