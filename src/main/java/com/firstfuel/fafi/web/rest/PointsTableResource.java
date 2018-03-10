package com.firstfuel.fafi.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.firstfuel.fafi.repository.PlayerRepository;
import com.firstfuel.fafi.repository.TournamentFranchisePointsRepository;
import com.firstfuel.fafi.service.dto.FranchisePointsDTO;
import com.firstfuel.fafi.service.dto.PlayerPointsDTO;
import com.firstfuel.fafi.service.dto.PointsTableCriteria;

import io.github.jhipster.service.filter.LongFilter;

@RestController
@RequestMapping("/api")
public class PointsTableResource {

    private final Logger log = LoggerFactory.getLogger(PointsTableResource.class);

    @Autowired
    private PlayerRepository playerRepo;
    @Autowired
    private TournamentFranchisePointsRepository frenchisePointRepo;

    @GetMapping("/points-table/players")
    @Timed
    public ResponseEntity<List<PlayerPointsDTO>> getAllPlayers(PointsTableCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PlayerPointsDTO by TournamentCriteria: {}", criteria);
        LongFilter tournamentId = criteria.getTournamentId();
        if (null != tournamentId && null != tournamentId.getEquals()) {
            LongFilter matchId = criteria.getMatchId();
            if (null != matchId && null != matchId.getEquals()) {
                return new ResponseEntity<>(playerRepo.getPlayerPoints(tournamentId.getEquals(), matchId.getEquals()),
                        new HttpHeaders(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(playerRepo.getPlayerPoints(tournamentId.getEquals()), new HttpHeaders(),
                        HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(playerRepo.getPlayerPoints(), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/points-table/franchise")
    @Timed
    public ResponseEntity<List<FranchisePointsDTO>> getAllFranchise(PointsTableCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Players by criteria: {}", criteria);
        return new ResponseEntity<>(frenchisePointRepo.getFranchisePoints(), new HttpHeaders(), HttpStatus.OK);
    }
}
