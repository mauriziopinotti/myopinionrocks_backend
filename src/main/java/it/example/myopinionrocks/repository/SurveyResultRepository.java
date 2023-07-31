package it.example.myopinionrocks.repository;

import it.example.myopinionrocks.domain.Survey;
import it.example.myopinionrocks.domain.SurveyResult;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SurveyResult entity.
 *
 * When extending this class, extend SurveyResultRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface SurveyResultRepository extends SurveyResultRepositoryWithBagRelationships, JpaRepository<SurveyResult, Long> {
    @Query("select surveyResult from SurveyResult surveyResult where surveyResult.user.login = ?#{principal.username}")
    List<SurveyResult> findByUserIsCurrentUser();

    default Optional<SurveyResult> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<SurveyResult> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<SurveyResult> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    List<SurveyResult> findAllBySurvey(Survey survey);

    int countByUserIdAndSurveyId(Long userId, Long surveyId);
}
