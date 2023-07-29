package it.example.myopinionrocks.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.example.myopinionrocks.domain.SurveyQuestion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SurveyQuestionDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    private SurveyDTO survey;

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

    public SurveyDTO getSurvey() {
        return survey;
    }

    public void setSurvey(SurveyDTO survey) {
        this.survey = survey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SurveyQuestionDTO)) {
            return false;
        }

        SurveyQuestionDTO surveyQuestionDTO = (SurveyQuestionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, surveyQuestionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SurveyQuestionDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", survey=" + getSurvey() +
            "}";
    }
}
