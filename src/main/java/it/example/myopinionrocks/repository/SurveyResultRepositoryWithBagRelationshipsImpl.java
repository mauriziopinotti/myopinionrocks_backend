package it.example.myopinionrocks.repository;

import it.example.myopinionrocks.domain.SurveyResult;
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
public class SurveyResultRepositoryWithBagRelationshipsImpl implements SurveyResultRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SurveyResult> fetchBagRelationships(Optional<SurveyResult> surveyResult) {
        return surveyResult.map(this::fetchSurveyQuestions).map(this::fetchSurveyAnswers);
    }

    @Override
    public Page<SurveyResult> fetchBagRelationships(Page<SurveyResult> surveyResults) {
        return new PageImpl<>(
            fetchBagRelationships(surveyResults.getContent()),
            surveyResults.getPageable(),
            surveyResults.getTotalElements()
        );
    }

    @Override
    public List<SurveyResult> fetchBagRelationships(List<SurveyResult> surveyResults) {
        return Optional.of(surveyResults).map(this::fetchSurveyQuestions).map(this::fetchSurveyAnswers).orElse(Collections.emptyList());
    }

    SurveyResult fetchSurveyQuestions(SurveyResult result) {
        return entityManager
            .createQuery(
                "select surveyResult from SurveyResult surveyResult left join fetch surveyResult.surveyQuestions where surveyResult.id = :id",
                SurveyResult.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<SurveyResult> fetchSurveyQuestions(List<SurveyResult> surveyResults) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, surveyResults.size()).forEach(index -> order.put(surveyResults.get(index).getId(), index));
        List<SurveyResult> result = entityManager
            .createQuery(
                "select surveyResult from SurveyResult surveyResult left join fetch surveyResult.surveyQuestions where surveyResult in :surveyResults",
                SurveyResult.class
            )
            .setParameter("surveyResults", surveyResults)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    SurveyResult fetchSurveyAnswers(SurveyResult result) {
        return entityManager
            .createQuery(
                "select surveyResult from SurveyResult surveyResult left join fetch surveyResult.surveyAnswers where surveyResult.id = :id",
                SurveyResult.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<SurveyResult> fetchSurveyAnswers(List<SurveyResult> surveyResults) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, surveyResults.size()).forEach(index -> order.put(surveyResults.get(index).getId(), index));
        List<SurveyResult> result = entityManager
            .createQuery(
                "select surveyResult from SurveyResult surveyResult left join fetch surveyResult.surveyAnswers where surveyResult in :surveyResults",
                SurveyResult.class
            )
            .setParameter("surveyResults", surveyResults)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
