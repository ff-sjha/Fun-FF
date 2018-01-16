package com.firstfuel.fafi.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.firstfuel.fafi.domain.SeasonExpense;
import com.firstfuel.fafi.domain.*; // for static metamodels
import com.firstfuel.fafi.repository.SeasonExpenseRepository;
import com.firstfuel.fafi.service.dto.SeasonExpenseCriteria;


/**
 * Service for executing complex queries for SeasonExpense entities in the database.
 * The main input is a {@link SeasonExpenseCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SeasonExpense} or a {@link Page} of {@link SeasonExpense} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SeasonExpenseQueryService extends QueryService<SeasonExpense> {

    private final Logger log = LoggerFactory.getLogger(SeasonExpenseQueryService.class);


    private final SeasonExpenseRepository seasonExpenseRepository;

    public SeasonExpenseQueryService(SeasonExpenseRepository seasonExpenseRepository) {
        this.seasonExpenseRepository = seasonExpenseRepository;
    }

    /**
     * Return a {@link List} of {@link SeasonExpense} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SeasonExpense> findByCriteria(SeasonExpenseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<SeasonExpense> specification = createSpecification(criteria);
        return seasonExpenseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SeasonExpense} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SeasonExpense> findByCriteria(SeasonExpenseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<SeasonExpense> specification = createSpecification(criteria);
        return seasonExpenseRepository.findAll(specification, page);
    }

    /**
     * Function to convert SeasonExpenseCriteria to a {@link Specifications}
     */
    private Specifications<SeasonExpense> createSpecification(SeasonExpenseCriteria criteria) {
        Specifications<SeasonExpense> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SeasonExpense_.id));
            }
            if (criteria.getIncurredDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIncurredDate(), SeasonExpense_.incurredDate));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SeasonExpense_.description));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), SeasonExpense_.amount));
            }
            if (criteria.getTournamentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTournamentId(), SeasonExpense_.tournament, Tournament_.id));
            }
        }
        return specification;
    }

}
