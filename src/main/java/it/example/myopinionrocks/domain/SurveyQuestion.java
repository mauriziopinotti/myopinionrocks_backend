package it.example.myopinionrocks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SurveyQuestion.
 */
@Entity
@Table(name = "survey_question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SurveyQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "rel_survey_question__survey_answer",
            joinColumns = @JoinColumn(name = "survey_question_id"),
            inverseJoinColumns = @JoinColumn(name = "survey_answer_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = {"questions", "answers"}, allowSetters = true)
    private Set<SurveyAnswer> surveyAnswers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "surveyQuestions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = {"surveyQuestions", "surveys"}, allowSetters = true)
    private Set<Survey> surveys = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SurveyQuestion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public SurveyQuestion title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<SurveyAnswer> getSurveyAnswers() {
        return this.surveyAnswers;
    }

    public void setSurveyAnswers(Set<SurveyAnswer> surveyAnswers) {
        this.surveyAnswers = surveyAnswers;
    }

    public SurveyQuestion surveyAnswers(Set<SurveyAnswer> surveyAnswers) {
        this.setSurveyAnswers(surveyAnswers);
        return this;
    }

    public Set<Survey> getSurveys() {
        return this.surveys;
    }

    public void setSurveys(Set<Survey> surveys) {
        if (this.surveys != null) {
            this.surveys.forEach(i -> i.removeSurveyQuestion(this));
        }
        if (surveys != null) {
            surveys.forEach(i -> i.addSurveyQuestion(this));
        }
        this.surveys = surveys;
    }

    public SurveyQuestion surveys(Set<Survey> surveys) {
        this.setSurveys(surveys);
        return this;
    }

    public SurveyQuestion addSurvey(Survey survey) {
        this.surveys.add(survey);
        survey.getSurveyQuestions().add(this);
        return this;
    }

    public SurveyQuestion removeSurvey(Survey survey) {
        this.surveys.remove(survey);
        survey.getSurveyQuestions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SurveyQuestion)) {
            return false;
        }
        return id != null && id.equals(((SurveyQuestion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SurveyQuestion{" +
                "id=" + getId() +
                ", title='" + getTitle() + "'" +
                "}";
    }
}
