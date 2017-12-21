package com.firstfuel.fafi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Season.
 */
@Entity
@Table(name = "season")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Season implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "winner")
    private String winner;

    @OneToMany(mappedBy = "season")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Franchise> franchises = new HashSet<>();

    @OneToMany(mappedBy = "season")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tournament> tournaments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Season name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Season startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Season endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean isActive() {
        return active;
    }

    public Season active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getWinner() {
        return winner;
    }

    public Season winner(String winner) {
        this.winner = winner;
        return this;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Set<Franchise> getFranchises() {
        return franchises;
    }

    public Season franchises(Set<Franchise> franchises) {
        this.franchises = franchises;
        return this;
    }

    public Season addFranchise(Franchise franchise) {
        this.franchises.add(franchise);
        franchise.setSeason(this);
        return this;
    }

    public Season removeFranchise(Franchise franchise) {
        this.franchises.remove(franchise);
        franchise.setSeason(null);
        return this;
    }

    public void setFranchises(Set<Franchise> franchises) {
        this.franchises = franchises;
    }

    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public Season tournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
        return this;
    }

    public Season addTournament(Tournament tournament) {
        this.tournaments.add(tournament);
        tournament.setSeason(this);
        return this;
    }

    public Season removeTournament(Tournament tournament) {
        this.tournaments.remove(tournament);
        tournament.setSeason(null);
        return this;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
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
        Season season = (Season) o;
        if (season.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), season.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Season{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", active='" + isActive() + "'" +
            ", winner='" + getWinner() + "'" +
            "}";
    }
}
