package it.example.myopinionrocks.service.mapper;


import org.junit.jupiter.api.BeforeEach;

class SurveyMapperTest {

    private SurveyMapper surveyMapper;

    @BeforeEach
    public void setUp() {
        surveyMapper = new SurveyMapperImpl();
    }
}
