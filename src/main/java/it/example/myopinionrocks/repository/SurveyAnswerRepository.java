package it.example.myopinionrocks.repository;

import it.example.myopinionrocks.domain.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the SurveyAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {
    Optional<SurveyAnswer> findOneById(Long surveyAnswerId);
}
