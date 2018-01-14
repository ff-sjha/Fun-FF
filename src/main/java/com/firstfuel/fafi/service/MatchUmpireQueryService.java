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

import com.firstfuel.fafi.domain.MatchUmpire;
import com.firstfuel.fafi.domain.*; // for static metamodels
import com.firstfuel.fafi.repository.MatchUmpireRepository;
import com.firstfuel.fafi.service.dto.MatchUmpireCriteria;


/**
 * Service for executing complex queries for MatchUmpire entities in the database.
 * The main input is a {@link MatchUmpireCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MatchUmpire} or a {@link Page} of {@link MatchUmpire} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MatchUmpireQueryService extends QueryService<MatchUmpire> {

    private final Logger log = LoggerFactory.getLogger(MatchUmpireQueryService.class);


    private final MatchUmpireRepository matchUmpireRepository;

    public MatchUmpireQueryService(MatchUmpireRepository matchUmpireRepository) {
        this.matchUmpireRepository = matchUmpireRepository;
    }

    /**
     * Return a {@link List} of {@link MatchUmpire} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MatchUmpire> findByCriteria(MatchUmpireCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<MatchUmpire> specification = createSpecification(criteria);
        return matchUmpireRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MatchUmpire} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MatchUmpire> findByCriteria(MatchUmpireCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<MatchUmpire> specification = createSpecification(criteria);
        return matchUmpireRepository.findAll(specification, page);
    }

    /**
     * Function to convert MatchUmpireCriteria to a {@link Specifications}
     */
    private Specifications<MatchUmpire> createSpecification(MatchUmpireCriteria criteria) {
        Specifications<MatchUmpire> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MatchUmpire_.id));
            }
            if (criteria.getMatchId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMatchId(), MatchUmpire_.match, Match_.id));
            }
            if (criteria.getUmpireId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUmpireId(), MatchUmpire_.umpire, Player_.id));
            }
        }
        return specification;
    }

}
