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
 * Criteria class for the TieMatchSets entity. This class is used in TieMatchSetsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /tie-match-sets?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TieMatchSetsCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter setNumber;

    private IntegerFilter team1Points;

    private IntegerFilter team2Points;

    public TieMatchSetsCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(IntegerFilter setNumber) {
        this.setNumber = setNumber;
    }

    public IntegerFilter getTeam1Points() {
        return team1Points;
    }

    public void setTeam1Points(IntegerFilter team1Points) {
        this.team1Points = team1Points;
    }

    public IntegerFilter getTeam2Points() {
        return team2Points;
    }

    public void setTeam2Points(IntegerFilter team2Points) {
        this.team2Points = team2Points;
    }

    @Override
    public String toString() {
        return "TieMatchSetsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (setNumber != null ? "setNumber=" + setNumber + ", " : "") +
                (team1Points != null ? "team1Points=" + team1Points + ", " : "") +
                (team2Points != null ? "team2Points=" + team2Points + ", " : "") +
            "}";
    }

}
