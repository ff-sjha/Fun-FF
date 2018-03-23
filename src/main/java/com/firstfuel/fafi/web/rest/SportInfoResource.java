package com.firstfuel.fafi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.firstfuel.fafi.domain.SportInfo;

import com.firstfuel.fafi.repository.SportInfoRepository;
import com.firstfuel.fafi.web.rest.errors.BadRequestAlertException;
import com.firstfuel.fafi.web.rest.util.HeaderUtil;
import com.firstfuel.fafi.web.rest.util.PaginationUtil;
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
 * REST controller for managing SportInfo.
 */
@RestController
@RequestMapping("/api")
public class SportInfoResource {

    private final Logger log = LoggerFactory.getLogger(SportInfoResource.class);

    private static final String ENTITY_NAME = "sportInfo";

    private final SportInfoRepository sportInfoRepository;

    public SportInfoResource(SportInfoRepository sportInfoRepository) {
        this.sportInfoRepository = sportInfoRepository;
    }

    /**
     * POST  /sport-infos : Create a new sportInfo.
     *
     * @param sportInfo the sportInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sportInfo, or with status 400 (Bad Request) if the sportInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sport-infos")
    @Timed
    public ResponseEntity<SportInfo> createSportInfo(@Valid @RequestBody SportInfo sportInfo) throws URISyntaxException {
        log.debug("REST request to save SportInfo : {}", sportInfo);
        if (sportInfo.getId() != null) {
            throw new BadRequestAlertException("A new sportInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SportInfo result = sportInfoRepository.save(sportInfo);
        return ResponseEntity.created(new URI("/api/sport-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sport-infos : Updates an existing sportInfo.
     *
     * @param sportInfo the sportInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sportInfo,
     * or with status 400 (Bad Request) if the sportInfo is not valid,
     * or with status 500 (Internal Server Error) if the sportInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sport-infos")
    @Timed
    public ResponseEntity<SportInfo> updateSportInfo(@Valid @RequestBody SportInfo sportInfo) throws URISyntaxException {
        log.debug("REST request to update SportInfo : {}", sportInfo);
        if (sportInfo.getId() == null) {
            return createSportInfo(sportInfo);
        }
        SportInfo result = sportInfoRepository.save(sportInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sportInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sport-infos : get all the sportInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sportInfos in body
     */
    @GetMapping("/sport-infos")
    @Timed
    public ResponseEntity<List<SportInfo>> getAllSportInfos(Pageable pageable) {
        log.debug("REST request to get a page of SportInfos");
        Page<SportInfo> page = sportInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sport-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sport-infos/:id : get the "id" sportInfo.
     *
     * @param id the id of the sportInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sportInfo, or with status 404 (Not Found)
     */
    @GetMapping("/sport-infos/{id}")
    @Timed
    public ResponseEntity<SportInfo> getSportInfo(@PathVariable Long id) {
        log.debug("REST request to get SportInfo : {}", id);
        SportInfo sportInfo = sportInfoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sportInfo));
    }

    /**
     * DELETE  /sport-infos/:id : delete the "id" sportInfo.
     *
     * @param id the id of the sportInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sport-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteSportInfo(@PathVariable Long id) {
        log.debug("REST request to delete SportInfo : {}", id);
        sportInfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
