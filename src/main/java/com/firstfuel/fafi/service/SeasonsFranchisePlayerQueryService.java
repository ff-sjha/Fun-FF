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

import com.firstfuel.fafi.domain.SeasonsFranchisePlayer;
import com.firstfuel.fafi.domain.*; // for static metamodels
import com.firstfuel.fafi.repository.SeasonsFranchisePlayerRepository;
import com.firstfuel.fafi.service.dto.SeasonsFranchisePlayerCriteria;

import com.firstfuel.fafi.service.dto.SeasonsFranchisePlayerDTO;
import com.firstfuel.fafi.service.mapper.SeasonsFranchisePlayerMapper;

/**
 * Service for executing complex queries for SeasonsFranchisePlayer entities in the database.
 * The main input is a {@link SeasonsFranchisePlayerCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SeasonsFranchisePlayerDTO} or a {@link Page} of {@link SeasonsFranchisePlayerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SeasonsFranchisePlayerQueryService extends QueryService<SeasonsFranchisePlayer> {

    private final Logger log = LoggerFactory.getLogger(SeasonsFranchisePlayerQueryService.class);


    private final SeasonsFranchisePlayerRepository seasonsFranchisePlayerRepository;

    private final SeasonsFranchisePlayerMapper seasonsFranchisePlayerMapper;

    public SeasonsFranchisePlayerQueryService(SeasonsFranchisePlayerRepository seasonsFranchisePlayerRepository, SeasonsFranchisePlayerMapper seasonsFranchisePlayerMapper) {
        this.seasonsFranchisePlayerRepository = seasonsFranchisePlayerRepository;
        this.seasonsFranchisePlayerMapper = seasonsFranchisePlayerMapper;
    }

    /**
     * Return a {@link List} of {@link SeasonsFranchisePlayerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SeasonsFranchisePlayerDTO> findByCriteria(SeasonsFranchisePlayerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<SeasonsFranchisePlayer> specification = createSpecification(criteria);
        return seasonsFranchisePlayerMapper.toDto(seasonsFranchisePlayerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SeasonsFranchisePlayerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SeasonsFranchisePlayerDTO> findByCriteria(SeasonsFranchisePlayerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<SeasonsFranchisePlayer> specification = createSpecification(criteria);
        final Page<SeasonsFranchisePlayer> result = seasonsFranchisePlayerRepository.findAll(specification, page);
        return result.map(seasonsFranchisePlayerMapper::toDto);
    }

    /**
     * Function to convert SeasonsFranchisePlayerCriteria to a {@link Specifications}
     */
    private Specifications<SeasonsFranchisePlayer> createSpecification(SeasonsFranchisePlayerCriteria criteria) {
        Specifications<SeasonsFranchisePlayer> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SeasonsFranchisePlayer_.id));
            }
            if (criteria.getBidPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBidPrice(), SeasonsFranchisePlayer_.bidPrice));
            }
            if (criteria.getSeasonsFranchiseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSeasonsFranchiseId(), SeasonsFranchisePlayer_.seasonsFranchise, SeasonsFranchise_.id));
            }
            if (criteria.getPlayerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPlayerId(), SeasonsFranchisePlayer_.player, Player_.id));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getActive(), SeasonsFranchisePlayer_.player, Player_.active));
            }
        }
        return specification;
    }

}
