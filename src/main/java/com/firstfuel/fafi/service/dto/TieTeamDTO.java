package com.firstfuel.fafi.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TieTeam entity.
 */
public class TieTeamDTO implements Serializable {

    private Long id;

    private Double points;

    private String name;

    private Set<PlayerDTO> tiePlayers = new HashSet<>();

    private Long franchiseId;

    private String franchiseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PlayerDTO> getTiePlayers() {
        return tiePlayers;
    }

    public void setTiePlayers(Set<PlayerDTO> players) {
        this.tiePlayers = players;
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

    public void setFranchiseName( String franchiseName ) {
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

        TieTeamDTO tieTeamDTO = (TieTeamDTO) o;
        if(tieTeamDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tieTeamDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TieTeamDTO{" +
            "id=" + getId() +
            ", points=" + getPoints() +
            ", name='" + getName() + "'" +
            "}";
    }
}
