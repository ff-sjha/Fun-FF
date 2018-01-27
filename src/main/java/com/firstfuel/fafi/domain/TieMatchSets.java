package com.firstfuel.fafi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TieMatchSets.
 */
@Entity
@Table(name = "tie_match_sets")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TieMatchSets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1)
    @Column(name = "set_number", nullable = false)
    private Integer setNumber;

    @Column(name = "team_1_points")
    private Integer team1Points;

    @Column(name = "team_2_points")
    private Integer team2Points;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSetNumber() {
        return setNumber;
    }

    public TieMatchSets setNumber(Integer setNumber) {
        this.setNumber = setNumber;
        return this;
    }

    public void setSetNumber(Integer setNumber) {
        this.setNumber = setNumber;
    }

    public Integer getTeam1Points() {
        return team1Points;
    }

    public TieMatchSets team1Points(Integer team1Points) {
        this.team1Points = team1Points;
        return this;
    }

    public void setTeam1Points(Integer team1Points) {
        this.team1Points = team1Points;
    }

    public Integer getTeam2Points() {
        return team2Points;
    }

    public TieMatchSets team2Points(Integer team2Points) {
        this.team2Points = team2Points;
        return this;
    }

    public void setTeam2Points(Integer team2Points) {
        this.team2Points = team2Points;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TieMatchSets tieMatchSets = (TieMatchSets) o;
        if (tieMatchSets.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tieMatchSets.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TieMatchSets{" +
            "id=" + getId() +
            ", setNumber=" + getSetNumber() +
            ", team1Points=" + getTeam1Points() +
            ", team2Points=" + getTeam2Points() +
            "}";
    }
}
