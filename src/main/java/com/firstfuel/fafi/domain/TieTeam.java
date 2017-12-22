package com.firstfuel.fafi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TieTeam.
 */
@Entity
@Table(name = "tie_team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TieTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "points")
    private Double points;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tie_team_tie_players",
               joinColumns = @JoinColumn(name="tie_teams_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tie_players_id", referencedColumnName="id"))
    private Set<Player> tiePlayers = new HashSet<>();

    @ManyToOne
    private Franchise franchise;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPoints() {
        return points;
    }

    public TieTeam points(Double points) {
        this.points = points;
        return this;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public Set<Player> getTiePlayers() {
        return tiePlayers;
    }

    public TieTeam tiePlayers(Set<Player> players) {
        this.tiePlayers = players;
        return this;
    }

    public TieTeam addTiePlayers(Player player) {
        this.tiePlayers.add(player);
        //player.getTieTeams().add(this);
        return this;
    }

    public TieTeam removeTiePlayers(Player player) {
        this.tiePlayers.remove(player);
        //player.getTieTeams().remove(this);
        return this;
    }

    public void setTiePlayers(Set<Player> players) {
        this.tiePlayers = players;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public TieTeam franchise(Franchise franchise) {
        this.franchise = franchise;
        return this;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
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
        TieTeam tieTeam = (TieTeam) o;
        if (tieTeam.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tieTeam.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TieTeam{" +
            "id=" + getId() +
            ", points=" + getPoints() +
            "}";
    }
}
