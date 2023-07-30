package it.example.myopinionrocks.service.mapper;

import it.example.myopinionrocks.domain.*;
import it.example.myopinionrocks.service.dto.*;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link SurveyResult} and its DTO {@link SurveyResultDTO}.
 */
@Mapper(componentModel = "spring")
public interface SurveyResultMapper extends EntityMapper<SurveyResultDTO, SurveyResult> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "survey", source = "survey", qualifiedByName = "surveyId")
    SurveyResultDTO toDto(SurveyResult s);

    SurveyResult toEntity(SurveyResultDTO surveyResultDTO);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("surveyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SurveyDTO toDtoSurveyId(Survey survey);

    @Named("surveyQuestionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SurveyQuestionDTO toDtoSurveyQuestionId(SurveyQuestion surveyQuestion);

    @Named("surveyQuestionIdSet")
    default Set<SurveyQuestionDTO> toDtoSurveyQuestionIdSet(Set<SurveyQuestion> surveyQuestion) {
        return surveyQuestion.stream().map(this::toDtoSurveyQuestionId).collect(Collectors.toSet());
    }

    @Named("surveyAnswerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SurveyAnswerDTO toDtoSurveyAnswerId(SurveyAnswer surveyAnswer);

    @Named("surveyAnswerIdSet")
    default Set<SurveyAnswerDTO> toDtoSurveyAnswerIdSet(Set<SurveyAnswer> surveyAnswer) {
        return surveyAnswer.stream().map(this::toDtoSurveyAnswerId).collect(Collectors.toSet());
    }
}
