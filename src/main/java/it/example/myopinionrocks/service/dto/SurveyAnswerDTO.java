package it.example.myopinionrocks.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.example.myopinionrocks.domain.SurveyAnswer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SurveyAnswerDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    private SurveyQuestionDTO question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SurveyQuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(SurveyQuestionDTO question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SurveyAnswerDTO)) {
            return false;
        }

        SurveyAnswerDTO surveyAnswerDTO = (SurveyAnswerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, surveyAnswerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SurveyAnswerDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", question=" + getQuestion() +
            "}";
    }
}
