package it.example.myopinionrocks.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link it.example.myopinionrocks.domain.Survey} entity.
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SurveyDTO implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    private String title;

    private List<SurveyQuestionDTO> surveyQuestions;
}
