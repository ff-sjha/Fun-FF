package com.firstfuel.fafi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.firstfuel.fafi.service.SeasonsFranchisePlayerService;
import com.firstfuel.fafi.web.rest.errors.BadRequestAlertException;
import com.firstfuel.fafi.web.rest.util.HeaderUtil;
import com.firstfuel.fafi.web.rest.util.PaginationUtil;
import com.firstfuel.fafi.service.dto.SeasonsFranchisePlayerDTO;
import com.firstfuel.fafi.service.dto.SeasonsFranchisePlayerCriteria;
import com.firstfuel.fafi.service.SeasonsFranchisePlayerQueryService;
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
 * REST controller for managing SeasonsFranchisePlayer.
 */
@RestController
@RequestMapping("/api")
public class SeasonsFranchisePlayerResource {

    private final Logger log = LoggerFactory.getLogger(SeasonsFranchisePlayerResource.class);

    private static final String ENTITY_NAME = "seasonsFranchisePlayer";

    private final SeasonsFranchisePlayerService seasonsFranchisePlayerService;

    private final SeasonsFranchisePlayerQueryService seasonsFranchisePlayerQueryService;

    public SeasonsFranchisePlayerResource(SeasonsFranchisePlayerService seasonsFranchisePlayerService, SeasonsFranchisePlayerQueryService seasonsFranchisePlayerQueryService) {
        this.seasonsFranchisePlayerService = seasonsFranchisePlayerService;
        this.seasonsFranchisePlayerQueryService = seasonsFranchisePlayerQueryService;
    }

    /**
     * POST  /seasons-franchise-players : Create a new seasonsFranchisePlayer.
     *
     * @param seasonsFranchisePlayerDTO the seasonsFranchisePlayerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seasonsFranchisePlayerDTO, or with status 400 (Bad Request) if the seasonsFranchisePlayer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seasons-franchise-players")
    @Timed
    public ResponseEntity<SeasonsFranchisePlayerDTO> createSeasonsFranchisePlayer(@Valid @RequestBody SeasonsFranchisePlayerDTO seasonsFranchisePlayerDTO) throws URISyntaxException {
        log.debug("REST request to save SeasonsFranchisePlayer : {}", seasonsFranchisePlayerDTO);
        if (seasonsFranchisePlayerDTO.getId() != null) {
            throw new BadRequestAlertException("A new seasonsFranchisePlayer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeasonsFranchisePlayerDTO result = seasonsFranchisePlayerService.save(seasonsFranchisePlayerDTO);
        return ResponseEntity.created(new URI("/api/seasons-franchise-players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seasons-franchise-players : Updates an existing seasonsFranchisePlayer.
     *
     * @param seasonsFranchisePlayerDTO the seasonsFranchisePlayerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seasonsFranchisePlayerDTO,
     * or with status 400 (Bad Request) if the seasonsFranchisePlayerDTO is not valid,
     * or with status 500 (Internal Server Error) if the seasonsFranchisePlayerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seasons-franchise-players")
    @Timed
    public ResponseEntity<SeasonsFranchisePlayerDTO> updateSeasonsFranchisePlayer(@Valid @RequestBody SeasonsFranchisePlayerDTO seasonsFranchisePlayerDTO) throws URISyntaxException {
        log.debug("REST request to update SeasonsFranchisePlayer : {}", seasonsFranchisePlayerDTO);
        if (seasonsFranchisePlayerDTO.getId() == null) {
            return createSeasonsFranchisePlayer(seasonsFranchisePlayerDTO);
        }
        SeasonsFranchisePlayerDTO result = seasonsFranchisePlayerService.save(seasonsFranchisePlayerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seasonsFranchisePlayerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seasons-franchise-players : get all the seasonsFranchisePlayers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of seasonsFranchisePlayers in body
     */
    @GetMapping("/seasons-franchise-players")
    @Timed
    public ResponseEntity<List<SeasonsFranchisePlayerDTO>> getAllSeasonsFranchisePlayers(SeasonsFranchisePlayerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SeasonsFranchisePlayers by criteria: {}", criteria);
        Page<SeasonsFranchisePlayerDTO> page = seasonsFranchisePlayerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/seasons-franchise-players");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /seasons-franchise-players/:id : get the "id" seasonsFranchisePlayer.
     *
     * @param id the id of the seasonsFranchisePlayerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seasonsFranchisePlayerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seasons-franchise-players/{id}")
    @Timed
    public ResponseEntity<SeasonsFranchisePlayerDTO> getSeasonsFranchisePlayer(@PathVariable Long id) {
        log.debug("REST request to get SeasonsFranchisePlayer : {}", id);
        SeasonsFranchisePlayerDTO seasonsFranchisePlayerDTO = seasonsFranchisePlayerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seasonsFranchisePlayerDTO));
    }

    /**
     * DELETE  /seasons-franchise-players/:id : delete the "id" seasonsFranchisePlayer.
     *
     * @param id the id of the seasonsFranchisePlayerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seasons-franchise-players/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeasonsFranchisePlayer(@PathVariable Long id) {
        log.debug("REST request to delete SeasonsFranchisePlayer : {}", id);
        seasonsFranchisePlayerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
