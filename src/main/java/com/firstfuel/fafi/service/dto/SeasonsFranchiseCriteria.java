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
 * Criteria class for the SeasonsFranchise entity. This class is used in SeasonsFranchiseResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /seasons-franchises?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SeasonsFranchiseCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter seasonId;

    private LongFilter franchiseId;

    private LongFilter ownerId;

    private LongFilter iconPlayerId;

    public SeasonsFranchiseCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(LongFilter seasonId) {
        this.seasonId = seasonId;
    }

    public LongFilter getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(LongFilter franchiseId) {
        this.franchiseId = franchiseId;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getIconPlayerId() {
        return iconPlayerId;
    }

    public void setIconPlayerId(LongFilter iconPlayerId) {
        this.iconPlayerId = iconPlayerId;
    }

    @Override
    public String toString() {
        return "SeasonsFranchiseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (seasonId != null ? "seasonId=" + seasonId + ", " : "") +
                (franchiseId != null ? "franchiseId=" + franchiseId + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (iconPlayerId != null ? "iconPlayerId=" + iconPlayerId + ", " : "") +
            "}";
    }

}
