package com.firstfuel.fafi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.firstfuel.fafi.domain.enumeration.Games;

/**
 * A SportInfo.
 */
@Entity
@Table(name = "sport_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SportInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "game", nullable = false)
    private Games game;

    @Lob
    @Column(name = "rules")
    private String rules;

    @Lob
    @Column(name = "scoring_system")
    private String scoring_system;

    @Lob
    @Column(name = "points_system")
    private String points_system;

    @Lob
    @Column(name = "match_system")
    private String match_system;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Games getGame() {
        return game;
    }

    public SportInfo game(Games game) {
        this.game = game;
        return this;
    }

    public void setGame(Games game) {
        this.game = game;
    }

    public String getRules() {
        return rules;
    }

    public SportInfo rules(String rules) {
        this.rules = rules;
        return this;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getScoring_system() {
        return scoring_system;
    }

    public SportInfo scoring_system(String scoring_system) {
        this.scoring_system = scoring_system;
        return this;
    }

    public void setScoring_system(String scoring_system) {
        this.scoring_system = scoring_system;
    }

    public String getPoints_system() {
        return points_system;
    }

    public SportInfo points_system(String points_system) {
        this.points_system = points_system;
        return this;
    }

    public void setPoints_system(String points_system) {
        this.points_system = points_system;
    }

    public String getMatch_system() {
        return match_system;
    }

    public SportInfo match_system(String match_system) {
        this.match_system = match_system;
        return this;
    }

    public void setMatch_system(String match_system) {
        this.match_system = match_system;
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
        SportInfo sportInfo = (SportInfo) o;
        if (sportInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sportInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SportInfo{" +
            "id=" + getId() +
            ", game='" + getGame() + "'" +
            ", rules='" + getRules() + "'" +
            ", scoring_system='" + getScoring_system() + "'" +
            ", points_system='" + getPoints_system() + "'" +
            ", match_system='" + getMatch_system() + "'" +
            "}";
    }
}
