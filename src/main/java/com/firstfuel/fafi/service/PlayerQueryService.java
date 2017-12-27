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

import com.firstfuel.fafi.domain.Player;
import com.firstfuel.fafi.domain.*; // for static metamodels
import com.firstfuel.fafi.repository.PlayerRepository;
import com.firstfuel.fafi.service.dto.PlayerCriteria;

import com.firstfuel.fafi.service.dto.PlayerDTO;
import com.firstfuel.fafi.service.mapper.PlayerMapper;

/**
 * Service for executing complex queries for Player entities in the database.
 * The main input is a {@link PlayerCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlayerDTO} or a {@link Page} of {@link PlayerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlayerQueryService extends QueryService<Player> {

    private final Logger log = LoggerFactory.getLogger(PlayerQueryService.class);


    private final PlayerRepository playerRepository;

    private final PlayerMapper playerMapper;

    public PlayerQueryService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    /**
     * Return a {@link List} of {@link PlayerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlayerDTO> findByCriteria(PlayerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Player> specification = createSpecification(criteria);
        return playerMapper.toDto(playerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlayerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlayerDTO> findByCriteria(PlayerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Player> specification = createSpecification(criteria);
        final Page<Player> result = playerRepository.findAll(specification, page);
        return result.map(playerMapper::toDto);
    }

    /**
     * Function to convert PlayerCriteria to a {@link Specifications}
     */
    private Specifications<Player> createSpecification(PlayerCriteria criteria) {
        Specifications<Player> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Player_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Player_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Player_.lastName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Player_.email));
            }
        }
        return specification;
    }

}
