package it.example.myopinionrocks.repository;

import it.example.myopinionrocks.domain.SurveyResult;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface SurveyResultRepositoryWithBagRelationships {
    Optional<SurveyResult> fetchBagRelationships(Optional<SurveyResult> surveyResult);

    List<SurveyResult> fetchBagRelationships(List<SurveyResult> surveyResults);

    Page<SurveyResult> fetchBagRelationships(Page<SurveyResult> surveyResults);
}
