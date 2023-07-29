package it.example.myopinionrocks.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SurveyAnswer.
 */
@Entity
@Table(name = "survey_answer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SurveyAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties(value = {"surveyAnswers", "survey", "questions"}, allowSetters = true)
    private SurveyQuestion question;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyAnswer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = {"surveyQuestion", "surveyAnswer", "user"}, allowSetters = true)
    private Set<SurveyResult> answers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Transient
    private Long resultCount;

    public Long getId() {
        return this.id;
    }

    public SurveyAnswer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public SurveyAnswer title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SurveyQuestion getQuestion() {
        return this.question;
    }

    public void setQuestion(SurveyQuestion surveyQuestion) {
        this.question = surveyQuestion;
    }

    public SurveyAnswer question(SurveyQuestion surveyQuestion) {
        this.setQuestion(surveyQuestion);
        return this;
    }

    public Set<SurveyResult> getAnswers() {
        return this.answers;
    }

    public void setAnswers(Set<SurveyResult> surveyResults) {
        if (this.answers != null) {
            this.answers.forEach(i -> i.setSurveyAnswer(null));
        }
        if (surveyResults != null) {
            surveyResults.forEach(i -> i.setSurveyAnswer(this));
        }
        this.answers = surveyResults;
    }

    public SurveyAnswer answers(Set<SurveyResult> surveyResults) {
        this.setAnswers(surveyResults);
        return this;
    }

    public SurveyAnswer addAnswer(SurveyResult surveyResult) {
        this.answers.add(surveyResult);
        surveyResult.setSurveyAnswer(this);
        return this;
    }

    public SurveyAnswer removeAnswer(SurveyResult surveyResult) {
        this.answers.remove(surveyResult);
        surveyResult.setSurveyAnswer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    public Long getResultCount() {
        return resultCount;
    }

    public void setResultCount(Long resultCount) {
        this.resultCount = resultCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SurveyAnswer)) {
            return false;
        }
        return id != null && id.equals(((SurveyAnswer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SurveyAnswer{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
