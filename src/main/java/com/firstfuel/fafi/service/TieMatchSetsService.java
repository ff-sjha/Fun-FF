package com.firstfuel.fafi.service;

import com.firstfuel.fafi.domain.TieMatchSets;
import com.firstfuel.fafi.repository.TieMatchSetsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TieMatchSets.
 */
@Service
@Transactional
public class TieMatchSetsService {

    private final Logger log = LoggerFactory.getLogger(TieMatchSetsService.class);

    private final TieMatchSetsRepository tieMatchSetsRepository;

    public TieMatchSetsService(TieMatchSetsRepository tieMatchSetsRepository) {
        this.tieMatchSetsRepository = tieMatchSetsRepository;
    }

    /**
     * Save a tieMatchSets.
     *
     * @param tieMatchSets the entity to save
     * @return the persisted entity
     */
    public TieMatchSets save(TieMatchSets tieMatchSets) {
        log.debug("Request to save TieMatchSets : {}", tieMatchSets);
        return tieMatchSetsRepository.save(tieMatchSets);
    }

    /**
     * Get all the tieMatchSets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TieMatchSets> findAll(Pageable pageable) {
        log.debug("Request to get all TieMatchSets");
        return tieMatchSetsRepository.findAll(pageable);
    }

    /**
     * Get one tieMatchSets by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TieMatchSets findOne(Long id) {
        log.debug("Request to get TieMatchSets : {}", id);
        return tieMatchSetsRepository.findOne(id);
    }

    /**
     * Delete the tieMatchSets by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TieMatchSets : {}", id);
        tieMatchSetsRepository.delete(id);
    }
}
