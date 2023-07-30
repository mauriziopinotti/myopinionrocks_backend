package it.example.myopinionrocks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Survey.
 */
@Entity
@Table(name = "survey")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Survey implements Serializable {

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
            name = "rel_survey__survey_question",
            joinColumns = @JoinColumn(name = "survey_id"),
            inverseJoinColumns = @JoinColumn(name = "survey_question_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = {"surveyAnswers", "surveys", "questions"}, allowSetters = true)
    private Set<SurveyQuestion> surveyQuestions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "survey")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = {"user", "survey", "surveyQuestions", "surveyAnswers"}, allowSetters = true)
    private Set<SurveyResult> surveys = new HashSet<>();

    // questionId => answerId => count
    @Transient
    private Map<Long, Map<Long, Long>> previousSubmissionsCount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Survey id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Survey title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<SurveyQuestion> getSurveyQuestions() {
        return this.surveyQuestions;
    }

    public void setSurveyQuestions(Set<SurveyQuestion> surveyQuestions) {
        this.surveyQuestions = surveyQuestions;
    }

    public Survey surveyQuestions(Set<SurveyQuestion> surveyQuestions) {
        this.setSurveyQuestions(surveyQuestions);
        return this;
    }

    public Survey addSurveyQuestion(SurveyQuestion surveyQuestion) {
        this.surveyQuestions.add(surveyQuestion);
        surveyQuestion.getSurveys().add(this);
        return this;
    }

    public Survey removeSurveyQuestion(SurveyQuestion surveyQuestion) {
        this.surveyQuestions.remove(surveyQuestion);
        surveyQuestion.getSurveys().remove(this);
        return this;
    }

    public Set<SurveyResult> getSurveys() {
        return this.surveys;
    }

    public void setSurveys(Set<SurveyResult> surveyResults) {
        if (this.surveys != null) {
            this.surveys.forEach(i -> i.setSurvey(null));
        }
        if (surveyResults != null) {
            surveyResults.forEach(i -> i.setSurvey(this));
        }
        this.surveys = surveyResults;
    }

    public Survey surveys(Set<SurveyResult> surveyResults) {
        this.setSurveys(surveyResults);
        return this;
    }

    public Survey addSurvey(SurveyResult surveyResult) {
        this.surveys.add(surveyResult);
        surveyResult.setSurvey(this);
        return this;
    }

    public Survey removeSurvey(SurveyResult surveyResult) {
        this.surveys.remove(surveyResult);
        surveyResult.setSurvey(null);
        return this;
    }

    public Map<Long, Map<Long, Long>> getPreviousSubmissionsCount() {
        return previousSubmissionsCount;
    }

    public void setPreviousSubmissionsCount(Map<Long, Map<Long, Long>> previousSubmissionsCount) {
        this.previousSubmissionsCount = previousSubmissionsCount;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Survey)) {
            return false;
        }
        return id != null && id.equals(((Survey) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Survey{" +
                "id=" + getId() +
                ", title='" + getTitle() + "'" +
                "}";
    }
}
