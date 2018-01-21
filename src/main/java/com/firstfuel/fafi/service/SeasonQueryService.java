package com.firstfuel.fafi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import com.firstfuel.fafi.domain.Franchise_;
import com.firstfuel.fafi.domain.Season;
import com.firstfuel.fafi.domain.Season_;
import com.firstfuel.fafi.domain.Tournament_;
import com.firstfuel.fafi.repository.SeasonRepository;
import com.firstfuel.fafi.service.dto.SeasonCriteria;
import com.firstfuel.fafi.service.dto.SeasonDTO;
import com.firstfuel.fafi.service.mapper.SeasonMapper;

import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.BooleanFilter;

/**
 * Service for executing complex queries for Season entities in the database.
 * The main input is a {@link SeasonCriteria} which get's converted to
 * {@link Specifications}, in a way that all the filters must apply. It returns
 * a {@link List} of {@link SeasonDTO} or a {@link Page} of {@link SeasonDTO}
 * which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SeasonQueryService extends QueryService<Season> {

    private final Logger log = LoggerFactory.getLogger(SeasonQueryService.class);

    private final SeasonRepository seasonRepository;

    private final SeasonMapper seasonMapper;

    public SeasonQueryService(SeasonRepository seasonRepository, SeasonMapper seasonMapper) {
        this.seasonRepository = seasonRepository;
        this.seasonMapper = seasonMapper;
    }

    /**
     * Return a {@link List} of {@link SeasonDTO} which matches the criteria from
     * the database
     * 
     * @param criteria
     *            The object which holds all the filters, which the entities should
     *            match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SeasonDTO> findByCriteria(SeasonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Season> specification = createSpecification(criteria);
        return seasonMapper.toDto(seasonRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public SeasonDTO getActiveSeason() {
        SeasonCriteria criteria = new SeasonCriteria();
        BooleanFilter active = new BooleanFilter();
        active.setEquals(true);
        criteria.setActive(active);
        final Specifications<Season> specification = createSpecification(criteria);
        Season season = seasonRepository.findOne(specification);
        return seasonMapper.toDto(season);
    }

    /**
     * Return a {@link Page} of {@link SeasonDTO} which matches the criteria from
     * the database
     * 
     * @param criteria
     *            The object which holds all the filters, which the entities should
     *            match.
     * @param page
     *            The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SeasonDTO> findByCriteria(SeasonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Season> specification = createSpecification(criteria);
        final Page<Season> result = seasonRepository.findAll(specification, page);
        return result.map(seasonMapper::toDto);
    }

    /**
     * Function to convert SeasonCriteria to a {@link Specifications}
     */
    private Specifications<Season> createSpecification(SeasonCriteria criteria) {
        Specifications<Season> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Season_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Season_.name));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Season_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), Season_.endDate));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Season_.active));
            }
            if (criteria.getTournamentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTournamentId(),
                        Season_.tournaments, Tournament_.id));
            }
            if (criteria.getWinnerId() != null) {
                specification = specification
                        .and(buildReferringEntitySpecification(criteria.getWinnerId(), Season_.winner, Franchise_.id));
            }
        }
        return specification;
    }

}
