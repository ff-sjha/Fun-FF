package com.firstfuel.fafi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MatchFranchise.
 */
@Entity
@Table(name = "match_franchise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MatchFranchise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Match match;

    @ManyToOne(optional = false)
    @NotNull
    private SeasonsFranchise seasonsFranchise;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public MatchFranchise match(Match match) {
        this.match = match;
        return this;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public SeasonsFranchise getSeasonsFranchise() {
        return seasonsFranchise;
    }

    public MatchFranchise seasonsFranchise(SeasonsFranchise seasonsFranchise) {
        this.seasonsFranchise = seasonsFranchise;
        return this;
    }

    public void setSeasonsFranchise(SeasonsFranchise seasonsFranchise) {
        this.seasonsFranchise = seasonsFranchise;
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
        MatchFranchise matchFranchise = (MatchFranchise) o;
        if (matchFranchise.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matchFranchise.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatchFranchise{" +
            "id=" + getId() +
            "}";
    }
}
