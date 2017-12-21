package com.firstfuel.fafi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Franchise.
 */
@Entity
@Table(name = "franchise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Franchise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logo_path")
    private String logoPath;

    @Column(name = "owner")
    private String owner;

    @Column(name = "icon_player")
    private String iconPlayer;

    @ManyToOne
    private Season season;

    @OneToMany(mappedBy = "franchise")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Player> players = new HashSet<>();

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

    public Franchise name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public Franchise logoPath(String logoPath) {
        this.logoPath = logoPath;
        return this;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getOwner() {
        return owner;
    }

    public Franchise owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getIconPlayer() {
        return iconPlayer;
    }

    public Franchise iconPlayer(String iconPlayer) {
        this.iconPlayer = iconPlayer;
        return this;
    }

    public void setIconPlayer(String iconPlayer) {
        this.iconPlayer = iconPlayer;
    }

    public Season getSeason() {
        return season;
    }

    public Franchise season(Season season) {
        this.season = season;
        return this;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public Franchise players(Set<Player> players) {
        this.players = players;
        return this;
    }

    public Franchise addPlayers(Player player) {
        this.players.add(player);
        player.setFranchise(this);
        return this;
    }

    public Franchise removePlayers(Player player) {
        this.players.remove(player);
        player.setFranchise(null);
        return this;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
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
        Franchise franchise = (Franchise) o;
        if (franchise.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), franchise.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Franchise{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", logoPath='" + getLogoPath() + "'" +
            ", owner='" + getOwner() + "'" +
            ", iconPlayer='" + getIconPlayer() + "'" +
            "}";
    }
}
