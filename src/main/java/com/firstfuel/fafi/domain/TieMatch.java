package com.firstfuel.fafi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.firstfuel.fafi.domain.enumeration.TieType;

/**
 * A TieMatch.
 */
@Entity
@Table(name = "tie_match")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TieMatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tie_type", nullable = false)
    private TieType tieType;

    @ManyToOne(optional = false)
    @NotNull
    private Match match;

    @ManyToOne
    private SeasonsFranchisePlayer team1Player1;

    @ManyToOne
    private SeasonsFranchisePlayer team1Player2;

    @ManyToOne
    private SeasonsFranchisePlayer team2Player1;

    @ManyToOne
    private SeasonsFranchisePlayer team2Player2;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TieType getTieType() {
        return tieType;
    }

    public TieMatch tieType(TieType tieType) {
        this.tieType = tieType;
        return this;
    }

    public void setTieType(TieType tieType) {
        this.tieType = tieType;
    }

    public Match getMatch() {
        return match;
    }

    public TieMatch match(Match match) {
        this.match = match;
        return this;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public SeasonsFranchisePlayer getTeam1Player1() {
        return team1Player1;
    }

    public TieMatch team1Player1(SeasonsFranchisePlayer seasonsFranchisePlayer) {
        this.team1Player1 = seasonsFranchisePlayer;
        return this;
    }

    public void setTeam1Player1(SeasonsFranchisePlayer seasonsFranchisePlayer) {
        this.team1Player1 = seasonsFranchisePlayer;
    }

    public SeasonsFranchisePlayer getTeam1Player2() {
        return team1Player2;
    }

    public TieMatch team1Player2(SeasonsFranchisePlayer seasonsFranchisePlayer) {
        this.team1Player2 = seasonsFranchisePlayer;
        return this;
    }

    public void setTeam1Player2(SeasonsFranchisePlayer seasonsFranchisePlayer) {
        this.team1Player2 = seasonsFranchisePlayer;
    }

    public SeasonsFranchisePlayer getTeam2Player1() {
        return team2Player1;
    }

    public TieMatch team2Player1(SeasonsFranchisePlayer seasonsFranchisePlayer) {
        this.team2Player1 = seasonsFranchisePlayer;
        return this;
    }

    public void setTeam2Player1(SeasonsFranchisePlayer seasonsFranchisePlayer) {
        this.team2Player1 = seasonsFranchisePlayer;
    }

    public SeasonsFranchisePlayer getTeam2Player2() {
        return team2Player2;
    }

    public TieMatch team2Player2(SeasonsFranchisePlayer seasonsFranchisePlayer) {
        this.team2Player2 = seasonsFranchisePlayer;
        return this;
    }

    public void setTeam2Player2(SeasonsFranchisePlayer seasonsFranchisePlayer) {
        this.team2Player2 = seasonsFranchisePlayer;
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
        TieMatch tieMatch = (TieMatch) o;
        if (tieMatch.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tieMatch.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TieMatch{" +
            "id=" + getId() +
            ", tieType='" + getTieType() + "'" +
            "}";
    }
}
