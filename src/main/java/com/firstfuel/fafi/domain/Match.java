package com.firstfuel.fafi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Match.
 */
@Entity
@Table(name = "fafi_match")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date_time")
    private ZonedDateTime startDateTime;

    @Column(name = "end_date_time")
    private ZonedDateTime endDateTime;

    @Column(name = "points_earned_by_franchise")
    private Integer pointsEarnedByFranchise;

    @NotNull
    @Column(name = "match_name", nullable = false)
    private String matchName;

    @ManyToOne
    private Tournament tournament;

    @ManyToOne
    private SeasonsFranchise winningFranchise;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public Match startDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public Match endDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Integer getPointsEarnedByFranchise() {
        return pointsEarnedByFranchise;
    }

    public Match pointsEarnedByFranchise(Integer pointsEarnedByFranchise) {
        this.pointsEarnedByFranchise = pointsEarnedByFranchise;
        return this;
    }

    public void setPointsEarnedByFranchise(Integer pointsEarnedByFranchise) {
        this.pointsEarnedByFranchise = pointsEarnedByFranchise;
    }

    public String getMatchName() {
        return matchName;
    }

    public Match matchName(String matchName) {
        this.matchName = matchName;
        return this;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public Match tournament(Tournament tournament) {
        this.tournament = tournament;
        return this;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public SeasonsFranchise getWinningFranchise() {
        return winningFranchise;
    }

    public Match winningFranchise(SeasonsFranchise seasonsFranchise) {
        this.winningFranchise = seasonsFranchise;
        return this;
    }

    public void setWinningFranchise(SeasonsFranchise seasonsFranchise) {
        this.winningFranchise = seasonsFranchise;
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
        Match match = (Match) o;
        if (match.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), match.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Match{" +
            "id=" + getId() +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", pointsEarnedByFranchise=" + getPointsEarnedByFranchise() +
            ", matchName='" + getMatchName() + "'" +
            "}";
    }
}
