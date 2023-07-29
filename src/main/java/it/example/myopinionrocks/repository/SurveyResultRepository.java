package it.example.myopinionrocks.repository;

import it.example.myopinionrocks.domain.SurveyResult;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SurveyResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurveyResultRepository extends JpaRepository<SurveyResult, Long> {
    @Query("select surveyResult from SurveyResult surveyResult where surveyResult.user.login = ?#{principal.username}")
    List<SurveyResult> findByUserIsCurrentUser();
}
