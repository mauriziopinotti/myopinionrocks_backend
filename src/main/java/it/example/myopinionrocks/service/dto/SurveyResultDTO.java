package it.example.myopinionrocks.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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

    private UserDTO user;

    private SurveyDTO survey;

    private Set<SurveyQuestionDTO> surveyQuestions = new HashSet<>();

    private Set<SurveyAnswerDTO> surveyAnswers = new HashSet<>();
}
