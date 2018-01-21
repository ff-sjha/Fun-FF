package com.firstfuel.fafi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import com.firstfuel.fafi.domain.Franchise_;
import com.firstfuel.fafi.domain.Player_;
import com.firstfuel.fafi.domain.Season_;
import com.firstfuel.fafi.domain.SeasonsFranchise;
import com.firstfuel.fafi.domain.SeasonsFranchise_;
import com.firstfuel.fafi.repository.SeasonsFranchiseRepository;
import com.firstfuel.fafi.service.dto.SeasonDTO;
import com.firstfuel.fafi.service.dto.SeasonsFranchiseCriteria;
import com.firstfuel.fafi.service.dto.SeasonsFranchiseDTO;
import com.firstfuel.fafi.service.mapper.SeasonsFranchiseMapper;

import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.LongFilter;

/**
 * Service for executing complex queries for SeasonsFranchise entities in the
 * database. The main input is a {@link SeasonsFranchiseCriteria} which get's
 * converted to {@link Specifications}, in a way that all the filters must
 * apply. It returns a {@link List} of {@link SeasonsFranchiseDTO} or a
 * {@link Page} of {@link SeasonsFranchiseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SeasonsFranchiseQueryService extends QueryService<SeasonsFranchise> {

    private final Logger log = LoggerFactory.getLogger(SeasonsFranchiseQueryService.class);

    private final SeasonsFranchiseRepository seasonsFranchiseRepository;

    private final SeasonsFranchiseMapper seasonsFranchiseMapper;

    @Autowired
    private SeasonQueryService seasonQueryService;

    public SeasonsFranchiseQueryService(SeasonsFranchiseRepository seasonsFranchiseRepository,
            SeasonsFranchiseMapper seasonsFranchiseMapper) {
        this.seasonsFranchiseRepository = seasonsFranchiseRepository;
        this.seasonsFranchiseMapper = seasonsFranchiseMapper;
    }

    /**
     * Return a {@link List} of {@link SeasonsFranchiseDTO} which matches the
     * criteria from the database
     * 
     * @param criteria
     *            The object which holds all the filters, which the entities should
     *            match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SeasonsFranchiseDTO> findByCriteria(SeasonsFranchiseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<SeasonsFranchise> specification = createSpecification(criteria);
        return seasonsFranchiseMapper.toDto(seasonsFranchiseRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public List<SeasonsFranchiseDTO> findActiveSeasonFranchise() {
        SeasonDTO active = seasonQueryService.getActiveSeason();
        SeasonsFranchiseCriteria criteria = new SeasonsFranchiseCriteria();
        LongFilter seasonId = new LongFilter();
        seasonId.setEquals(active.getId());
        criteria.setSeasonId(seasonId);
        final Specifications<SeasonsFranchise> specification = createSpecification(criteria);
        return seasonsFranchiseMapper.toDto(seasonsFranchiseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SeasonsFranchiseDTO} which matches the
     * criteria from the database
     * 
     * @param criteria
     *            The object which holds all the filters, which the entities should
     *            match.
     * @param page
     *            The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SeasonsFranchiseDTO> findByCriteria(SeasonsFranchiseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<SeasonsFranchise> specification = createSpecification(criteria);
        final Page<SeasonsFranchise> result = seasonsFranchiseRepository.findAll(specification, page);
        return result.map(seasonsFranchiseMapper::toDto);
    }

    /**
     * Function to convert SeasonsFranchiseCriteria to a {@link Specifications}
     */
    private Specifications<SeasonsFranchise> createSpecification(SeasonsFranchiseCriteria criteria) {
        Specifications<SeasonsFranchise> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SeasonsFranchise_.id));
            }
            if (criteria.getSeasonId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSeasonId(),
                        SeasonsFranchise_.season, Season_.id));
            }
            if (criteria.getFranchiseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getFranchiseId(),
                        SeasonsFranchise_.franchise, Franchise_.id));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(
                        buildReferringEntitySpecification(criteria.getOwnerId(), SeasonsFranchise_.owner, Player_.id));
            }
            if (criteria.getIconPlayerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getIconPlayerId(),
                        SeasonsFranchise_.iconPlayer, Player_.id));
            }
        }
        return specification;
    }

}
