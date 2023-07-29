package it.example.myopinionrocks.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.example.myopinionrocks.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SurveyQuestionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SurveyQuestion.class);
        SurveyQuestion surveyQuestion1 = new SurveyQuestion();
        surveyQuestion1.setId(1L);
        SurveyQuestion surveyQuestion2 = new SurveyQuestion();
        surveyQuestion2.setId(surveyQuestion1.getId());
        assertThat(surveyQuestion1).isEqualTo(surveyQuestion2);
        surveyQuestion2.setId(2L);
        assertThat(surveyQuestion1).isNotEqualTo(surveyQuestion2);
        surveyQuestion1.setId(null);
        assertThat(surveyQuestion1).isNotEqualTo(surveyQuestion2);
    }
}
