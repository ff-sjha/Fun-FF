package com.firstfuel.fafi.service.impl;

import com.firstfuel.fafi.service.SeasonsFranchiseService;
import com.firstfuel.fafi.domain.SeasonsFranchise;
import com.firstfuel.fafi.repository.SeasonsFranchiseRepository;
import com.firstfuel.fafi.service.dto.SeasonsFranchiseDTO;
import com.firstfuel.fafi.service.mapper.SeasonsFranchiseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SeasonsFranchise.
 */
@Service
@Transactional
public class SeasonsFranchiseServiceImpl implements SeasonsFranchiseService{

    private final Logger log = LoggerFactory.getLogger(SeasonsFranchiseServiceImpl.class);

    private final SeasonsFranchiseRepository seasonsFranchiseRepository;

    private final SeasonsFranchiseMapper seasonsFranchiseMapper;

    public SeasonsFranchiseServiceImpl(SeasonsFranchiseRepository seasonsFranchiseRepository, SeasonsFranchiseMapper seasonsFranchiseMapper) {
        this.seasonsFranchiseRepository = seasonsFranchiseRepository;
        this.seasonsFranchiseMapper = seasonsFranchiseMapper;
    }

    /**
     * Save a seasonsFranchise.
     *
     * @param seasonsFranchiseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeasonsFranchiseDTO save(SeasonsFranchiseDTO seasonsFranchiseDTO) {
        log.debug("Request to save SeasonsFranchise : {}", seasonsFranchiseDTO);
        SeasonsFranchise seasonsFranchise = seasonsFranchiseMapper.toEntity(seasonsFranchiseDTO);
        seasonsFranchise = seasonsFranchiseRepository.save(seasonsFranchise);
        return seasonsFranchiseMapper.toDto(seasonsFranchise);
    }

    /**
     * Get all the seasonsFranchises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SeasonsFranchiseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SeasonsFranchises");
        return seasonsFranchiseRepository.findAll(pageable)
            .map(seasonsFranchiseMapper::toDto);
    }

    /**
     * Get one seasonsFranchise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SeasonsFranchiseDTO findOne(Long id) {
        log.debug("Request to get SeasonsFranchise : {}", id);
        SeasonsFranchise seasonsFranchise = seasonsFranchiseRepository.findOne(id);
        return seasonsFranchiseMapper.toDto(seasonsFranchise);
    }

    /**
     * Delete the seasonsFranchise by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SeasonsFranchise : {}", id);
        seasonsFranchiseRepository.delete(id);
    }
}
