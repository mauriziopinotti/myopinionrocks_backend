package it.example.myopinionrocks.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SurveyAnswerMapperTest {

    private SurveyAnswerMapper surveyAnswerMapper;

    @BeforeEach
    public void setUp() {
        surveyAnswerMapper = new SurveyAnswerMapperImpl();
    }
}
