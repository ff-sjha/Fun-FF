package com.firstfuel.fafi.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the SeasonExpense entity. This class is used in SeasonExpenseResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /season-expenses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SeasonExpenseCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter incurredDate;

    private StringFilter description;

    private DoubleFilter amount;

    private LongFilter seasonId;

    public SeasonExpenseCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getIncurredDate() {
        return incurredDate;
    }

    public void setIncurredDate(LocalDateFilter incurredDate) {
        this.incurredDate = incurredDate;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public LongFilter getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(LongFilter seasonId) {
        this.seasonId = seasonId;
    }

    @Override
    public String toString() {
        return "SeasonExpenseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (incurredDate != null ? "incurredDate=" + incurredDate + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (seasonId != null ? "seasonId=" + seasonId + ", " : "") +
            "}";
    }

}
