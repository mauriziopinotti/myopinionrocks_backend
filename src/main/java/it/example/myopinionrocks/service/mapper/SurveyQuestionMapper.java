package it.example.myopinionrocks.service.mapper;

import it.example.myopinionrocks.domain.SurveyAnswer;
import it.example.myopinionrocks.domain.SurveyQuestion;
import it.example.myopinionrocks.service.dto.SurveyAnswerDTO;
import it.example.myopinionrocks.service.dto.SurveyQuestionDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SurveyQuestion} and its DTO {@link SurveyQuestionDTO}.
 */
@Mapper(componentModel = "spring")
public interface SurveyQuestionMapper extends EntityMapper<SurveyQuestionDTO, SurveyQuestion> {
    @Mapping(target = "surveyAnswers", source = "surveyAnswers", qualifiedByName = "surveyAnswerIdSet")
    SurveyQuestionDTO toDto(SurveyQuestion s);

    SurveyQuestion toEntity(SurveyQuestionDTO surveyQuestionDTO);

    @Named("surveyAnswerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SurveyAnswerDTO toDtoSurveyAnswerId(SurveyAnswer surveyAnswer);

    @Named("surveyAnswerIdSet")
    default Set<SurveyAnswerDTO> toDtoSurveyAnswerIdSet(Set<SurveyAnswer> surveyAnswer) {
        return surveyAnswer.stream().map(this::toDtoSurveyAnswerId).collect(Collectors.toSet());
    }
}
