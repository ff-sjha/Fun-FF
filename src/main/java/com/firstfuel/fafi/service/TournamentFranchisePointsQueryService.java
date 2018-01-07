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

import com.firstfuel.fafi.domain.TournamentFranchisePoints;
import com.firstfuel.fafi.domain.*; // for static metamodels
import com.firstfuel.fafi.repository.TournamentFranchisePointsRepository;
import com.firstfuel.fafi.service.dto.TournamentFranchisePointsCriteria;

import com.firstfuel.fafi.service.dto.TournamentFranchisePointsDTO;
import com.firstfuel.fafi.service.mapper.TournamentFranchisePointsMapper;

/**
 * Service for executing complex queries for TournamentFranchisePoints entities in the database.
 * The main input is a {@link TournamentFranchisePointsCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TournamentFranchisePointsDTO} or a {@link Page} of {@link TournamentFranchisePointsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TournamentFranchisePointsQueryService extends QueryService<TournamentFranchisePoints> {

    private final Logger log = LoggerFactory.getLogger(TournamentFranchisePointsQueryService.class);


    private final TournamentFranchisePointsRepository tournamentFranchisePointsRepository;

    private final TournamentFranchisePointsMapper tournamentFranchisePointsMapper;

    public TournamentFranchisePointsQueryService(TournamentFranchisePointsRepository tournamentFranchisePointsRepository, TournamentFranchisePointsMapper tournamentFranchisePointsMapper) {
        this.tournamentFranchisePointsRepository = tournamentFranchisePointsRepository;
        this.tournamentFranchisePointsMapper = tournamentFranchisePointsMapper;
    }

    /**
     * Return a {@link List} of {@link TournamentFranchisePointsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TournamentFranchisePointsDTO> findByCriteria(TournamentFranchisePointsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<TournamentFranchisePoints> specification = createSpecification(criteria);
        return tournamentFranchisePointsMapper.toDto(tournamentFranchisePointsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TournamentFranchisePointsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TournamentFranchisePointsDTO> findByCriteria(TournamentFranchisePointsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<TournamentFranchisePoints> specification = createSpecification(criteria);
        final Page<TournamentFranchisePoints> result = tournamentFranchisePointsRepository.findAll(specification, page);
        return result.map(tournamentFranchisePointsMapper::toDto);
    }

    /**
     * Function to convert TournamentFranchisePointsCriteria to a {@link Specifications}
     */
    private Specifications<TournamentFranchisePoints> createSpecification(TournamentFranchisePointsCriteria criteria) {
        Specifications<TournamentFranchisePoints> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TournamentFranchisePoints_.id));
            }
            if (criteria.getPoints() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPoints(), TournamentFranchisePoints_.points));
            }
            if (criteria.getTournamentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTournamentId(), TournamentFranchisePoints_.tournament, Tournament_.id));
            }
            if (criteria.getFranchiseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getFranchiseId(), TournamentFranchisePoints_.franchise, Franchise_.id));
            }
        }
        return specification;
    }

}
