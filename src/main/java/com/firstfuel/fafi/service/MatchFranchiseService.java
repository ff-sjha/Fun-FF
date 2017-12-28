package com.firstfuel.fafi.service;

import com.firstfuel.fafi.service.dto.MatchFranchiseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MatchFranchise.
 */
public interface MatchFranchiseService {

    /**
     * Save a matchFranchise.
     *
     * @param matchFranchiseDTO the entity to save
     * @return the persisted entity
     */
    MatchFranchiseDTO save(MatchFranchiseDTO matchFranchiseDTO);

    /**
     * Get all the matchFranchises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MatchFranchiseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" matchFranchise.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MatchFranchiseDTO findOne(Long id);

    /**
     * Delete the "id" matchFranchise.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
