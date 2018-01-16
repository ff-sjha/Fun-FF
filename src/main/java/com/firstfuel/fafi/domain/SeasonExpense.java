package com.firstfuel.fafi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A SeasonExpense.
 */
@Entity
@Table(name = "season_expense")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SeasonExpense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "incurred_date", nullable = false)
    private LocalDate incurredDate;

    @NotNull
    @Size(max = 500)
    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "amount", nullable = false)
    private Double amount;

    @ManyToOne(optional = false)
    @NotNull
    private Season season;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getIncurredDate() {
        return incurredDate;
    }

    public SeasonExpense incurredDate(LocalDate incurredDate) {
        this.incurredDate = incurredDate;
        return this;
    }

    public void setIncurredDate(LocalDate incurredDate) {
        this.incurredDate = incurredDate;
    }

    public String getDescription() {
        return description;
    }

    public SeasonExpense description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public SeasonExpense amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Season getSeason() {
        return season;
    }

    public SeasonExpense season(Season season) {
        this.season = season;
        return this;
    }

    public void setSeason(Season season) {
        this.season = season;
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
        SeasonExpense seasonExpense = (SeasonExpense) o;
        if (seasonExpense.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seasonExpense.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeasonExpense{" +
            "id=" + getId() +
            ", incurredDate='" + getIncurredDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
