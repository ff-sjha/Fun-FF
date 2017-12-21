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
 * Criteria class for the TieTeam entity. This class is used in TieTeamResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /tie-teams?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TieTeamCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private DoubleFilter points;

    private LongFilter tiePlayersId;

    public TieTeamCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getPoints() {
        return points;
    }

    public void setPoints(DoubleFilter points) {
        this.points = points;
    }

    public LongFilter getTiePlayersId() {
        return tiePlayersId;
    }

    public void setTiePlayersId(LongFilter tiePlayersId) {
        this.tiePlayersId = tiePlayersId;
    }

    @Override
    public String toString() {
        return "TieTeamCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (points != null ? "points=" + points + ", " : "") +
                (tiePlayersId != null ? "tiePlayersId=" + tiePlayersId + ", " : "") +
            "}";
    }

}
