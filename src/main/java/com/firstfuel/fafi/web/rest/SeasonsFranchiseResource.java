package com.firstfuel.fafi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.firstfuel.fafi.service.SeasonsFranchiseService;
import com.firstfuel.fafi.web.rest.errors.BadRequestAlertException;
import com.firstfuel.fafi.web.rest.util.HeaderUtil;
import com.firstfuel.fafi.web.rest.util.PaginationUtil;
import com.firstfuel.fafi.service.dto.SeasonsFranchiseDTO;
import com.firstfuel.fafi.service.dto.SeasonsFranchiseCriteria;
import com.firstfuel.fafi.service.SeasonsFranchiseQueryService;
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
 * REST controller for managing SeasonsFranchise.
 */
@RestController
@RequestMapping("/api")
public class SeasonsFranchiseResource {

    private final Logger log = LoggerFactory.getLogger(SeasonsFranchiseResource.class);

    private static final String ENTITY_NAME = "seasonsFranchise";

    private final SeasonsFranchiseService seasonsFranchiseService;

    private final SeasonsFranchiseQueryService seasonsFranchiseQueryService;

    public SeasonsFranchiseResource(SeasonsFranchiseService seasonsFranchiseService, SeasonsFranchiseQueryService seasonsFranchiseQueryService) {
        this.seasonsFranchiseService = seasonsFranchiseService;
        this.seasonsFranchiseQueryService = seasonsFranchiseQueryService;
    }

    /**
     * POST  /seasons-franchises : Create a new seasonsFranchise.
     *
     * @param seasonsFranchiseDTO the seasonsFranchiseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seasonsFranchiseDTO, or with status 400 (Bad Request) if the seasonsFranchise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seasons-franchises")
    @Timed
    public ResponseEntity<SeasonsFranchiseDTO> createSeasonsFranchise(@Valid @RequestBody SeasonsFranchiseDTO seasonsFranchiseDTO) throws URISyntaxException {
        log.debug("REST request to save SeasonsFranchise : {}", seasonsFranchiseDTO);
        if (seasonsFranchiseDTO.getId() != null) {
            throw new BadRequestAlertException("A new seasonsFranchise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeasonsFranchiseDTO result = seasonsFranchiseService.save(seasonsFranchiseDTO);
        return ResponseEntity.created(new URI("/api/seasons-franchises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seasons-franchises : Updates an existing seasonsFranchise.
     *
     * @param seasonsFranchiseDTO the seasonsFranchiseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seasonsFranchiseDTO,
     * or with status 400 (Bad Request) if the seasonsFranchiseDTO is not valid,
     * or with status 500 (Internal Server Error) if the seasonsFranchiseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seasons-franchises")
    @Timed
    public ResponseEntity<SeasonsFranchiseDTO> updateSeasonsFranchise(@Valid @RequestBody SeasonsFranchiseDTO seasonsFranchiseDTO) throws URISyntaxException {
        log.debug("REST request to update SeasonsFranchise : {}", seasonsFranchiseDTO);
        if (seasonsFranchiseDTO.getId() == null) {
            return createSeasonsFranchise(seasonsFranchiseDTO);
        }
        SeasonsFranchiseDTO result = seasonsFranchiseService.save(seasonsFranchiseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seasonsFranchiseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seasons-franchises : get all the seasonsFranchises.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of seasonsFranchises in body
     */
    @GetMapping("/seasons-franchises")
    @Timed
    public ResponseEntity<List<SeasonsFranchiseDTO>> getAllSeasonsFranchises(SeasonsFranchiseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SeasonsFranchises by criteria: {}", criteria);
        Page<SeasonsFranchiseDTO> page = seasonsFranchiseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/seasons-franchises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/seasons-franchises/active")
    @Timed
    public ResponseEntity<List<SeasonsFranchiseDTO>> getAllActiveSeasonsFranchises(SeasonsFranchiseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SeasonsFranchises by criteria: {}", criteria);
        Page<SeasonsFranchiseDTO> page = seasonsFranchiseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/seasons-franchises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /seasons-franchises/:id : get the "id" seasonsFranchise.
     *
     * @param id the id of the seasonsFranchiseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seasonsFranchiseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seasons-franchises/{id}")
    @Timed
    public ResponseEntity<SeasonsFranchiseDTO> getSeasonsFranchise(@PathVariable Long id) {
        log.debug("REST request to get SeasonsFranchise : {}", id);
        SeasonsFranchiseDTO seasonsFranchiseDTO = seasonsFranchiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seasonsFranchiseDTO));
    }

    /**
     * DELETE  /seasons-franchises/:id : delete the "id" seasonsFranchise.
     *
     * @param id the id of the seasonsFranchiseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seasons-franchises/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeasonsFranchise(@PathVariable Long id) {
        log.debug("REST request to delete SeasonsFranchise : {}", id);
        seasonsFranchiseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
