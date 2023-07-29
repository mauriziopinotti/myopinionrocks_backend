package it.example.myopinionrocks.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SurveyResultMapperTest {

    private SurveyResultMapper surveyResultMapper;

    @BeforeEach
    public void setUp() {
        surveyResultMapper = new SurveyResultMapperImpl();
    }
}
