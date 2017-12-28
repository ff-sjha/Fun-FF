package com.firstfuel.fafi.service;

import com.firstfuel.fafi.service.dto.MatchPlayersDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MatchPlayers.
 */
public interface MatchPlayersService {

    /**
     * Save a matchPlayers.
     *
     * @param matchPlayersDTO the entity to save
     * @return the persisted entity
     */
    MatchPlayersDTO save(MatchPlayersDTO matchPlayersDTO);

    /**
     * Get all the matchPlayers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MatchPlayersDTO> findAll(Pageable pageable);

    /**
     * Get the "id" matchPlayers.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MatchPlayersDTO findOne(Long id);

    /**
     * Delete the "id" matchPlayers.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
