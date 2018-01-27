package com.firstfuel.fafi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.firstfuel.fafi.domain.TieMatchSets;
import com.firstfuel.fafi.service.TieMatchSetsService;
import com.firstfuel.fafi.web.rest.errors.BadRequestAlertException;
import com.firstfuel.fafi.web.rest.util.HeaderUtil;
import com.firstfuel.fafi.web.rest.util.PaginationUtil;
import com.firstfuel.fafi.service.dto.TieMatchSetsCriteria;
import com.firstfuel.fafi.service.TieMatchSetsQueryService;
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
 * REST controller for managing TieMatchSets.
 */
@RestController
@RequestMapping("/api")
public class TieMatchSetsResource {

    private final Logger log = LoggerFactory.getLogger(TieMatchSetsResource.class);

    private static final String ENTITY_NAME = "tieMatchSets";

    private final TieMatchSetsService tieMatchSetsService;

    private final TieMatchSetsQueryService tieMatchSetsQueryService;

    public TieMatchSetsResource(TieMatchSetsService tieMatchSetsService, TieMatchSetsQueryService tieMatchSetsQueryService) {
        this.tieMatchSetsService = tieMatchSetsService;
        this.tieMatchSetsQueryService = tieMatchSetsQueryService;
    }

    /**
     * POST  /tie-match-sets : Create a new tieMatchSets.
     *
     * @param tieMatchSets the tieMatchSets to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tieMatchSets, or with status 400 (Bad Request) if the tieMatchSets has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tie-match-sets")
    @Timed
    public ResponseEntity<TieMatchSets> createTieMatchSets(@Valid @RequestBody TieMatchSets tieMatchSets) throws URISyntaxException {
        log.debug("REST request to save TieMatchSets : {}", tieMatchSets);
        if (tieMatchSets.getId() != null) {
            throw new BadRequestAlertException("A new tieMatchSets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TieMatchSets result = tieMatchSetsService.save(tieMatchSets);
        return ResponseEntity.created(new URI("/api/tie-match-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tie-match-sets : Updates an existing tieMatchSets.
     *
     * @param tieMatchSets the tieMatchSets to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tieMatchSets,
     * or with status 400 (Bad Request) if the tieMatchSets is not valid,
     * or with status 500 (Internal Server Error) if the tieMatchSets couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tie-match-sets")
    @Timed
    public ResponseEntity<TieMatchSets> updateTieMatchSets(@Valid @RequestBody TieMatchSets tieMatchSets) throws URISyntaxException {
        log.debug("REST request to update TieMatchSets : {}", tieMatchSets);
        if (tieMatchSets.getId() == null) {
            return createTieMatchSets(tieMatchSets);
        }
        TieMatchSets result = tieMatchSetsService.save(tieMatchSets);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tieMatchSets.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tie-match-sets : get all the tieMatchSets.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of tieMatchSets in body
     */
    @GetMapping("/tie-match-sets")
    @Timed
    public ResponseEntity<List<TieMatchSets>> getAllTieMatchSets(TieMatchSetsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TieMatchSets by criteria: {}", criteria);
        Page<TieMatchSets> page = tieMatchSetsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tie-match-sets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tie-match-sets/:id : get the "id" tieMatchSets.
     *
     * @param id the id of the tieMatchSets to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tieMatchSets, or with status 404 (Not Found)
     */
    @GetMapping("/tie-match-sets/{id}")
    @Timed
    public ResponseEntity<TieMatchSets> getTieMatchSets(@PathVariable Long id) {
        log.debug("REST request to get TieMatchSets : {}", id);
        TieMatchSets tieMatchSets = tieMatchSetsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tieMatchSets));
    }

    /**
     * DELETE  /tie-match-sets/:id : delete the "id" tieMatchSets.
     *
     * @param id the id of the tieMatchSets to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tie-match-sets/{id}")
    @Timed
    public ResponseEntity<Void> deleteTieMatchSets(@PathVariable Long id) {
        log.debug("REST request to delete TieMatchSets : {}", id);
        tieMatchSetsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
