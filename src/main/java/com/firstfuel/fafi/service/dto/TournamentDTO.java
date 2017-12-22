package com.firstfuel.fafi.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Tournament entity.
 */
public class TournamentDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long seasonId;

    private String seasonName;

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

    public Long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Long seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
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

        TournamentDTO tournamentDTO = (TournamentDTO) o;
        if(tournamentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tournamentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TournamentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
