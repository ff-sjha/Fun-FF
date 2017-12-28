package com.firstfuel.fafi.service.impl;

import com.firstfuel.fafi.service.SeasonsFranchisePlayerService;
import com.firstfuel.fafi.domain.SeasonsFranchisePlayer;
import com.firstfuel.fafi.repository.SeasonsFranchisePlayerRepository;
import com.firstfuel.fafi.service.dto.SeasonsFranchisePlayerDTO;
import com.firstfuel.fafi.service.mapper.SeasonsFranchisePlayerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SeasonsFranchisePlayer.
 */
@Service
@Transactional
public class SeasonsFranchisePlayerServiceImpl implements SeasonsFranchisePlayerService{

    private final Logger log = LoggerFactory.getLogger(SeasonsFranchisePlayerServiceImpl.class);

    private final SeasonsFranchisePlayerRepository seasonsFranchisePlayerRepository;

    private final SeasonsFranchisePlayerMapper seasonsFranchisePlayerMapper;

    public SeasonsFranchisePlayerServiceImpl(SeasonsFranchisePlayerRepository seasonsFranchisePlayerRepository, SeasonsFranchisePlayerMapper seasonsFranchisePlayerMapper) {
        this.seasonsFranchisePlayerRepository = seasonsFranchisePlayerRepository;
        this.seasonsFranchisePlayerMapper = seasonsFranchisePlayerMapper;
    }

    /**
     * Save a seasonsFranchisePlayer.
     *
     * @param seasonsFranchisePlayerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeasonsFranchisePlayerDTO save(SeasonsFranchisePlayerDTO seasonsFranchisePlayerDTO) {
        log.debug("Request to save SeasonsFranchisePlayer : {}", seasonsFranchisePlayerDTO);
        SeasonsFranchisePlayer seasonsFranchisePlayer = seasonsFranchisePlayerMapper.toEntity(seasonsFranchisePlayerDTO);
        seasonsFranchisePlayer = seasonsFranchisePlayerRepository.save(seasonsFranchisePlayer);
        return seasonsFranchisePlayerMapper.toDto(seasonsFranchisePlayer);
    }

    /**
     * Get all the seasonsFranchisePlayers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SeasonsFranchisePlayerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SeasonsFranchisePlayers");
        return seasonsFranchisePlayerRepository.findAll(pageable)
            .map(seasonsFranchisePlayerMapper::toDto);
    }

    /**
     * Get one seasonsFranchisePlayer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SeasonsFranchisePlayerDTO findOne(Long id) {
        log.debug("Request to get SeasonsFranchisePlayer : {}", id);
        SeasonsFranchisePlayer seasonsFranchisePlayer = seasonsFranchisePlayerRepository.findOne(id);
        return seasonsFranchisePlayerMapper.toDto(seasonsFranchisePlayer);
    }

    /**
     * Delete the seasonsFranchisePlayer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SeasonsFranchisePlayer : {}", id);
        seasonsFranchisePlayerRepository.delete(id);
    }
}
