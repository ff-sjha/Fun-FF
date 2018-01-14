package com.firstfuel.fafi.service;

import com.firstfuel.fafi.domain.MatchUmpire;
import com.firstfuel.fafi.repository.MatchUmpireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MatchUmpire.
 */
@Service
@Transactional
public class MatchUmpireService {

    private final Logger log = LoggerFactory.getLogger(MatchUmpireService.class);

    private final MatchUmpireRepository matchUmpireRepository;

    public MatchUmpireService(MatchUmpireRepository matchUmpireRepository) {
        this.matchUmpireRepository = matchUmpireRepository;
    }

    /**
     * Save a matchUmpire.
     *
     * @param matchUmpire the entity to save
     * @return the persisted entity
     */
    public MatchUmpire save(MatchUmpire matchUmpire) {
        log.debug("Request to save MatchUmpire : {}", matchUmpire);
        return matchUmpireRepository.save(matchUmpire);
    }

    /**
     * Get all the matchUmpires.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MatchUmpire> findAll(Pageable pageable) {
        log.debug("Request to get all MatchUmpires");
        return matchUmpireRepository.findAll(pageable);
    }

    /**
     * Get one matchUmpire by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MatchUmpire findOne(Long id) {
        log.debug("Request to get MatchUmpire : {}", id);
        return matchUmpireRepository.findOne(id);
    }

    /**
     * Delete the matchUmpire by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MatchUmpire : {}", id);
        matchUmpireRepository.delete(id);
    }
}
