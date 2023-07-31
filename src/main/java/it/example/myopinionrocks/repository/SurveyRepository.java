package it.example.myopinionrocks.repository;

import it.example.myopinionrocks.domain.Survey;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Survey entity.
 * <p>
 * When extending this class, extend SurveyRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface SurveyRepository extends SurveyRepositoryWithBagRelationships, JpaRepository<Survey, Long> {
    default Optional<Survey> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Survey> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Survey> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    // FIXME: the RAND() function is not portable and only works in MySQL-ish
    @Query("SELECT s FROM Survey s ORDER BY RAND() LIMIT 1")
    Optional<Survey> findOneRandom();

    // FIXME: the RAND() function is not portable and only works in MySQL-ish
    @Query("SELECT s FROM Survey s "
        + "WHERE s.id NOT IN (SELECT r.survey FROM SurveyResult r WHERE r.user.id = :userId) "
        + "ORDER BY RAND() LIMIT 1")
    Optional<Survey> findOneRandom(@Param("userId") Long userId);

    Optional<Survey> findOneById(Long surveyId);
}
