package com.firstfuel.fafi.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TieMatch entity.
 */
public class TieMatchDTO implements Serializable {

    private Long id;

    private Long matchId;

    private Long team1Id;

    private Long team2Id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Long getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(Long tieTeamId) {
        this.team1Id = tieTeamId;
    }

    public Long getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(Long tieTeamId) {
        this.team2Id = tieTeamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TieMatchDTO tieMatchDTO = (TieMatchDTO) o;
        if(tieMatchDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tieMatchDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TieMatchDTO{" +
            "id=" + getId() +
            "}";
    }
}
