package com.firstfuel.fafi.service;

import com.firstfuel.fafi.domain.SeasonExpense;
import com.firstfuel.fafi.repository.SeasonExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SeasonExpense.
 */
@Service
@Transactional
public class SeasonExpenseService {

    private final Logger log = LoggerFactory.getLogger(SeasonExpenseService.class);

    private final SeasonExpenseRepository seasonExpenseRepository;

    public SeasonExpenseService(SeasonExpenseRepository seasonExpenseRepository) {
        this.seasonExpenseRepository = seasonExpenseRepository;
    }

    /**
     * Save a seasonExpense.
     *
     * @param seasonExpense the entity to save
     * @return the persisted entity
     */
    public SeasonExpense save(SeasonExpense seasonExpense) {
        log.debug("Request to save SeasonExpense : {}", seasonExpense);
        return seasonExpenseRepository.save(seasonExpense);
    }

    /**
     * Get all the seasonExpenses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SeasonExpense> findAll(Pageable pageable) {
        log.debug("Request to get all SeasonExpenses");
        return seasonExpenseRepository.findAll(pageable);
    }

    /**
     * Get one seasonExpense by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SeasonExpense findOne(Long id) {
        log.debug("Request to get SeasonExpense : {}", id);
        return seasonExpenseRepository.findOne(id);
    }

    /**
     * Delete the seasonExpense by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SeasonExpense : {}", id);
        seasonExpenseRepository.delete(id);
    }
}
