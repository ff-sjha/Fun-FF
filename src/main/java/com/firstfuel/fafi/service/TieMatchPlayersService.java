package com.firstfuel.fafi.service;

import com.firstfuel.fafi.domain.TieMatchPlayers;
import com.firstfuel.fafi.repository.TieMatchPlayersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TieMatchPlayers.
 */
@Service
@Transactional
public class TieMatchPlayersService {

    private final Logger log = LoggerFactory.getLogger(TieMatchPlayersService.class);

    private final TieMatchPlayersRepository tieMatchPlayersRepository;

    public TieMatchPlayersService(TieMatchPlayersRepository tieMatchPlayersRepository) {
        this.tieMatchPlayersRepository = tieMatchPlayersRepository;
    }

    /**
     * Save a tieMatchPlayers.
     *
     * @param tieMatchPlayers the entity to save
     * @return the persisted entity
     */
    public TieMatchPlayers save(TieMatchPlayers tieMatchPlayers) {
        log.debug("Request to save TieMatchPlayers : {}", tieMatchPlayers);
        return tieMatchPlayersRepository.save(tieMatchPlayers);
    }

    /**
     * Get all the tieMatchPlayers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TieMatchPlayers> findAll(Pageable pageable) {
        log.debug("Request to get all TieMatchPlayers");
        return tieMatchPlayersRepository.findAll(pageable);
    }

    /**
     * Get one tieMatchPlayers by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TieMatchPlayers findOne(Long id) {
        log.debug("Request to get TieMatchPlayers : {}", id);
        return tieMatchPlayersRepository.findOne(id);
    }

    /**
     * Delete the tieMatchPlayers by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TieMatchPlayers : {}", id);
        tieMatchPlayersRepository.delete(id);
    }
}
