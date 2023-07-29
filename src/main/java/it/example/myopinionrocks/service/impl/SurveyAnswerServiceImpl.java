package it.example.myopinionrocks.service.impl;

import it.example.myopinionrocks.domain.SurveyAnswer;
import it.example.myopinionrocks.repository.SurveyAnswerRepository;
import it.example.myopinionrocks.service.SurveyAnswerService;
import it.example.myopinionrocks.service.dto.SurveyAnswerDTO;
import it.example.myopinionrocks.service.mapper.SurveyAnswerMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SurveyAnswer}.
 */
@Service
@Transactional
public class SurveyAnswerServiceImpl implements SurveyAnswerService {

    private final Logger log = LoggerFactory.getLogger(SurveyAnswerServiceImpl.class);

    private final SurveyAnswerRepository surveyAnswerRepository;

    private final SurveyAnswerMapper surveyAnswerMapper;

    public SurveyAnswerServiceImpl(SurveyAnswerRepository surveyAnswerRepository, SurveyAnswerMapper surveyAnswerMapper) {
        this.surveyAnswerRepository = surveyAnswerRepository;
        this.surveyAnswerMapper = surveyAnswerMapper;
    }

    @Override
    public SurveyAnswerDTO save(SurveyAnswerDTO surveyAnswerDTO) {
        log.debug("Request to save SurveyAnswer : {}", surveyAnswerDTO);
        SurveyAnswer surveyAnswer = surveyAnswerMapper.toEntity(surveyAnswerDTO);
        surveyAnswer = surveyAnswerRepository.save(surveyAnswer);
        return surveyAnswerMapper.toDto(surveyAnswer);
    }

    @Override
    public SurveyAnswerDTO update(SurveyAnswerDTO surveyAnswerDTO) {
        log.debug("Request to update SurveyAnswer : {}", surveyAnswerDTO);
        SurveyAnswer surveyAnswer = surveyAnswerMapper.toEntity(surveyAnswerDTO);
        surveyAnswer = surveyAnswerRepository.save(surveyAnswer);
        return surveyAnswerMapper.toDto(surveyAnswer);
    }

    @Override
    public Optional<SurveyAnswerDTO> partialUpdate(SurveyAnswerDTO surveyAnswerDTO) {
        log.debug("Request to partially update SurveyAnswer : {}", surveyAnswerDTO);

        return surveyAnswerRepository
            .findById(surveyAnswerDTO.getId())
            .map(existingSurveyAnswer -> {
                surveyAnswerMapper.partialUpdate(existingSurveyAnswer, surveyAnswerDTO);

                return existingSurveyAnswer;
            })
            .map(surveyAnswerRepository::save)
            .map(surveyAnswerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SurveyAnswerDTO> findAll() {
        log.debug("Request to get all SurveyAnswers");
        return surveyAnswerRepository.findAll().stream().map(surveyAnswerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SurveyAnswerDTO> findOne(Long id) {
        log.debug("Request to get SurveyAnswer : {}", id);
        return surveyAnswerRepository.findById(id).map(surveyAnswerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SurveyAnswer : {}", id);
        surveyAnswerRepository.deleteById(id);
    }
}
