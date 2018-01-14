package com.firstfuel.fafi.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.firstfuel.fafi.domain.enumeration.Games;
import com.firstfuel.fafi.domain.enumeration.Stage;

/**
 * A DTO for the Match entity.
 */
public class MatchDTO implements Serializable {

    private Long id;

    private ZonedDateTime startDateTime;

    private ZonedDateTime endDateTime;

    private Integer pointsEarnedByFranchise;

    @NotNull
    private String matchName;

    @NotNull
    private Stage stage;

    private String venue;

    private Long tournamentId;

    private Long winningFranchiseId;

    private String winningFranchiseFranchiseName;

    private Games tournamentType;
    
    private Boolean completed;

    private List<FranchisePlayersDTO> teamPlayers;
    
    private List<MatchUmpireDTO> umpires;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Integer getPointsEarnedByFranchise() {
        return pointsEarnedByFranchise;
    }

    public void setPointsEarnedByFranchise(Integer pointsEarnedByFranchise) {
        this.pointsEarnedByFranchise = pointsEarnedByFranchise;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
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

    public Games getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(Games tournamentType) {
        this.tournamentType = tournamentType;
    }

    public List<FranchisePlayersDTO> getTeamPlayers() {
        return teamPlayers;
    }

    public void setTeamPlayers(List<FranchisePlayersDTO> teamPlayers) {
        this.teamPlayers = teamPlayers;
    }
    

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public List<MatchUmpireDTO> getUmpires() {
        return umpires;
    }

    public void setUmpires(List<MatchUmpireDTO> umpires) {
        this.umpires = umpires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MatchDTO matchDTO = (MatchDTO) o;
        if (matchDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matchDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatchDTO{" + "id=" + getId() + ", startDateTime='" + getStartDateTime() + "'" + ", endDateTime='"
                + getEndDateTime() + "'" + ", pointsEarnedByFranchise=" + getPointsEarnedByFranchise() + ", matchName='"
                + getMatchName() + "'" + ", stage='" + getStage() + "'" + ", venue='" + getVenue() + "'" + "}";
    }
}
