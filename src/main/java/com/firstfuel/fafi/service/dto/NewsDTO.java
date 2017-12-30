package com.firstfuel.fafi.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the News entity.
 */
public class NewsDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @Lob
    private String details;

    @NotNull
    private LocalDate activeFrom;

    @NotNull
    private LocalDate ativeTill;

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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(LocalDate activeFrom) {
        this.activeFrom = activeFrom;
    }

    public LocalDate getAtiveTill() {
        return ativeTill;
    }

    public void setAtiveTill(LocalDate ativeTill) {
        this.ativeTill = ativeTill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NewsDTO newsDTO = (NewsDTO) o;
        if(newsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), newsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NewsDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", details='" + getDetails() + "'" +
            ", activeFrom='" + getActiveFrom() + "'" +
            ", ativeTill='" + getAtiveTill() + "'" +
            "}";
    }
}
