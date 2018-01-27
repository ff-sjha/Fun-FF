package com.firstfuel.fafi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.firstfuel.fafi.domain.TieMatchPlayers;
import com.firstfuel.fafi.service.TieMatchPlayersService;
import com.firstfuel.fafi.web.rest.errors.BadRequestAlertException;
import com.firstfuel.fafi.web.rest.util.HeaderUtil;
import com.firstfuel.fafi.web.rest.util.PaginationUtil;
import com.firstfuel.fafi.service.dto.TieMatchPlayersCriteria;
import com.firstfuel.fafi.service.TieMatchPlayersQueryService;
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
 * REST controller for managing TieMatchPlayers.
 */
@RestController
@RequestMapping("/api")
public class TieMatchPlayersResource {

    private final Logger log = LoggerFactory.getLogger(TieMatchPlayersResource.class);

    private static final String ENTITY_NAME = "tieMatchPlayers";

    private final TieMatchPlayersService tieMatchPlayersService;

    private final TieMatchPlayersQueryService tieMatchPlayersQueryService;

    public TieMatchPlayersResource(TieMatchPlayersService tieMatchPlayersService, TieMatchPlayersQueryService tieMatchPlayersQueryService) {
        this.tieMatchPlayersService = tieMatchPlayersService;
        this.tieMatchPlayersQueryService = tieMatchPlayersQueryService;
    }

    /**
     * POST  /tie-match-players : Create a new tieMatchPlayers.
     *
     * @param tieMatchPlayers the tieMatchPlayers to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tieMatchPlayers, or with status 400 (Bad Request) if the tieMatchPlayers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tie-match-players")
    @Timed
    public ResponseEntity<TieMatchPlayers> createTieMatchPlayers(@Valid @RequestBody TieMatchPlayers tieMatchPlayers) throws URISyntaxException {
        log.debug("REST request to save TieMatchPlayers : {}", tieMatchPlayers);
        if (tieMatchPlayers.getId() != null) {
            throw new BadRequestAlertException("A new tieMatchPlayers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TieMatchPlayers result = tieMatchPlayersService.save(tieMatchPlayers);
        return ResponseEntity.created(new URI("/api/tie-match-players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tie-match-players : Updates an existing tieMatchPlayers.
     *
     * @param tieMatchPlayers the tieMatchPlayers to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tieMatchPlayers,
     * or with status 400 (Bad Request) if the tieMatchPlayers is not valid,
     * or with status 500 (Internal Server Error) if the tieMatchPlayers couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tie-match-players")
    @Timed
    public ResponseEntity<TieMatchPlayers> updateTieMatchPlayers(@Valid @RequestBody TieMatchPlayers tieMatchPlayers) throws URISyntaxException {
        log.debug("REST request to update TieMatchPlayers : {}", tieMatchPlayers);
        if (tieMatchPlayers.getId() == null) {
            return createTieMatchPlayers(tieMatchPlayers);
        }
        TieMatchPlayers result = tieMatchPlayersService.save(tieMatchPlayers);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tieMatchPlayers.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tie-match-players : get all the tieMatchPlayers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of tieMatchPlayers in body
     */
    @GetMapping("/tie-match-players")
    @Timed
    public ResponseEntity<List<TieMatchPlayers>> getAllTieMatchPlayers(TieMatchPlayersCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TieMatchPlayers by criteria: {}", criteria);
        Page<TieMatchPlayers> page = tieMatchPlayersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tie-match-players");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tie-match-players/:id : get the "id" tieMatchPlayers.
     *
     * @param id the id of the tieMatchPlayers to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tieMatchPlayers, or with status 404 (Not Found)
     */
    @GetMapping("/tie-match-players/{id}")
    @Timed
    public ResponseEntity<TieMatchPlayers> getTieMatchPlayers(@PathVariable Long id) {
        log.debug("REST request to get TieMatchPlayers : {}", id);
        TieMatchPlayers tieMatchPlayers = tieMatchPlayersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tieMatchPlayers));
    }

    /**
     * DELETE  /tie-match-players/:id : delete the "id" tieMatchPlayers.
     *
     * @param id the id of the tieMatchPlayers to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tie-match-players/{id}")
    @Timed
    public ResponseEntity<Void> deleteTieMatchPlayers(@PathVariable Long id) {
        log.debug("REST request to delete TieMatchPlayers : {}", id);
        tieMatchPlayersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
