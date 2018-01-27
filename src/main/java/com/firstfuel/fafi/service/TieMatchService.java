package com.firstfuel.fafi.service;

import com.firstfuel.fafi.domain.TieMatch;
import com.firstfuel.fafi.repository.TieMatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TieMatch.
 */
@Service
@Transactional
public class TieMatchService {

    private final Logger log = LoggerFactory.getLogger(TieMatchService.class);

    private final TieMatchRepository tieMatchRepository;

    public TieMatchService(TieMatchRepository tieMatchRepository) {
        this.tieMatchRepository = tieMatchRepository;
    }

    /**
     * Save a tieMatch.
     *
     * @param tieMatch the entity to save
     * @return the persisted entity
     */
    public TieMatch save(TieMatch tieMatch) {
        log.debug("Request to save TieMatch : {}", tieMatch);
        return tieMatchRepository.save(tieMatch);
    }

    /**
     * Get all the tieMatches.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TieMatch> findAll(Pageable pageable) {
        log.debug("Request to get all TieMatches");
        return tieMatchRepository.findAll(pageable);
    }

    /**
     * Get one tieMatch by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TieMatch findOne(Long id) {
        log.debug("Request to get TieMatch : {}", id);
        return tieMatchRepository.findOne(id);
    }

    /**
     * Delete the tieMatch by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TieMatch : {}", id);
        tieMatchRepository.delete(id);
    }
}
