package com.firstfuel.fafi.service;

import com.firstfuel.fafi.service.dto.SeasonsFranchiseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SeasonsFranchise.
 */
public interface SeasonsFranchiseService {

    /**
     * Save a seasonsFranchise.
     *
     * @param seasonsFranchiseDTO the entity to save
     * @return the persisted entity
     */
    SeasonsFranchiseDTO save(SeasonsFranchiseDTO seasonsFranchiseDTO);

    /**
     * Get all the seasonsFranchises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SeasonsFranchiseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" seasonsFranchise.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SeasonsFranchiseDTO findOne(Long id);

    /**
     * Delete the "id" seasonsFranchise.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
