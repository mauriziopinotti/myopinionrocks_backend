package it.example.myopinionrocks.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.example.myopinionrocks.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SurveyAnswerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SurveyAnswerDTO.class);
        SurveyAnswerDTO surveyAnswerDTO1 = new SurveyAnswerDTO();
        surveyAnswerDTO1.setId(1L);
        SurveyAnswerDTO surveyAnswerDTO2 = new SurveyAnswerDTO();
        assertThat(surveyAnswerDTO1).isNotEqualTo(surveyAnswerDTO2);
        surveyAnswerDTO2.setId(surveyAnswerDTO1.getId());
        assertThat(surveyAnswerDTO1).isEqualTo(surveyAnswerDTO2);
        surveyAnswerDTO2.setId(2L);
        assertThat(surveyAnswerDTO1).isNotEqualTo(surveyAnswerDTO2);
        surveyAnswerDTO1.setId(null);
        assertThat(surveyAnswerDTO1).isNotEqualTo(surveyAnswerDTO2);
    }
}
