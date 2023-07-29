package it.example.myopinionrocks.service.dto;

import it.example.myopinionrocks.domain.SurveyAnswer;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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

    private List<SurveyAnswerDTO> surveyAnswers;
}
