package com.firstfuel.fafi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.firstfuel.fafi.domain.SeasonExpense;
import com.firstfuel.fafi.service.SeasonExpenseService;
import com.firstfuel.fafi.web.rest.errors.BadRequestAlertException;
import com.firstfuel.fafi.web.rest.util.HeaderUtil;
import com.firstfuel.fafi.web.rest.util.PaginationUtil;
import com.firstfuel.fafi.service.dto.SeasonExpenseCriteria;
import com.firstfuel.fafi.service.SeasonExpenseQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SeasonExpense.
 */
@RestController
@RequestMapping("/api")
public class SeasonExpenseResource {

    private final Logger log = LoggerFactory.getLogger(SeasonExpenseResource.class);

    private static final String ENTITY_NAME = "seasonExpense";

    private final SeasonExpenseService seasonExpenseService;

    private final SeasonExpenseQueryService seasonExpenseQueryService;

    public SeasonExpenseResource(SeasonExpenseService seasonExpenseService, SeasonExpenseQueryService seasonExpenseQueryService) {
        this.seasonExpenseService = seasonExpenseService;
        this.seasonExpenseQueryService = seasonExpenseQueryService;
    }

    /**
     * POST  /season-expenses : Create a new seasonExpense.
     *
     * @param seasonExpense the seasonExpense to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seasonExpense, or with status 400 (Bad Request) if the seasonExpense has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/season-expenses")
    @Timed
    public ResponseEntity<SeasonExpense> createSeasonExpense(@Valid @RequestBody SeasonExpense seasonExpense) throws URISyntaxException {
        log.debug("REST request to save SeasonExpense : {}", seasonExpense);
        if (seasonExpense.getId() != null) {
            throw new BadRequestAlertException("A new seasonExpense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeasonExpense result = seasonExpenseService.save(seasonExpense);
        return ResponseEntity.created(new URI("/api/season-expenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /season-expenses : Updates an existing seasonExpense.
     *
     * @param seasonExpense the seasonExpense to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seasonExpense,
     * or with status 400 (Bad Request) if the seasonExpense is not valid,
     * or with status 500 (Internal Server Error) if the seasonExpense couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/season-expenses")
    @Timed
    public ResponseEntity<SeasonExpense> updateSeasonExpense(@Valid @RequestBody SeasonExpense seasonExpense) throws URISyntaxException {
        log.debug("REST request to update SeasonExpense : {}", seasonExpense);
        if (seasonExpense.getId() == null) {
            return createSeasonExpense(seasonExpense);
        }
        SeasonExpense result = seasonExpenseService.save(seasonExpense);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seasonExpense.getId().toString()))
            .body(result);
    }

    /**
     * GET  /season-expenses : get all the seasonExpenses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of seasonExpenses in body
     */
    @GetMapping("/season-expenses")
    @Timed
    public ResponseEntity<List<SeasonExpense>> getAllSeasonExpenses(SeasonExpenseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SeasonExpenses by criteria: {}", criteria);
        Page<SeasonExpense> page = seasonExpenseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/season-expenses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /season-expenses/:id : get the "id" seasonExpense.
     *
     * @param id the id of the seasonExpense to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seasonExpense, or with status 404 (Not Found)
     */
    @GetMapping("/season-expenses/{id}")
    @Timed
    public ResponseEntity<SeasonExpense> getSeasonExpense(@PathVariable Long id) {
        log.debug("REST request to get SeasonExpense : {}", id);
        SeasonExpense seasonExpense = seasonExpenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seasonExpense));
    }

    /**
     * DELETE  /season-expenses/:id : delete the "id" seasonExpense.
     *
     * @param id the id of the seasonExpense to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/season-expenses/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeasonExpense(@PathVariable Long id) {
        log.debug("REST request to delete SeasonExpense : {}", id);
        seasonExpenseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
