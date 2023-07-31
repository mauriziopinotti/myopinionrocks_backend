package it.example.myopinionrocks.service.mapper;

import it.example.myopinionrocks.domain.Survey;
import it.example.myopinionrocks.service.dto.SurveyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Survey} and its DTO {@link SurveyDTO}.
 */
@Mapper(componentModel = "spring")
public interface SurveyMapper extends EntityMapper<SurveyDTO, Survey> {
    @Mapping(target = "surveyQuestions", source = "surveyQuestions")
    SurveyDTO toDto(Survey s);

    @Mapping(target = "removeSurveyQuestion", ignore = true)
    Survey toEntity(SurveyDTO surveyDTO);
}
