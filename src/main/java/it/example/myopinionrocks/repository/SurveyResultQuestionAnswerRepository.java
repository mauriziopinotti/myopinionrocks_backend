package it.example.myopinionrocks.repository;

import it.example.myopinionrocks.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResultQuestionAnswerRepository extends JpaRepository<SurveyQuestionAnswerResult, Long> {

    @Query("SELECT COUNT(r) FROM SurveyQuestionAnswerResult r "
        + "INNER JOIN SurveyResult sr ON r.surveyResultId = sr.id "
        + "WHERE sr.survey = :survey AND r.surveyQuestionId = :questionId and r.surveyAnswerId = :answerId")
    long countBySurveyAndQuestionAndAnswer(@Param("survey") Survey survey, @Param("questionId") Long questionId, @Param("answerId") Long answerId);
}
