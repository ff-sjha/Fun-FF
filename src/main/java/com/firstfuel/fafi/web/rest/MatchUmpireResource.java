package com.firstfuel.fafi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.firstfuel.fafi.domain.MatchUmpire;
import com.firstfuel.fafi.service.MatchUmpireService;
import com.firstfuel.fafi.web.rest.errors.BadRequestAlertException;
import com.firstfuel.fafi.web.rest.util.HeaderUtil;
import com.firstfuel.fafi.web.rest.util.PaginationUtil;
import com.firstfuel.fafi.service.dto.MatchUmpireCriteria;
import com.firstfuel.fafi.service.MatchUmpireQueryService;
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
 * REST controller for managing MatchUmpire.
 */
@RestController
@RequestMapping("/api")
public class MatchUmpireResource {

    private final Logger log = LoggerFactory.getLogger(MatchUmpireResource.class);

    private static final String ENTITY_NAME = "matchUmpire";

    private final MatchUmpireService matchUmpireService;

    private final MatchUmpireQueryService matchUmpireQueryService;

    public MatchUmpireResource(MatchUmpireService matchUmpireService, MatchUmpireQueryService matchUmpireQueryService) {
        this.matchUmpireService = matchUmpireService;
        this.matchUmpireQueryService = matchUmpireQueryService;
    }

    /**
     * POST  /match-umpires : Create a new matchUmpire.
     *
     * @param matchUmpire the matchUmpire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new matchUmpire, or with status 400 (Bad Request) if the matchUmpire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/match-umpires")
    @Timed
    public ResponseEntity<MatchUmpire> createMatchUmpire(@Valid @RequestBody MatchUmpire matchUmpire) throws URISyntaxException {
        log.debug("REST request to save MatchUmpire : {}", matchUmpire);
        if (matchUmpire.getId() != null) {
            throw new BadRequestAlertException("A new matchUmpire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchUmpire result = matchUmpireService.save(matchUmpire);
        return ResponseEntity.created(new URI("/api/match-umpires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /match-umpires : Updates an existing matchUmpire.
     *
     * @param matchUmpire the matchUmpire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated matchUmpire,
     * or with status 400 (Bad Request) if the matchUmpire is not valid,
     * or with status 500 (Internal Server Error) if the matchUmpire couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/match-umpires")
    @Timed
    public ResponseEntity<MatchUmpire> updateMatchUmpire(@Valid @RequestBody MatchUmpire matchUmpire) throws URISyntaxException {
        log.debug("REST request to update MatchUmpire : {}", matchUmpire);
        if (matchUmpire.getId() == null) {
            return createMatchUmpire(matchUmpire);
        }
        MatchUmpire result = matchUmpireService.save(matchUmpire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, matchUmpire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /match-umpires : get all the matchUmpires.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of matchUmpires in body
     */
    @GetMapping("/match-umpires")
    @Timed
    public ResponseEntity<List<MatchUmpire>> getAllMatchUmpires(MatchUmpireCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MatchUmpires by criteria: {}", criteria);
        Page<MatchUmpire> page = matchUmpireQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/match-umpires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /match-umpires/:id : get the "id" matchUmpire.
     *
     * @param id the id of the matchUmpire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the matchUmpire, or with status 404 (Not Found)
     */
    @GetMapping("/match-umpires/{id}")
    @Timed
    public ResponseEntity<MatchUmpire> getMatchUmpire(@PathVariable Long id) {
        log.debug("REST request to get MatchUmpire : {}", id);
        MatchUmpire matchUmpire = matchUmpireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(matchUmpire));
    }

    /**
     * DELETE  /match-umpires/:id : delete the "id" matchUmpire.
     *
     * @param id the id of the matchUmpire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/match-umpires/{id}")
    @Timed
    public ResponseEntity<Void> deleteMatchUmpire(@PathVariable Long id) {
        log.debug("REST request to delete MatchUmpire : {}", id);
        matchUmpireService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
