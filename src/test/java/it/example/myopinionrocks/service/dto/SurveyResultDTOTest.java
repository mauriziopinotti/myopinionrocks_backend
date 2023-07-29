package it.example.myopinionrocks.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.example.myopinionrocks.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SurveyResultDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SurveyResultDTO.class);
        SurveyResultDTO surveyResultDTO1 = new SurveyResultDTO();
        surveyResultDTO1.setId(1L);
        SurveyResultDTO surveyResultDTO2 = new SurveyResultDTO();
        assertThat(surveyResultDTO1).isNotEqualTo(surveyResultDTO2);
        surveyResultDTO2.setId(surveyResultDTO1.getId());
        assertThat(surveyResultDTO1).isEqualTo(surveyResultDTO2);
        surveyResultDTO2.setId(2L);
        assertThat(surveyResultDTO1).isNotEqualTo(surveyResultDTO2);
        surveyResultDTO1.setId(null);
        assertThat(surveyResultDTO1).isNotEqualTo(surveyResultDTO2);
    }
}
