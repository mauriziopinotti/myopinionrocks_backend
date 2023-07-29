package it.example.myopinionrocks.repository;

import it.example.myopinionrocks.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Survey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

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
