package it.example.myopinionrocks.repository;

import it.example.myopinionrocks.domain.SurveyQuestion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SurveyQuestionRepositoryWithBagRelationshipsImpl implements SurveyQuestionRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SurveyQuestion> fetchBagRelationships(Optional<SurveyQuestion> surveyQuestion) {
        return surveyQuestion.map(this::fetchSurveyAnswers);
    }

    @Override
    public Page<SurveyQuestion> fetchBagRelationships(Page<SurveyQuestion> surveyQuestions) {
        return new PageImpl<>(
            fetchBagRelationships(surveyQuestions.getContent()),
            surveyQuestions.getPageable(),
            surveyQuestions.getTotalElements()
        );
    }

    @Override
    public List<SurveyQuestion> fetchBagRelationships(List<SurveyQuestion> surveyQuestions) {
        return Optional.of(surveyQuestions).map(this::fetchSurveyAnswers).orElse(Collections.emptyList());
    }

    SurveyQuestion fetchSurveyAnswers(SurveyQuestion result) {
        return entityManager
            .createQuery(
                "select surveyQuestion from SurveyQuestion surveyQuestion left join fetch surveyQuestion.surveyAnswers where surveyQuestion.id = :id",
                SurveyQuestion.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<SurveyQuestion> fetchSurveyAnswers(List<SurveyQuestion> surveyQuestions) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, surveyQuestions.size()).forEach(index -> order.put(surveyQuestions.get(index).getId(), index));
        List<SurveyQuestion> result = entityManager
            .createQuery(
                "select surveyQuestion from SurveyQuestion surveyQuestion left join fetch surveyQuestion.surveyAnswers where surveyQuestion in :surveyQuestions",
                SurveyQuestion.class
            )
            .setParameter("surveyQuestions", surveyQuestions)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
