package it.example.myopinionrocks.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;
import java.io.Serializable;

/**
 * A SurveyResult.
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rel_survey_result__survey_question_answer")
@IdClass(SurveyQuestionAnswerResult.SurveyQuestionAnswerResultKey.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SurveyQuestionAnswerResult implements Serializable {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class SurveyQuestionAnswerResultKey implements Serializable {
        private Long surveyResultId;
        private Long surveyQuestionId;
        private Long surveyAnswerId;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "survey_result_id", nullable = false)
    private Long surveyResultId;

    @Id
    @NotNull
    @Column(name = "survey_question_id", nullable = false)
    private Long surveyQuestionId;

    @Id
    @NotNull
    @Column(name = "survey_answer_id", nullable = false)
    private Long surveyAnswerId;
}
