package it.example.myopinionrocks.service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link it.example.myopinionrocks.domain.SurveyResult} entity.
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SurveyResultDTO implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    private Instant datetime;

    private SurveyQuestionDTO surveyQuestion;

    private SurveyAnswerDTO surveyAnswer;

    private UserDTO user;
}
