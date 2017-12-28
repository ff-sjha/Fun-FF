package com.firstfuel.fafi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.firstfuel.fafi.service.MatchPlayersService;
import com.firstfuel.fafi.web.rest.errors.BadRequestAlertException;
import com.firstfuel.fafi.web.rest.util.HeaderUtil;
import com.firstfuel.fafi.web.rest.util.PaginationUtil;
import com.firstfuel.fafi.service.dto.MatchPlayersDTO;
import com.firstfuel.fafi.service.dto.MatchPlayersCriteria;
import com.firstfuel.fafi.service.MatchPlayersQueryService;
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
 * REST controller for managing MatchPlayers.
 */
@RestController
@RequestMapping("/api")
public class MatchPlayersResource {

    private final Logger log = LoggerFactory.getLogger(MatchPlayersResource.class);

    private static final String ENTITY_NAME = "matchPlayers";

    private final MatchPlayersService matchPlayersService;

    private final MatchPlayersQueryService matchPlayersQueryService;

    public MatchPlayersResource(MatchPlayersService matchPlayersService, MatchPlayersQueryService matchPlayersQueryService) {
        this.matchPlayersService = matchPlayersService;
        this.matchPlayersQueryService = matchPlayersQueryService;
    }

    /**
     * POST  /match-players : Create a new matchPlayers.
     *
     * @param matchPlayersDTO the matchPlayersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new matchPlayersDTO, or with status 400 (Bad Request) if the matchPlayers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/match-players")
    @Timed
    public ResponseEntity<MatchPlayersDTO> createMatchPlayers(@Valid @RequestBody MatchPlayersDTO matchPlayersDTO) throws URISyntaxException {
        log.debug("REST request to save MatchPlayers : {}", matchPlayersDTO);
        if (matchPlayersDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchPlayers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchPlayersDTO result = matchPlayersService.save(matchPlayersDTO);
        return ResponseEntity.created(new URI("/api/match-players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /match-players : Updates an existing matchPlayers.
     *
     * @param matchPlayersDTO the matchPlayersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated matchPlayersDTO,
     * or with status 400 (Bad Request) if the matchPlayersDTO is not valid,
     * or with status 500 (Internal Server Error) if the matchPlayersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/match-players")
    @Timed
    public ResponseEntity<MatchPlayersDTO> updateMatchPlayers(@Valid @RequestBody MatchPlayersDTO matchPlayersDTO) throws URISyntaxException {
        log.debug("REST request to update MatchPlayers : {}", matchPlayersDTO);
        if (matchPlayersDTO.getId() == null) {
            return createMatchPlayers(matchPlayersDTO);
        }
        MatchPlayersDTO result = matchPlayersService.save(matchPlayersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, matchPlayersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /match-players : get all the matchPlayers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of matchPlayers in body
     */
    @GetMapping("/match-players")
    @Timed
    public ResponseEntity<List<MatchPlayersDTO>> getAllMatchPlayers(MatchPlayersCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MatchPlayers by criteria: {}", criteria);
        Page<MatchPlayersDTO> page = matchPlayersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/match-players");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /match-players/:id : get the "id" matchPlayers.
     *
     * @param id the id of the matchPlayersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the matchPlayersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/match-players/{id}")
    @Timed
    public ResponseEntity<MatchPlayersDTO> getMatchPlayers(@PathVariable Long id) {
        log.debug("REST request to get MatchPlayers : {}", id);
        MatchPlayersDTO matchPlayersDTO = matchPlayersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(matchPlayersDTO));
    }

    /**
     * DELETE  /match-players/:id : delete the "id" matchPlayers.
     *
     * @param id the id of the matchPlayersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/match-players/{id}")
    @Timed
    public ResponseEntity<Void> deleteMatchPlayers(@PathVariable Long id) {
        log.debug("REST request to delete MatchPlayers : {}", id);
        matchPlayersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
