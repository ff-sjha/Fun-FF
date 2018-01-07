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
 * Criteria class for the TournamentFranchisePoints entity. This class is used in TournamentFranchisePointsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /tournament-franchise-points?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TournamentFranchisePointsCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter points;

    private LongFilter tournamentId;

    private LongFilter franchiseId;

    public TournamentFranchisePointsCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getPoints() {
        return points;
    }

    public void setPoints(IntegerFilter points) {
        this.points = points;
    }

    public LongFilter getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(LongFilter tournamentId) {
        this.tournamentId = tournamentId;
    }

    public LongFilter getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(LongFilter franchiseId) {
        this.franchiseId = franchiseId;
    }

    @Override
    public String toString() {
        return "TournamentFranchisePointsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (points != null ? "points=" + points + ", " : "") +
                (tournamentId != null ? "tournamentId=" + tournamentId + ", " : "") +
                (franchiseId != null ? "franchiseId=" + franchiseId + ", " : "") +
            "}";
    }

}
