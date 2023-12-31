package it.example.myopinionrocks.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.example.myopinionrocks.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SurveyAnswerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SurveyAnswer.class);
        SurveyAnswer surveyAnswer1 = new SurveyAnswer();
        surveyAnswer1.setId(1L);
        SurveyAnswer surveyAnswer2 = new SurveyAnswer();
        surveyAnswer2.setId(surveyAnswer1.getId());
        assertThat(surveyAnswer1).isEqualTo(surveyAnswer2);
        surveyAnswer2.setId(2L);
        assertThat(surveyAnswer1).isNotEqualTo(surveyAnswer2);
        surveyAnswer1.setId(null);
        assertThat(surveyAnswer1).isNotEqualTo(surveyAnswer2);
    }
}
