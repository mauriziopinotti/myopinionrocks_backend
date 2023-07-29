package it.example.myopinionrocks.service.mapper;

import it.example.myopinionrocks.domain.SurveyAnswer;
import it.example.myopinionrocks.domain.SurveyQuestion;
import it.example.myopinionrocks.domain.SurveyResult;
import it.example.myopinionrocks.domain.User;
import it.example.myopinionrocks.service.dto.SurveyAnswerDTO;
import it.example.myopinionrocks.service.dto.SurveyQuestionDTO;
import it.example.myopinionrocks.service.dto.SurveyResultDTO;
import it.example.myopinionrocks.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SurveyResult} and its DTO {@link SurveyResultDTO}.
 */
@Mapper(componentModel = "spring")
public interface SurveyResultMapper extends EntityMapper<SurveyResultDTO, SurveyResult> {
    @Mapping(target = "surveyQuestion", source = "surveyQuestion", qualifiedByName = "surveyQuestionId")
    @Mapping(target = "surveyAnswer", source = "surveyAnswer", qualifiedByName = "surveyAnswerId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    SurveyResultDTO toDto(SurveyResult s);

    @Named("surveyQuestionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SurveyQuestionDTO toDtoSurveyQuestionId(SurveyQuestion surveyQuestion);

    @Named("surveyAnswerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SurveyAnswerDTO toDtoSurveyAnswerId(SurveyAnswer surveyAnswer);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
