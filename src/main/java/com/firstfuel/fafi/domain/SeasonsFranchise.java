package com.firstfuel.fafi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SeasonsFranchise.
 */
@Entity
@Table(name = "seasons_franchise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SeasonsFranchise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Season season;

    @ManyToOne(optional = false)
    @NotNull
    private Franchise franchise;

    @ManyToOne(optional = false)
    @NotNull
    private Player owner;

    @ManyToOne(optional = false)
    @NotNull
    private Player iconPlayer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Season getSeason() {
        return season;
    }

    public SeasonsFranchise season(Season season) {
        this.season = season;
        return this;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public SeasonsFranchise franchise(Franchise franchise) {
        this.franchise = franchise;
        return this;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public Player getOwner() {
        return owner;
    }

    public SeasonsFranchise owner(Player player) {
        this.owner = player;
        return this;
    }

    public void setOwner(Player player) {
        this.owner = player;
    }

    public Player getIconPlayer() {
        return iconPlayer;
    }

    public SeasonsFranchise iconPlayer(Player player) {
        this.iconPlayer = player;
        return this;
    }

    public void setIconPlayer(Player player) {
        this.iconPlayer = player;
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
        SeasonsFranchise seasonsFranchise = (SeasonsFranchise) o;
        if (seasonsFranchise.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seasonsFranchise.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeasonsFranchise{" +
            "id=" + getId() +
            "}";
    }
}
