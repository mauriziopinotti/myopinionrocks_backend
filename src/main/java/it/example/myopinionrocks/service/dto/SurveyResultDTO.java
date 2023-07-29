package it.example.myopinionrocks.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link it.example.myopinionrocks.domain.SurveyResult} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SurveyResultDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant datetime;

    private SurveyQuestionDTO surveyQuestion;

    private SurveyAnswerDTO surveyAnswer;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDatetime() {
        return datetime;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }

    public SurveyQuestionDTO getSurveyQuestion() {
        return surveyQuestion;
    }

    public void setSurveyQuestion(SurveyQuestionDTO surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
    }

    public SurveyAnswerDTO getSurveyAnswer() {
        return surveyAnswer;
    }

    public void setSurveyAnswer(SurveyAnswerDTO surveyAnswer) {
        this.surveyAnswer = surveyAnswer;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SurveyResultDTO)) {
            return false;
        }

        SurveyResultDTO surveyResultDTO = (SurveyResultDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, surveyResultDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SurveyResultDTO{" +
            "id=" + getId() +
            ", datetime='" + getDatetime() + "'" +
            ", surveyQuestion=" + getSurveyQuestion() +
            ", surveyAnswer=" + getSurveyAnswer() +
            ", user=" + getUser() +
            "}";
    }
}
