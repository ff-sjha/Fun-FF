package com.firstfuel.fafi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MatchPlayers.
 */
@Entity
@Table(name = "match_players")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MatchPlayers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "player_of_the_match", nullable = false)
    private Boolean playerOfTheMatch;

    @Column(name = "player_points_earned")
    private Integer playerPointsEarned;

    @ManyToOne(optional = false)
    @NotNull
    private Match match;

    @ManyToOne(optional = false)
    @NotNull
    private SeasonsFranchisePlayer seasonsFranchisePlayer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPlayerOfTheMatch() {
        return playerOfTheMatch;
    }

    public MatchPlayers playerOfTheMatch(Boolean playerOfTheMatch) {
        this.playerOfTheMatch = playerOfTheMatch;
        return this;
    }

    public void setPlayerOfTheMatch(Boolean playerOfTheMatch) {
        this.playerOfTheMatch = playerOfTheMatch;
    }

    public Integer getPlayerPointsEarned() {
        return playerPointsEarned;
    }

    public MatchPlayers playerPointsEarned(Integer playerPointsEarned) {
        this.playerPointsEarned = playerPointsEarned;
        return this;
    }

    public void setPlayerPointsEarned(Integer playerPointsEarned) {
        this.playerPointsEarned = playerPointsEarned;
    }

    public Match getMatch() {
        return match;
    }

    public MatchPlayers match(Match match) {
        this.match = match;
        return this;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public SeasonsFranchisePlayer getSeasonsFranchisePlayer() {
        return seasonsFranchisePlayer;
    }

    public MatchPlayers seasonsFranchisePlayer(SeasonsFranchisePlayer seasonsFranchisePlayer) {
        this.seasonsFranchisePlayer = seasonsFranchisePlayer;
        return this;
    }

    public void setSeasonsFranchisePlayer(SeasonsFranchisePlayer seasonsFranchisePlayer) {
        this.seasonsFranchisePlayer = seasonsFranchisePlayer;
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
        MatchPlayers matchPlayers = (MatchPlayers) o;
        if (matchPlayers.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matchPlayers.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatchPlayers{" +
            "id=" + getId() +
            ", playerOfTheMatch='" + isPlayerOfTheMatch() + "'" +
            ", playerPointsEarned=" + getPlayerPointsEarned() +
            "}";
    }
}
