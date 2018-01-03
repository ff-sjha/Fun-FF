package com.firstfuel.fafi.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.firstfuel.fafi.service.dto.PlayerCriteria;
import com.firstfuel.fafi.service.dto.PlayerDTO;
import com.firstfuel.fafi.web.rest.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class PointsTableResource {
    
    private final Logger log = LoggerFactory.getLogger(PointsTableResource.class);

    @GetMapping("/points-table")
    @Timed
    public ResponseEntity<List<PlayerDTO>> getAllPlayers(PlayerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Players by criteria: {}", criteria);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(null, "/api/points-table");
        return new ResponseEntity<>(new ArrayList<>(), headers, HttpStatus.OK);
    }
}
