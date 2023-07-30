package it.example.myopinionrocks.service.mapper;

import it.example.myopinionrocks.domain.SurveyAnswer;
import it.example.myopinionrocks.service.dto.SurveyAnswerDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link SurveyAnswer} and its DTO {@link SurveyAnswerDTO}.
 */
@Mapper(componentModel = "spring")
public interface SurveyAnswerMapper extends EntityMapper<SurveyAnswerDTO, SurveyAnswer> {
}
