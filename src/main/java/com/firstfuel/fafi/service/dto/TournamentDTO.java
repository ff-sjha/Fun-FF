package com.firstfuel.fafi.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.firstfuel.fafi.domain.enumeration.Games;

/**
 * A DTO for the Tournament entity.
 */
public class TournamentDTO implements Serializable {

    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private Games type;

    private Long seasonId;

    private String seasonName;

    private Long winningFranchiseId;

    private String winningFranchiseFranchiseName;

    private Long playerOfTournamentId;

    private String playerOfTournamentPlayerFirstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Games getType() {
        return type;
    }

    public void setType(Games type) {
        this.type = type;
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

    public Long getWinningFranchiseId() {
        return winningFranchiseId;
    }

    public void setWinningFranchiseId(Long seasonsFranchiseId) {
        this.winningFranchiseId = seasonsFranchiseId;
    }

    public String getWinningFranchiseFranchiseName() {
        return winningFranchiseFranchiseName;
    }

    public void setWinningFranchiseFranchiseName(String seasonsFranchiseFranchiseName) {
        this.winningFranchiseFranchiseName = seasonsFranchiseFranchiseName;
    }

    public Long getPlayerOfTournamentId() {
        return playerOfTournamentId;
    }

    public void setPlayerOfTournamentId(Long seasonsFranchisePlayerId) {
        this.playerOfTournamentId = seasonsFranchisePlayerId;
    }

    public String getPlayerOfTournamentPlayerFirstName() {
        return playerOfTournamentPlayerFirstName;
    }

    public void setPlayerOfTournamentPlayerFirstName(String seasonsFranchisePlayerPlayerFirstName) {
        this.playerOfTournamentPlayerFirstName = seasonsFranchisePlayerPlayerFirstName;
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
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
