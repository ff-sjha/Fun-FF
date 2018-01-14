package com.firstfuel.fafi.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the MatchUmpire entity. This class is used in MatchUmpireResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /match-umpires?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MatchUmpireCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter matchId;

    private LongFilter umpireId;

    public MatchUmpireCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getMatchId() {
        return matchId;
    }

    public void setMatchId(LongFilter matchId) {
        this.matchId = matchId;
    }

    public LongFilter getUmpireId() {
        return umpireId;
    }

    public void setUmpireId(LongFilter umpireId) {
        this.umpireId = umpireId;
    }

    @Override
    public String toString() {
        return "MatchUmpireCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (matchId != null ? "matchId=" + matchId + ", " : "") +
                (umpireId != null ? "umpireId=" + umpireId + ", " : "") +
            "}";
    }

}
