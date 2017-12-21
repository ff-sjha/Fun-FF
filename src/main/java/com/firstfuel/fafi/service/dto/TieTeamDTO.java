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
            "}";
    }
}
