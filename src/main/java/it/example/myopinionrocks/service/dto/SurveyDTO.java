package it.example.myopinionrocks.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.example.myopinionrocks.domain.Survey} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SurveyDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SurveyDTO)) {
            return false;
        }

        SurveyDTO surveyDTO = (SurveyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, surveyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SurveyDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
