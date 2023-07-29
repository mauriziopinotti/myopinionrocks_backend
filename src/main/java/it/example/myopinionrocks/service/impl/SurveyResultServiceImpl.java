package it.example.myopinionrocks.service.impl;

import it.example.myopinionrocks.domain.SurveyResult;
import it.example.myopinionrocks.repository.SurveyResultRepository;
import it.example.myopinionrocks.service.SurveyResultService;
import it.example.myopinionrocks.service.dto.SurveyResultDTO;
import it.example.myopinionrocks.service.mapper.SurveyResultMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public SurveyResultServiceImpl(SurveyResultRepository surveyResultRepository, SurveyResultMapper surveyResultMapper) {
        this.surveyResultRepository = surveyResultRepository;
        this.surveyResultMapper = surveyResultMapper;
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
    public void delete(Long id) {
        log.debug("Request to delete SurveyResult : {}", id);
        surveyResultRepository.deleteById(id);
    }
}
