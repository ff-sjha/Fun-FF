package com.firstfuel.fafi.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TournamentFranchisePoints entity.
 */
public class TournamentFranchisePointsDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer points;

    private Long tournamentId;

    private String tournamentType;

    private Long franchiseId;

    private String franchiseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(String tournamentType) {
        this.tournamentType = tournamentType;
    }

    public Long getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(Long franchiseId) {
        this.franchiseId = franchiseId;
    }

    public String getFranchiseName() {
        return franchiseName;
    }

    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TournamentFranchisePointsDTO tournamentFranchisePointsDTO = (TournamentFranchisePointsDTO) o;
        if(tournamentFranchisePointsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tournamentFranchisePointsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TournamentFranchisePointsDTO{" +
            "id=" + getId() +
            ", points=" + getPoints() +
            "}";
    }
}
