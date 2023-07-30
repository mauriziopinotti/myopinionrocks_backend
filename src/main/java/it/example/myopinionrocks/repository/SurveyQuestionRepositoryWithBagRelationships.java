package it.example.myopinionrocks.repository;

import it.example.myopinionrocks.domain.SurveyQuestion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface SurveyQuestionRepositoryWithBagRelationships {
    Optional<SurveyQuestion> fetchBagRelationships(Optional<SurveyQuestion> surveyQuestion);

    List<SurveyQuestion> fetchBagRelationships(List<SurveyQuestion> surveyQuestions);

    Page<SurveyQuestion> fetchBagRelationships(Page<SurveyQuestion> surveyQuestions);
}
