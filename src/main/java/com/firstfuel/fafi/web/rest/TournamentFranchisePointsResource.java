package com.firstfuel.fafi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.firstfuel.fafi.service.TournamentFranchisePointsService;
import com.firstfuel.fafi.web.rest.errors.BadRequestAlertException;
import com.firstfuel.fafi.web.rest.util.HeaderUtil;
import com.firstfuel.fafi.web.rest.util.PaginationUtil;
import com.firstfuel.fafi.service.dto.TournamentFranchisePointsDTO;
import com.firstfuel.fafi.service.dto.TournamentFranchisePointsCriteria;
import com.firstfuel.fafi.service.TournamentFranchisePointsQueryService;
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
 * REST controller for managing TournamentFranchisePoints.
 */
@RestController
@RequestMapping("/api")
public class TournamentFranchisePointsResource {

    private final Logger log = LoggerFactory.getLogger(TournamentFranchisePointsResource.class);

    private static final String ENTITY_NAME = "tournamentFranchisePoints";

    private final TournamentFranchisePointsService tournamentFranchisePointsService;

    private final TournamentFranchisePointsQueryService tournamentFranchisePointsQueryService;

    public TournamentFranchisePointsResource(TournamentFranchisePointsService tournamentFranchisePointsService, TournamentFranchisePointsQueryService tournamentFranchisePointsQueryService) {
        this.tournamentFranchisePointsService = tournamentFranchisePointsService;
        this.tournamentFranchisePointsQueryService = tournamentFranchisePointsQueryService;
    }

    /**
     * POST  /tournament-franchise-points : Create a new tournamentFranchisePoints.
     *
     * @param tournamentFranchisePointsDTO the tournamentFranchisePointsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tournamentFranchisePointsDTO, or with status 400 (Bad Request) if the tournamentFranchisePoints has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tournament-franchise-points")
    @Timed
    public ResponseEntity<TournamentFranchisePointsDTO> createTournamentFranchisePoints(@Valid @RequestBody TournamentFranchisePointsDTO tournamentFranchisePointsDTO) throws URISyntaxException {
        log.debug("REST request to save TournamentFranchisePoints : {}", tournamentFranchisePointsDTO);
        if (tournamentFranchisePointsDTO.getId() != null) {
            throw new BadRequestAlertException("A new tournamentFranchisePoints cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TournamentFranchisePointsDTO result = tournamentFranchisePointsService.save(tournamentFranchisePointsDTO);
        return ResponseEntity.created(new URI("/api/tournament-franchise-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tournament-franchise-points : Updates an existing tournamentFranchisePoints.
     *
     * @param tournamentFranchisePointsDTO the tournamentFranchisePointsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tournamentFranchisePointsDTO,
     * or with status 400 (Bad Request) if the tournamentFranchisePointsDTO is not valid,
     * or with status 500 (Internal Server Error) if the tournamentFranchisePointsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tournament-franchise-points")
    @Timed
    public ResponseEntity<TournamentFranchisePointsDTO> updateTournamentFranchisePoints(@Valid @RequestBody TournamentFranchisePointsDTO tournamentFranchisePointsDTO) throws URISyntaxException {
        log.debug("REST request to update TournamentFranchisePoints : {}", tournamentFranchisePointsDTO);
        if (tournamentFranchisePointsDTO.getId() == null) {
            return createTournamentFranchisePoints(tournamentFranchisePointsDTO);
        }
        TournamentFranchisePointsDTO result = tournamentFranchisePointsService.save(tournamentFranchisePointsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tournamentFranchisePointsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tournament-franchise-points : get all the tournamentFranchisePoints.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of tournamentFranchisePoints in body
     */
    @GetMapping("/tournament-franchise-points")
    @Timed
    public ResponseEntity<List<TournamentFranchisePointsDTO>> getAllTournamentFranchisePoints(TournamentFranchisePointsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TournamentFranchisePoints by criteria: {}", criteria);
        Page<TournamentFranchisePointsDTO> page = tournamentFranchisePointsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tournament-franchise-points");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tournament-franchise-points/:id : get the "id" tournamentFranchisePoints.
     *
     * @param id the id of the tournamentFranchisePointsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tournamentFranchisePointsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tournament-franchise-points/{id}")
    @Timed
    public ResponseEntity<TournamentFranchisePointsDTO> getTournamentFranchisePoints(@PathVariable Long id) {
        log.debug("REST request to get TournamentFranchisePoints : {}", id);
        TournamentFranchisePointsDTO tournamentFranchisePointsDTO = tournamentFranchisePointsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tournamentFranchisePointsDTO));
    }

    /**
     * DELETE  /tournament-franchise-points/:id : delete the "id" tournamentFranchisePoints.
     *
     * @param id the id of the tournamentFranchisePointsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tournament-franchise-points/{id}")
    @Timed
    public ResponseEntity<Void> deleteTournamentFranchisePoints(@PathVariable Long id) {
        log.debug("REST request to delete TournamentFranchisePoints : {}", id);
        tournamentFranchisePointsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
