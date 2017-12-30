package com.firstfuel.fafi.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the News entity. This class is used in NewsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /news?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NewsCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter title;

    private LocalDateFilter activeFrom;

    private LocalDateFilter ativeTill;

    public NewsCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public LocalDateFilter getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(LocalDateFilter activeFrom) {
        this.activeFrom = activeFrom;
    }

    public LocalDateFilter getAtiveTill() {
        return ativeTill;
    }

    public void setAtiveTill(LocalDateFilter ativeTill) {
        this.ativeTill = ativeTill;
    }

    @Override
    public String toString() {
        return "NewsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (activeFrom != null ? "activeFrom=" + activeFrom + ", " : "") +
                (ativeTill != null ? "ativeTill=" + ativeTill + ", " : "") +
            "}";
    }

}
