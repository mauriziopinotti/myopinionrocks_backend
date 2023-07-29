package it.example.myopinionrocks.service.dto;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A DTO for the {@link it.example.myopinionrocks.domain.SurveyResult} entity.
 */
@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SurveyResultSubmitDTO implements Serializable {

    public static class IntegerMapKeyDeserializer extends KeyDeserializer {
        @Override
        public Long deserializeKey(String key, DeserializationContext context) {
            return Long.valueOf(key);
        }
    }

    private Long surveyId;

    // questionId => answerId
    @JsonDeserialize(keyUsing = IntegerMapKeyDeserializer.class)
    private Map<Long, Long> surveyAnswers;
}
