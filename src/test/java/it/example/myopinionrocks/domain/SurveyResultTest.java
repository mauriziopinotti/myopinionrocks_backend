package it.example.myopinionrocks.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.example.myopinionrocks.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SurveyResultTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SurveyResult.class);
        SurveyResult surveyResult1 = new SurveyResult();
        surveyResult1.setId(1L);
        SurveyResult surveyResult2 = new SurveyResult();
        surveyResult2.setId(surveyResult1.getId());
        assertThat(surveyResult1).isEqualTo(surveyResult2);
        surveyResult2.setId(2L);
        assertThat(surveyResult1).isNotEqualTo(surveyResult2);
        surveyResult1.setId(null);
        assertThat(surveyResult1).isNotEqualTo(surveyResult2);
    }
}
