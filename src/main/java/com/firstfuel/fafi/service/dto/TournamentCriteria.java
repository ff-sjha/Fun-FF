package com.firstfuel.fafi.service.dto;

import java.io.Serializable;
import com.firstfuel.fafi.domain.enumeration.Games;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the Tournament entity. This class is used in TournamentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /tournaments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TournamentCriteria implements Serializable {
    /**
     * Class for filtering Games
     */
    public static class GamesFilter extends Filter<Games> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private GamesFilter type;

    private LongFilter seasonId;

    private LongFilter matchesId;

    private LongFilter winningFranchiseId;

    private LongFilter playerOfTournamentId;
    
    private BooleanFilter active;

    public TournamentCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public GamesFilter getType() {
        return type;
    }

    public void setType(GamesFilter type) {
        this.type = type;
    }

    public LongFilter getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(LongFilter seasonId) {
        this.seasonId = seasonId;
    }

    public LongFilter getMatchesId() {
        return matchesId;
    }

    public void setMatchesId(LongFilter matchesId) {
        this.matchesId = matchesId;
    }

    public LongFilter getWinningFranchiseId() {
        return winningFranchiseId;
    }

    public void setWinningFranchiseId(LongFilter winningFranchiseId) {
        this.winningFranchiseId = winningFranchiseId;
    }

    public LongFilter getPlayerOfTournamentId() {
        return playerOfTournamentId;
    }

    public void setPlayerOfTournamentId(LongFilter playerOfTournamentId) {
        this.playerOfTournamentId = playerOfTournamentId;
    }

	public BooleanFilter getActive() {
		return active;
	}

	public void setActive(BooleanFilter active) {
		this.active = active;
	}

	@Override
    public String toString() {
        return "TournamentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (seasonId != null ? "seasonId=" + seasonId + ", " : "") +
                (matchesId != null ? "matchesId=" + matchesId + ", " : "") +
                (winningFranchiseId != null ? "winningFranchiseId=" + winningFranchiseId + ", " : "") +
                (playerOfTournamentId != null ? "playerOfTournamentId=" + playerOfTournamentId + ", " : "") +
            "}";
    }

}
