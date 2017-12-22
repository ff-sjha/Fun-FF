package com.firstfuel.fafi.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;



import io.github.jhipster.service.filter.ZonedDateTimeFilter;


/**
 * Criteria class for the Match entity. This class is used in MatchResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /matches?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MatchCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private ZonedDateTimeFilter startDateTime;

    private ZonedDateTimeFilter endDateTime;

    private LongFilter tournamentId;

    private LongFilter franchise1Id;

    private LongFilter franchise2Id;

    private LongFilter winnerId;

    public MatchCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(ZonedDateTimeFilter startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTimeFilter getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(ZonedDateTimeFilter endDateTime) {
        this.endDateTime = endDateTime;
    }

    public LongFilter getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(LongFilter tournamentId) {
        this.tournamentId = tournamentId;
    }

    public LongFilter getFranchise1Id() {
        return franchise1Id;
    }

    public void setFranchise1Id(LongFilter franchise1Id) {
        this.franchise1Id = franchise1Id;
    }

    public LongFilter getFranchise2Id() {
        return franchise2Id;
    }

    public void setFranchise2Id(LongFilter franchise2Id) {
        this.franchise2Id = franchise2Id;
    }

    public LongFilter getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(LongFilter winnerId) {
        this.winnerId = winnerId;
    }

    @Override
    public String toString() {
        return "MatchCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startDateTime != null ? "startDateTime=" + startDateTime + ", " : "") +
                (endDateTime != null ? "endDateTime=" + endDateTime + ", " : "") +
                (tournamentId != null ? "tournamentId=" + tournamentId + ", " : "") +
                (franchise1Id != null ? "franchise1Id=" + franchise1Id + ", " : "") +
                (franchise2Id != null ? "franchise2Id=" + franchise2Id + ", " : "") +
                (winnerId != null ? "winnerId=" + winnerId + ", " : "") +
            "}";
    }

}
