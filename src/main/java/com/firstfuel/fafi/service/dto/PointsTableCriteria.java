/**
 * 
 */
package com.firstfuel.fafi.service.dto;

import java.io.Serializable;

import io.github.jhipster.service.filter.LongFilter;

/**
 * @author ameethp
 *
 */
public class PointsTableCriteria implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private LongFilter tournamentId;
    @Override
    public String toString() {
        return "PointsTableCriteria [tournamentId=" + tournamentId + ", matchId=" + matchId + "]";
    }

    private LongFilter matchId;

    public PointsTableCriteria() {
    }

    public LongFilter getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(LongFilter tournamentId) {
        this.tournamentId = tournamentId;
    }

    public LongFilter getMatchId() {
        return matchId;
    }

    public void setMatchId(LongFilter matchId) {
        this.matchId = matchId;
    }

}
