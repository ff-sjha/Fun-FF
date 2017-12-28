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

import com.firstfuel.fafi.domain.MatchPlayers;
import com.firstfuel.fafi.domain.*; // for static metamodels
import com.firstfuel.fafi.repository.MatchPlayersRepository;
import com.firstfuel.fafi.service.dto.MatchPlayersCriteria;

import com.firstfuel.fafi.service.dto.MatchPlayersDTO;
import com.firstfuel.fafi.service.mapper.MatchPlayersMapper;

/**
 * Service for executing complex queries for MatchPlayers entities in the database.
 * The main input is a {@link MatchPlayersCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MatchPlayersDTO} or a {@link Page} of {@link MatchPlayersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MatchPlayersQueryService extends QueryService<MatchPlayers> {

    private final Logger log = LoggerFactory.getLogger(MatchPlayersQueryService.class);


    private final MatchPlayersRepository matchPlayersRepository;

    private final MatchPlayersMapper matchPlayersMapper;

    public MatchPlayersQueryService(MatchPlayersRepository matchPlayersRepository, MatchPlayersMapper matchPlayersMapper) {
        this.matchPlayersRepository = matchPlayersRepository;
        this.matchPlayersMapper = matchPlayersMapper;
    }

    /**
     * Return a {@link List} of {@link MatchPlayersDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MatchPlayersDTO> findByCriteria(MatchPlayersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<MatchPlayers> specification = createSpecification(criteria);
        return matchPlayersMapper.toDto(matchPlayersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MatchPlayersDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MatchPlayersDTO> findByCriteria(MatchPlayersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<MatchPlayers> specification = createSpecification(criteria);
        final Page<MatchPlayers> result = matchPlayersRepository.findAll(specification, page);
        return result.map(matchPlayersMapper::toDto);
    }

    /**
     * Function to convert MatchPlayersCriteria to a {@link Specifications}
     */
    private Specifications<MatchPlayers> createSpecification(MatchPlayersCriteria criteria) {
        Specifications<MatchPlayers> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MatchPlayers_.id));
            }
            if (criteria.getPlayerOfTheMatch() != null) {
                specification = specification.and(buildSpecification(criteria.getPlayerOfTheMatch(), MatchPlayers_.playerOfTheMatch));
            }
            if (criteria.getPlayerPointsEarned() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlayerPointsEarned(), MatchPlayers_.playerPointsEarned));
            }
            if (criteria.getMatchId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMatchId(), MatchPlayers_.match, Match_.id));
            }
            if (criteria.getSeasonsFranchisePlayerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSeasonsFranchisePlayerId(), MatchPlayers_.seasonsFranchisePlayer, SeasonsFranchisePlayer_.id));
            }
        }
        return specification;
    }

}
