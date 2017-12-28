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

import com.firstfuel.fafi.domain.enumeration.Games;

/**
 * A Tournament.
 */
@Entity
@Table(name = "tournament")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tournament implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fafi_type", nullable = false)
    private Games type;

    @ManyToOne
    private Season season;

    @OneToMany(mappedBy = "tournament")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Match> matches = new HashSet<>();

    @ManyToOne
    private SeasonsFranchise winningFranchise;

    @ManyToOne
    private SeasonsFranchisePlayer playerOfTournament;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Tournament startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Tournament endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Games getType() {
        return type;
    }

    public Tournament type(Games type) {
        this.type = type;
        return this;
    }

    public void setType(Games type) {
        this.type = type;
    }

    public Season getSeason() {
        return season;
    }

    public Tournament season(Season season) {
        this.season = season;
        return this;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public Tournament matches(Set<Match> matches) {
        this.matches = matches;
        return this;
    }

    public Tournament addMatches(Match match) {
        this.matches.add(match);
        match.setTournament(this);
        return this;
    }

    public Tournament removeMatches(Match match) {
        this.matches.remove(match);
        match.setTournament(null);
        return this;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }

    public SeasonsFranchise getWinningFranchise() {
        return winningFranchise;
    }

    public Tournament winningFranchise(SeasonsFranchise seasonsFranchise) {
        this.winningFranchise = seasonsFranchise;
        return this;
    }

    public void setWinningFranchise(SeasonsFranchise seasonsFranchise) {
        this.winningFranchise = seasonsFranchise;
    }

    public SeasonsFranchisePlayer getPlayerOfTournament() {
        return playerOfTournament;
    }

    public Tournament playerOfTournament(SeasonsFranchisePlayer seasonsFranchisePlayer) {
        this.playerOfTournament = seasonsFranchisePlayer;
        return this;
    }

    public void setPlayerOfTournament(SeasonsFranchisePlayer seasonsFranchisePlayer) {
        this.playerOfTournament = seasonsFranchisePlayer;
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
        Tournament tournament = (Tournament) o;
        if (tournament.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tournament.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tournament{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
