package com.firstfuel.fafi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SeasonsFranchisePlayer.
 */
@Entity
@Table(name = "seasons_franchise_player")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SeasonsFranchisePlayer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "bid_price", nullable = false)
    private Double bidPrice;

    @ManyToOne(optional = false)
    @NotNull
    private SeasonsFranchise seasonsFranchise;

    @ManyToOne(optional = false)
    @NotNull
    private Player player;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public SeasonsFranchisePlayer bidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
        return this;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public SeasonsFranchise getSeasonsFranchise() {
        return seasonsFranchise;
    }

    public SeasonsFranchisePlayer seasonsFranchise(SeasonsFranchise seasonsFranchise) {
        this.seasonsFranchise = seasonsFranchise;
        return this;
    }

    public void setSeasonsFranchise(SeasonsFranchise seasonsFranchise) {
        this.seasonsFranchise = seasonsFranchise;
    }

    public Player getPlayer() {
        return player;
    }

    public SeasonsFranchisePlayer player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
        SeasonsFranchisePlayer seasonsFranchisePlayer = (SeasonsFranchisePlayer) o;
        if (seasonsFranchisePlayer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seasonsFranchisePlayer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeasonsFranchisePlayer{" +
            "id=" + getId() +
            ", bidPrice=" + getBidPrice() +
            "}";
    }
}
