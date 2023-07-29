package it.example.myopinionrocks.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "question", "answers" }, allowSetters = true)
    private Set<SurveyAnswer> surveyAnswers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties(value = { "surveyQuestions" }, allowSetters = true)
    private Survey survey;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyQuestion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "surveyQuestion", "surveyAnswer", "user" }, allowSetters = true)
    private Set<SurveyResult> questions = new HashSet<>();

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
        if (this.surveyAnswers != null) {
            this.surveyAnswers.forEach(i -> i.setQuestion(null));
        }
        if (surveyAnswers != null) {
            surveyAnswers.forEach(i -> i.setQuestion(this));
        }
        this.surveyAnswers = surveyAnswers;
    }

    public SurveyQuestion surveyAnswers(Set<SurveyAnswer> surveyAnswers) {
        this.setSurveyAnswers(surveyAnswers);
        return this;
    }

    public SurveyQuestion addSurveyAnswer(SurveyAnswer surveyAnswer) {
        this.surveyAnswers.add(surveyAnswer);
        surveyAnswer.setQuestion(this);
        return this;
    }

    public SurveyQuestion removeSurveyAnswer(SurveyAnswer surveyAnswer) {
        this.surveyAnswers.remove(surveyAnswer);
        surveyAnswer.setQuestion(null);
        return this;
    }

    public Survey getSurvey() {
        return this.survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public SurveyQuestion survey(Survey survey) {
        this.setSurvey(survey);
        return this;
    }

    public Set<SurveyResult> getQuestions() {
        return this.questions;
    }

    public void setQuestions(Set<SurveyResult> surveyResults) {
        if (this.questions != null) {
            this.questions.forEach(i -> i.setSurveyQuestion(null));
        }
        if (surveyResults != null) {
            surveyResults.forEach(i -> i.setSurveyQuestion(this));
        }
        this.questions = surveyResults;
    }

    public SurveyQuestion questions(Set<SurveyResult> surveyResults) {
        this.setQuestions(surveyResults);
        return this;
    }

    public SurveyQuestion addQuestion(SurveyResult surveyResult) {
        this.questions.add(surveyResult);
        surveyResult.setSurveyQuestion(this);
        return this;
    }

    public SurveyQuestion removeQuestion(SurveyResult surveyResult) {
        this.questions.remove(surveyResult);
        surveyResult.setSurveyQuestion(null);
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
