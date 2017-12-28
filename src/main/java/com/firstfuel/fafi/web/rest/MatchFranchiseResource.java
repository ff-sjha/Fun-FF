package com.firstfuel.fafi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.firstfuel.fafi.service.MatchFranchiseService;
import com.firstfuel.fafi.web.rest.errors.BadRequestAlertException;
import com.firstfuel.fafi.web.rest.util.HeaderUtil;
import com.firstfuel.fafi.web.rest.util.PaginationUtil;
import com.firstfuel.fafi.service.dto.MatchFranchiseDTO;
import com.firstfuel.fafi.service.dto.MatchFranchiseCriteria;
import com.firstfuel.fafi.service.MatchFranchiseQueryService;
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
 * REST controller for managing MatchFranchise.
 */
@RestController
@RequestMapping("/api")
public class MatchFranchiseResource {

    private final Logger log = LoggerFactory.getLogger(MatchFranchiseResource.class);

    private static final String ENTITY_NAME = "matchFranchise";

    private final MatchFranchiseService matchFranchiseService;

    private final MatchFranchiseQueryService matchFranchiseQueryService;

    public MatchFranchiseResource(MatchFranchiseService matchFranchiseService, MatchFranchiseQueryService matchFranchiseQueryService) {
        this.matchFranchiseService = matchFranchiseService;
        this.matchFranchiseQueryService = matchFranchiseQueryService;
    }

    /**
     * POST  /match-franchises : Create a new matchFranchise.
     *
     * @param matchFranchiseDTO the matchFranchiseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new matchFranchiseDTO, or with status 400 (Bad Request) if the matchFranchise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/match-franchises")
    @Timed
    public ResponseEntity<MatchFranchiseDTO> createMatchFranchise(@Valid @RequestBody MatchFranchiseDTO matchFranchiseDTO) throws URISyntaxException {
        log.debug("REST request to save MatchFranchise : {}", matchFranchiseDTO);
        if (matchFranchiseDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchFranchise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchFranchiseDTO result = matchFranchiseService.save(matchFranchiseDTO);
        return ResponseEntity.created(new URI("/api/match-franchises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /match-franchises : Updates an existing matchFranchise.
     *
     * @param matchFranchiseDTO the matchFranchiseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated matchFranchiseDTO,
     * or with status 400 (Bad Request) if the matchFranchiseDTO is not valid,
     * or with status 500 (Internal Server Error) if the matchFranchiseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/match-franchises")
    @Timed
    public ResponseEntity<MatchFranchiseDTO> updateMatchFranchise(@Valid @RequestBody MatchFranchiseDTO matchFranchiseDTO) throws URISyntaxException {
        log.debug("REST request to update MatchFranchise : {}", matchFranchiseDTO);
        if (matchFranchiseDTO.getId() == null) {
            return createMatchFranchise(matchFranchiseDTO);
        }
        MatchFranchiseDTO result = matchFranchiseService.save(matchFranchiseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, matchFranchiseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /match-franchises : get all the matchFranchises.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of matchFranchises in body
     */
    @GetMapping("/match-franchises")
    @Timed
    public ResponseEntity<List<MatchFranchiseDTO>> getAllMatchFranchises(MatchFranchiseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MatchFranchises by criteria: {}", criteria);
        Page<MatchFranchiseDTO> page = matchFranchiseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/match-franchises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /match-franchises/:id : get the "id" matchFranchise.
     *
     * @param id the id of the matchFranchiseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the matchFranchiseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/match-franchises/{id}")
    @Timed
    public ResponseEntity<MatchFranchiseDTO> getMatchFranchise(@PathVariable Long id) {
        log.debug("REST request to get MatchFranchise : {}", id);
        MatchFranchiseDTO matchFranchiseDTO = matchFranchiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(matchFranchiseDTO));
    }

    /**
     * DELETE  /match-franchises/:id : delete the "id" matchFranchise.
     *
     * @param id the id of the matchFranchiseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/match-franchises/{id}")
    @Timed
    public ResponseEntity<Void> deleteMatchFranchise(@PathVariable Long id) {
        log.debug("REST request to delete MatchFranchise : {}", id);
        matchFranchiseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
