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

    private LongFilter team1Player1Id;

    private LongFilter team1Player2Id;

    private LongFilter team2Player1Id;

    private LongFilter team2Player2Id;

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

    public LongFilter getTeam1Player1Id() {
        return team1Player1Id;
    }

    public void setTeam1Player1Id(LongFilter team1Player1Id) {
        this.team1Player1Id = team1Player1Id;
    }

    public LongFilter getTeam1Player2Id() {
        return team1Player2Id;
    }

    public void setTeam1Player2Id(LongFilter team1Player2Id) {
        this.team1Player2Id = team1Player2Id;
    }

    public LongFilter getTeam2Player1Id() {
        return team2Player1Id;
    }

    public void setTeam2Player1Id(LongFilter team2Player1Id) {
        this.team2Player1Id = team2Player1Id;
    }

    public LongFilter getTeam2Player2Id() {
        return team2Player2Id;
    }

    public void setTeam2Player2Id(LongFilter team2Player2Id) {
        this.team2Player2Id = team2Player2Id;
    }

    @Override
    public String toString() {
        return "TieMatchCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tieType != null ? "tieType=" + tieType + ", " : "") +
                (matchId != null ? "matchId=" + matchId + ", " : "") +
                (team1Player1Id != null ? "team1Player1Id=" + team1Player1Id + ", " : "") +
                (team1Player2Id != null ? "team1Player2Id=" + team1Player2Id + ", " : "") +
                (team2Player1Id != null ? "team2Player1Id=" + team2Player1Id + ", " : "") +
                (team2Player2Id != null ? "team2Player2Id=" + team2Player2Id + ", " : "") +
            "}";
    }

}
