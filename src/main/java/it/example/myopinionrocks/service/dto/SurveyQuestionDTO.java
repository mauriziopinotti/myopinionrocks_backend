package it.example.myopinionrocks.service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link it.example.myopinionrocks.domain.SurveyQuestion} entity.
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SurveyQuestionDTO implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    private String title;

    private Set<SurveyAnswerDTO> surveyAnswers = new HashSet<>();
}
