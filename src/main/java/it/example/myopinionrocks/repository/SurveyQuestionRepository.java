package it.example.myopinionrocks.repository;

import it.example.myopinionrocks.domain.SurveyQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the SurveyQuestion entity.
 * <p>
 * When extending this class, extend SurveyQuestionRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface SurveyQuestionRepository extends SurveyQuestionRepositoryWithBagRelationships, JpaRepository<SurveyQuestion, Long> {
    default Optional<SurveyQuestion> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<SurveyQuestion> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<SurveyQuestion> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    Optional<SurveyQuestion> findOneById(Long surveyQuestionId);
}
