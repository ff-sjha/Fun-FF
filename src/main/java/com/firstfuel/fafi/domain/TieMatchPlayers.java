package com.firstfuel.fafi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TieMatchPlayers.
 */
@Entity
@Table(name = "tie_match_players")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TieMatchPlayers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private TieMatch tieMatch;

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

    public TieMatch getTieMatch() {
        return tieMatch;
    }

    public TieMatchPlayers tieMatch(TieMatch tieMatch) {
        this.tieMatch = tieMatch;
        return this;
    }

    public void setTieMatch(TieMatch tieMatch) {
        this.tieMatch = tieMatch;
    }

    public SeasonsFranchisePlayer getSeasonsFranchisePlayer() {
        return seasonsFranchisePlayer;
    }

    public TieMatchPlayers seasonsFranchisePlayer(SeasonsFranchisePlayer seasonsFranchisePlayer) {
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
        TieMatchPlayers tieMatchPlayers = (TieMatchPlayers) o;
        if (tieMatchPlayers.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tieMatchPlayers.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TieMatchPlayers{" +
            "id=" + getId() +
            "}";
    }
}
