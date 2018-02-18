package com.firstfuel.fafi.service.dto;


import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
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

    @NotNull
    private String matchName;

    @NotNull
    private Stage stage;

    private String venue;

    @NotNull
    private Boolean completed;

    private Long tournamentId;

    private Long winningFranchiseId;

    private String winningFranchiseFranchiseName;

    private Long team1Id;

    private String team1Franchise;

    private Long team2Id;

    private String team2Franchise;

    private Long team3Id;

    private String team3Franchise;

    private Long team4Id;

    private String team4Franchise;

    private Games tournamentType;
    
    private Integer team1Points;

    private Integer team2Points;

    private Integer team3Points;

    private Integer team4Points;
    
    
    private List<FranchisePlayersDTO> teamPlayers;
    
    private List<MatchUmpireDTO> umpires;

    private List<TieMatchDTO> tieMatchDTOs;

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

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
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

    public Long getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(Long seasonsFranchiseId) {
        this.team1Id = seasonsFranchiseId;
    }

    public String getTeam1Franchise() {
        return team1Franchise;
    }

    public void setTeam1Franchise(String seasonsFranchiseFranchise) {
        this.team1Franchise = seasonsFranchiseFranchise;
    }

    public Long getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(Long seasonsFranchiseId) {
        this.team2Id = seasonsFranchiseId;
    }

    public String getTeam2Franchise() {
        return team2Franchise;
    }

    public void setTeam2Franchise(String seasonsFranchiseFranchise) {
        this.team2Franchise = seasonsFranchiseFranchise;
    }

    public Long getTeam3Id() {
        return team3Id;
    }

    public void setTeam3Id(Long seasonsFranchiseId) {
        this.team3Id = seasonsFranchiseId;
    }

    public String getTeam3Franchise() {
        return team3Franchise;
    }

    public void setTeam3Franchise(String seasonsFranchiseFranchise) {
        this.team3Franchise = seasonsFranchiseFranchise;
    }

    public Long getTeam4Id() {
        return team4Id;
    }

    public void setTeam4Id(Long seasonsFranchiseId) {
        this.team4Id = seasonsFranchiseId;
    }

    public String getTeam4Franchise() {
        return team4Franchise;
    }

    public void setTeam4Franchise(String seasonsFranchiseFranchise) {
        this.team4Franchise = seasonsFranchiseFranchise;
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

    public List<TieMatchDTO> getTieMatchDTOs() {
        return tieMatchDTOs;
    }

    public void setTieMatchDTOs(List<TieMatchDTO> tieMatchDTOs) {
        this.tieMatchDTOs = tieMatchDTOs;
    }

    public void setTeamPlayers(List<FranchisePlayersDTO> teamPlayers) {
        this.teamPlayers = teamPlayers;
    }

    public List<MatchUmpireDTO> getUmpires() {
        return umpires;
    }

    public void setUmpires(List<MatchUmpireDTO> umpires) {
        this.umpires = umpires;
    }

    public Integer getTeam1Points() {
        return team1Points;
    }

    public void setTeam1Points(Integer team1Points) {
        this.team1Points = team1Points;
    }

    public Integer getTeam2Points() {
        return team2Points;
    }

    public void setTeam2Points(Integer team2Points) {
        this.team2Points = team2Points;
    }

    public Integer getTeam3Points() {
        return team3Points;
    }

    public void setTeam3Points(Integer team3Points) {
        this.team3Points = team3Points;
    }

    public Integer getTeam4Points() {
        return team4Points;
    }

    public void setTeam4Points(Integer team4Points) {
        this.team4Points = team4Points;
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
        if(matchDTO.getId() == null || getId() == null) {
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
        return "MatchDTO{" +
            "id=" + getId() +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", matchName='" + getMatchName() + "'" +
            ", stage='" + getStage() + "'" +
            ", venue='" + getVenue() + "'" +
            ", completed='" + isCompleted() + "'" +
            "}";
    }
}
