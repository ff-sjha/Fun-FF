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
 * Criteria class for the SeasonsFranchisePlayer entity. This class is used in SeasonsFranchisePlayerResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /seasons-franchise-players?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SeasonsFranchisePlayerCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private DoubleFilter bidPrice;

    private LongFilter seasonsFranchiseId;

    private LongFilter playerId;
    
    private BooleanFilter active;

    public SeasonsFranchisePlayerCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(DoubleFilter bidPrice) {
        this.bidPrice = bidPrice;
    }

    public LongFilter getSeasonsFranchiseId() {
        return seasonsFranchiseId;
    }

    public void setSeasonsFranchiseId(LongFilter seasonsFranchiseId) {
        this.seasonsFranchiseId = seasonsFranchiseId;
    }

    public LongFilter getPlayerId() {
        return playerId;
    }

    public void setPlayerId(LongFilter playerId) {
        this.playerId = playerId;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "SeasonsFranchisePlayerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (bidPrice != null ? "bidPrice=" + bidPrice + ", " : "") +
                (seasonsFranchiseId != null ? "seasonsFranchiseId=" + seasonsFranchiseId + ", " : "") +
                (playerId != null ? "playerId=" + playerId + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
            "}";
    }

}
