package com.firstfuel.fafi.service.dto;

import java.io.Serializable;
import com.firstfuel.fafi.domain.enumeration.TieType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the TieMatch entity. This class is used in TieMatchResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /tie-matches?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TieMatchCriteria implements Serializable {
    /**
     * Class for filtering TieType
     */
    public static class TieTypeFilter extends Filter<TieType> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private TieTypeFilter tieType;

    private LongFilter matchId;

    public TieMatchCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TieTypeFilter getTieType() {
        return tieType;
    }

    public void setTieType(TieTypeFilter tieType) {
        this.tieType = tieType;
    }

    public LongFilter getMatchId() {
        return matchId;
    }

    public void setMatchId(LongFilter matchId) {
        this.matchId = matchId;
    }

    @Override
    public String toString() {
        return "TieMatchCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tieType != null ? "tieType=" + tieType + ", " : "") +
                (matchId != null ? "matchId=" + matchId + ", " : "") +
            "}";
    }

}
