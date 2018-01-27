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

import com.firstfuel.fafi.domain.TieMatchPlayers;
import com.firstfuel.fafi.domain.*; // for static metamodels
import com.firstfuel.fafi.repository.TieMatchPlayersRepository;
import com.firstfuel.fafi.service.dto.TieMatchPlayersCriteria;


/**
 * Service for executing complex queries for TieMatchPlayers entities in the database.
 * The main input is a {@link TieMatchPlayersCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TieMatchPlayers} or a {@link Page} of {@link TieMatchPlayers} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TieMatchPlayersQueryService extends QueryService<TieMatchPlayers> {

    private final Logger log = LoggerFactory.getLogger(TieMatchPlayersQueryService.class);


    private final TieMatchPlayersRepository tieMatchPlayersRepository;

    public TieMatchPlayersQueryService(TieMatchPlayersRepository tieMatchPlayersRepository) {
        this.tieMatchPlayersRepository = tieMatchPlayersRepository;
    }

    /**
     * Return a {@link List} of {@link TieMatchPlayers} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TieMatchPlayers> findByCriteria(TieMatchPlayersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<TieMatchPlayers> specification = createSpecification(criteria);
        return tieMatchPlayersRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TieMatchPlayers} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TieMatchPlayers> findByCriteria(TieMatchPlayersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<TieMatchPlayers> specification = createSpecification(criteria);
        return tieMatchPlayersRepository.findAll(specification, page);
    }

    /**
     * Function to convert TieMatchPlayersCriteria to a {@link Specifications}
     */
    private Specifications<TieMatchPlayers> createSpecification(TieMatchPlayersCriteria criteria) {
        Specifications<TieMatchPlayers> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TieMatchPlayers_.id));
            }
            if (criteria.getTieMatchId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTieMatchId(), TieMatchPlayers_.tieMatch, TieMatch_.id));
            }
            if (criteria.getSeasonsFranchisePlayerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSeasonsFranchisePlayerId(), TieMatchPlayers_.seasonsFranchisePlayer, SeasonsFranchisePlayer_.id));
            }
        }
        return specification;
    }

}
