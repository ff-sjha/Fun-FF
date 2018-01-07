package com.firstfuel.fafi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TournamentFranchisePoints.
 */
@Entity
@Table(name = "tournament_franchise_points")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TournamentFranchisePoints implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "points", nullable = false)
    private Integer points;

    @ManyToOne(optional = false)
    @NotNull
    private Tournament tournament;

    @ManyToOne(optional = false)
    @NotNull
    private Franchise franchise;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public TournamentFranchisePoints points(Integer points) {
        this.points = points;
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public TournamentFranchisePoints tournament(Tournament tournament) {
        this.tournament = tournament;
        return this;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public TournamentFranchisePoints franchise(Franchise franchise) {
        this.franchise = franchise;
        return this;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
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
        TournamentFranchisePoints tournamentFranchisePoints = (TournamentFranchisePoints) o;
        if (tournamentFranchisePoints.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tournamentFranchisePoints.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TournamentFranchisePoints{" +
            "id=" + getId() +
            ", points=" + getPoints() +
            "}";
    }
}
