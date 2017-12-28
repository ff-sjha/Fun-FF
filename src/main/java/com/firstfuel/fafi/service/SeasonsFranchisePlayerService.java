package com.firstfuel.fafi.service;

import com.firstfuel.fafi.service.dto.SeasonsFranchisePlayerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SeasonsFranchisePlayer.
 */
public interface SeasonsFranchisePlayerService {

    /**
     * Save a seasonsFranchisePlayer.
     *
     * @param seasonsFranchisePlayerDTO the entity to save
     * @return the persisted entity
     */
    SeasonsFranchisePlayerDTO save(SeasonsFranchisePlayerDTO seasonsFranchisePlayerDTO);

    /**
     * Get all the seasonsFranchisePlayers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SeasonsFranchisePlayerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" seasonsFranchisePlayer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SeasonsFranchisePlayerDTO findOne(Long id);

    /**
     * Delete the "id" seasonsFranchisePlayer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
