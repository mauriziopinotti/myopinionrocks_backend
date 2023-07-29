package it.example.myopinionrocks.repository;

import it.example.myopinionrocks.domain.Survey;
import it.example.myopinionrocks.domain.SurveyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the SurveyResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurveyResultRepository extends JpaRepository<SurveyResult, Long> {
    @Query("select surveyResult from SurveyResult surveyResult where surveyResult.user.login = ?#{principal.username}")
    List<SurveyResult> findByUserIsCurrentUser();

    List<SurveyResult> findAllBySurvey(Survey survey);

    int countByUserIdAndSurveyId(Long userId, Long surveyId);
}
