package it.example.myopinionrocks.service.mapper;

import it.example.myopinionrocks.domain.Survey;
import it.example.myopinionrocks.domain.SurveyAnswer;
import it.example.myopinionrocks.domain.SurveyQuestion;
import it.example.myopinionrocks.service.dto.SurveyAnswerDTO;
import it.example.myopinionrocks.service.dto.SurveyDTO;
import it.example.myopinionrocks.service.dto.SurveyQuestionDTO;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity {@link SurveyQuestion} and its DTO {@link SurveyQuestionDTO}.
 */
@Mapper(componentModel = "spring")
public interface SurveyQuestionMapper extends EntityMapper<SurveyQuestionDTO, SurveyQuestion> {
    @Named("surveyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SurveyDTO toDtoSurveyId(Survey survey);
}
