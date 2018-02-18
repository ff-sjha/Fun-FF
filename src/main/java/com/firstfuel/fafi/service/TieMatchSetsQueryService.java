package com.firstfuel.fafi.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.firstfuel.fafi.domain.TieMatchSets;
import com.firstfuel.fafi.domain.*; // for static metamodels
import com.firstfuel.fafi.repository.TieMatchSetsRepository;
import com.firstfuel.fafi.service.dto.TieMatchSetsCriteria;


/**
 * Service for executing complex queries for TieMatchSets entities in the database.
 * The main input is a {@link TieMatchSetsCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TieMatchSets} or a {@link Page} of {@link TieMatchSets} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TieMatchSetsQueryService extends QueryService<TieMatchSets> {

    private final Logger log = LoggerFactory.getLogger(TieMatchSetsQueryService.class);


    private final TieMatchSetsRepository tieMatchSetsRepository;

    public TieMatchSetsQueryService(TieMatchSetsRepository tieMatchSetsRepository) {
        this.tieMatchSetsRepository = tieMatchSetsRepository;
    }

    /**
     * Return a {@link List} of {@link TieMatchSets} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TieMatchSets> findByCriteria(TieMatchSetsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<TieMatchSets> specification = createSpecification(criteria);
        return tieMatchSetsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TieMatchSets} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TieMatchSets> findByCriteria(TieMatchSetsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<TieMatchSets> specification = createSpecification(criteria);
        return tieMatchSetsRepository.findAll(specification, page);
    }

    /**
     * Function to convert TieMatchSetsCriteria to a {@link Specifications}
     */
    private Specifications<TieMatchSets> createSpecification(TieMatchSetsCriteria criteria) {
        Specifications<TieMatchSets> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TieMatchSets_.id));
            }
            if (criteria.getSetNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSetNumber(), TieMatchSets_.setNumber));
            }
            if (criteria.getTeam1Points() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTeam1Points(), TieMatchSets_.team1Points));
            }
            if (criteria.getTeam2Points() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTeam2Points(), TieMatchSets_.team2Points));
            }
            if (criteria.getTieMatchId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTieMatchId(), TieMatchSets_.tieMatch, TieMatch_.id));
            }
        }
        return specification;
    }

}
