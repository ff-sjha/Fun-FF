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

import com.firstfuel.fafi.domain.MatchFranchise;
import com.firstfuel.fafi.domain.*; // for static metamodels
import com.firstfuel.fafi.repository.MatchFranchiseRepository;
import com.firstfuel.fafi.service.dto.MatchFranchiseCriteria;

import com.firstfuel.fafi.service.dto.MatchFranchiseDTO;
import com.firstfuel.fafi.service.mapper.MatchFranchiseMapper;

/**
 * Service for executing complex queries for MatchFranchise entities in the database.
 * The main input is a {@link MatchFranchiseCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MatchFranchiseDTO} or a {@link Page} of {@link MatchFranchiseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MatchFranchiseQueryService extends QueryService<MatchFranchise> {

    private final Logger log = LoggerFactory.getLogger(MatchFranchiseQueryService.class);


    private final MatchFranchiseRepository matchFranchiseRepository;

    private final MatchFranchiseMapper matchFranchiseMapper;

    public MatchFranchiseQueryService(MatchFranchiseRepository matchFranchiseRepository, MatchFranchiseMapper matchFranchiseMapper) {
        this.matchFranchiseRepository = matchFranchiseRepository;
        this.matchFranchiseMapper = matchFranchiseMapper;
    }

    /**
     * Return a {@link List} of {@link MatchFranchiseDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MatchFranchiseDTO> findByCriteria(MatchFranchiseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<MatchFranchise> specification = createSpecification(criteria);
        return matchFranchiseMapper.toDto(matchFranchiseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MatchFranchiseDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MatchFranchiseDTO> findByCriteria(MatchFranchiseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<MatchFranchise> specification = createSpecification(criteria);
        final Page<MatchFranchise> result = matchFranchiseRepository.findAll(specification, page);
        return result.map(matchFranchiseMapper::toDto);
    }

    /**
     * Function to convert MatchFranchiseCriteria to a {@link Specifications}
     */
    private Specifications<MatchFranchise> createSpecification(MatchFranchiseCriteria criteria) {
        Specifications<MatchFranchise> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MatchFranchise_.id));
            }
            if (criteria.getMatchId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMatchId(), MatchFranchise_.match, Match_.id));
            }
            if (criteria.getSeasonsFranchiseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSeasonsFranchiseId(), MatchFranchise_.seasonsFranchise, SeasonsFranchise_.id));
            }
        }
        return specification;
    }

}
