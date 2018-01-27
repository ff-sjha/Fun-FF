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
 * Criteria class for the TieMatchPlayers entity. This class is used in TieMatchPlayersResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /tie-match-players?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TieMatchPlayersCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter tieMatchId;

    private LongFilter seasonsFranchisePlayerId;

    public TieMatchPlayersCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getTieMatchId() {
        return tieMatchId;
    }

    public void setTieMatchId(LongFilter tieMatchId) {
        this.tieMatchId = tieMatchId;
    }

    public LongFilter getSeasonsFranchisePlayerId() {
        return seasonsFranchisePlayerId;
    }

    public void setSeasonsFranchisePlayerId(LongFilter seasonsFranchisePlayerId) {
        this.seasonsFranchisePlayerId = seasonsFranchisePlayerId;
    }

    @Override
    public String toString() {
        return "TieMatchPlayersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tieMatchId != null ? "tieMatchId=" + tieMatchId + ", " : "") +
                (seasonsFranchisePlayerId != null ? "seasonsFranchisePlayerId=" + seasonsFranchisePlayerId + ", " : "") +
            "}";
    }

}
