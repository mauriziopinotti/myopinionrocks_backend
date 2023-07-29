package it.example.myopinionrocks.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SurveyQuestionMapperTest {

    private SurveyQuestionMapper surveyQuestionMapper;

    @BeforeEach
    public void setUp() {
        surveyQuestionMapper = new SurveyQuestionMapperImpl();
    }
}
