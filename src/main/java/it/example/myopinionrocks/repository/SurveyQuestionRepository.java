package it.example.myopinionrocks.repository;

import it.example.myopinionrocks.domain.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the SurveyQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, Long> {
    Optional<SurveyQuestion> findOneById(Long surveyQuestionId);
}
