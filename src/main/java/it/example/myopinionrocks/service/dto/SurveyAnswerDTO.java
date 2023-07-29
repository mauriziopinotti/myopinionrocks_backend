package it.example.myopinionrocks.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * A DTO for the {@link it.example.myopinionrocks.domain.SurveyAnswer} entity.
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SurveyAnswerDTO implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    private String title;

    private Long resultCount;
}
