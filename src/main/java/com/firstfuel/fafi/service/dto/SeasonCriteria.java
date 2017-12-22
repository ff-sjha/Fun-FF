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
 * Criteria class for the Season entity. This class is used in SeasonResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /seasons?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SeasonCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private BooleanFilter active;

    private LongFilter tournamentId;

    private LongFilter winnerId;

    public SeasonCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(LongFilter tournamentId) {
        this.tournamentId = tournamentId;
    }

    public LongFilter getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(LongFilter winnerId) {
        this.winnerId = winnerId;
    }

    @Override
    public String toString() {
        return "SeasonCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (tournamentId != null ? "tournamentId=" + tournamentId + ", " : "") +
                (winnerId != null ? "winnerId=" + winnerId + ", " : "") +
            "}";
    }

}
