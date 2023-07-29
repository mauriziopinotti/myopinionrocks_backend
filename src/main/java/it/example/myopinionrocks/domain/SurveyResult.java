package it.example.myopinionrocks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SurveyResult.
 */
@Entity
@Table(name = "survey_result")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SurveyResult implements Serializable {

    public static final String ENTITY_NAME = "survey-result";

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "datetime", nullable = false)
    private Instant datetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"surveyQuestions"}, allowSetters = true)
    private Survey survey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"surveyAnswers", "survey", "questions"}, allowSetters = true)
    private SurveyQuestion surveyQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"question", "answers"}, allowSetters = true)
    private SurveyAnswer surveyAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SurveyResult id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDatetime() {
        return this.datetime;
    }

    public SurveyResult datetime(Instant datetime) {
        this.setDatetime(datetime);
        return this;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }

    public SurveyQuestion getSurveyQuestion() {
        return this.surveyQuestion;
    }

    public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
    }

    public SurveyResult surveyQuestion(SurveyQuestion surveyQuestion) {
        this.setSurveyQuestion(surveyQuestion);
        return this;
    }

    public SurveyAnswer getSurveyAnswer() {
        return this.surveyAnswer;
    }

    public void setSurveyAnswer(SurveyAnswer surveyAnswer) {
        this.surveyAnswer = surveyAnswer;
    }

    public SurveyResult surveyAnswer(SurveyAnswer surveyAnswer) {
        this.setSurveyAnswer(surveyAnswer);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SurveyResult user(User user) {
        this.setUser(user);
        return this;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SurveyResult)) {
            return false;
        }
        return id != null && id.equals(((SurveyResult) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SurveyResult{" +
            "id=" + getId() +
            ", datetime='" + getDatetime() + "'" +
            "}";
    }
}
