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
 * Criteria class for the MatchPlayers entity. This class is used in MatchPlayersResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /match-players?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MatchPlayersCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private BooleanFilter playerOfTheMatch;

    private IntegerFilter playerPointsEarned;

    private LongFilter matchId;

    private LongFilter seasonsFranchisePlayerId;

    public MatchPlayersCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getPlayerOfTheMatch() {
        return playerOfTheMatch;
    }

    public void setPlayerOfTheMatch(BooleanFilter playerOfTheMatch) {
        this.playerOfTheMatch = playerOfTheMatch;
    }

    public IntegerFilter getPlayerPointsEarned() {
        return playerPointsEarned;
    }

    public void setPlayerPointsEarned(IntegerFilter playerPointsEarned) {
        this.playerPointsEarned = playerPointsEarned;
    }

    public LongFilter getMatchId() {
        return matchId;
    }

    public void setMatchId(LongFilter matchId) {
        this.matchId = matchId;
    }

    public LongFilter getSeasonsFranchisePlayerId() {
        return seasonsFranchisePlayerId;
    }

    public void setSeasonsFranchisePlayerId(LongFilter seasonsFranchisePlayerId) {
        this.seasonsFranchisePlayerId = seasonsFranchisePlayerId;
    }

    @Override
    public String toString() {
        return "MatchPlayersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (playerOfTheMatch != null ? "playerOfTheMatch=" + playerOfTheMatch + ", " : "") +
                (playerPointsEarned != null ? "playerPointsEarned=" + playerPointsEarned + ", " : "") +
                (matchId != null ? "matchId=" + matchId + ", " : "") +
                (seasonsFranchisePlayerId != null ? "seasonsFranchisePlayerId=" + seasonsFranchisePlayerId + ", " : "") +
            "}";
    }

}
