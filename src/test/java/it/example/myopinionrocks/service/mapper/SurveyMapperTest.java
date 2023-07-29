package it.example.myopinionrocks.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SurveyMapperTest {

    private SurveyMapper surveyMapper;

    @BeforeEach
    public void setUp() {
        surveyMapper = new SurveyMapperImpl();
    }
}
