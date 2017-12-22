package com.firstfuel.fafi.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Season entity.
 */
public class SeasonDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean active;

    private Long winnerId;

    private String winnerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long franchiseId) {
        this.winnerId = franchiseId;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName( String winnerName ) {
        this.winnerName = winnerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeasonDTO seasonDTO = (SeasonDTO) o;
        if(seasonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seasonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeasonDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
