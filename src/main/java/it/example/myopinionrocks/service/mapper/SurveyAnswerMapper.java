package it.example.myopinionrocks.service.mapper;

import it.example.myopinionrocks.domain.SurveyAnswer;
import it.example.myopinionrocks.domain.SurveyQuestion;
import it.example.myopinionrocks.service.dto.SurveyAnswerDTO;
import it.example.myopinionrocks.service.dto.SurveyQuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SurveyAnswer} and its DTO {@link SurveyAnswerDTO}.
 */
@Mapper(componentModel = "spring")
public interface SurveyAnswerMapper extends EntityMapper<SurveyAnswerDTO, SurveyAnswer> {
    @Named("surveyQuestionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SurveyQuestionDTO toDtoSurveyQuestionId(SurveyQuestion surveyQuestion);
}
