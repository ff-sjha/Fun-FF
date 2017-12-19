package com.firstfuel.fafi.service;

import com.firstfuel.fafi.service.dto.SeasonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Season.
 */
public interface SeasonService {

    /**
     * Save a season.
     *
     * @param seasonDTO the entity to save
     * @return the persisted entity
     */
    SeasonDTO save(SeasonDTO seasonDTO);

    /**
     * Get all the seasons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SeasonDTO> findAll(Pageable pageable);

    /**
     * Get the "id" season.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SeasonDTO findOne(Long id);

    /**
     * Delete the "id" season.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
