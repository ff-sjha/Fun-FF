package com.firstfuel.fafi.service;

import com.firstfuel.fafi.domain.TournamentFranchisePoints;
import com.firstfuel.fafi.repository.TournamentFranchisePointsRepository;
import com.firstfuel.fafi.service.dto.TournamentFranchisePointsDTO;
import com.firstfuel.fafi.service.mapper.TournamentFranchisePointsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TournamentFranchisePoints.
 */
@Service
@Transactional
public class TournamentFranchisePointsService {

    private final Logger log = LoggerFactory.getLogger(TournamentFranchisePointsService.class);

    private final TournamentFranchisePointsRepository tournamentFranchisePointsRepository;

    private final TournamentFranchisePointsMapper tournamentFranchisePointsMapper;

    public TournamentFranchisePointsService(TournamentFranchisePointsRepository tournamentFranchisePointsRepository, TournamentFranchisePointsMapper tournamentFranchisePointsMapper) {
        this.tournamentFranchisePointsRepository = tournamentFranchisePointsRepository;
        this.tournamentFranchisePointsMapper = tournamentFranchisePointsMapper;
    }

    /**
     * Save a tournamentFranchisePoints.
     *
     * @param tournamentFranchisePointsDTO the entity to save
     * @return the persisted entity
     */
    public TournamentFranchisePointsDTO save(TournamentFranchisePointsDTO tournamentFranchisePointsDTO) {
        log.debug("Request to save TournamentFranchisePoints : {}", tournamentFranchisePointsDTO);
        TournamentFranchisePoints tournamentFranchisePoints = tournamentFranchisePointsMapper.toEntity(tournamentFranchisePointsDTO);
        tournamentFranchisePoints = tournamentFranchisePointsRepository.save(tournamentFranchisePoints);
        return tournamentFranchisePointsMapper.toDto(tournamentFranchisePoints);
    }

    /**
     * Get all the tournamentFranchisePoints.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TournamentFranchisePointsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TournamentFranchisePoints");
        return tournamentFranchisePointsRepository.findAll(pageable)
            .map(tournamentFranchisePointsMapper::toDto);
    }

    /**
     * Get one tournamentFranchisePoints by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TournamentFranchisePointsDTO findOne(Long id) {
        log.debug("Request to get TournamentFranchisePoints : {}", id);
        TournamentFranchisePoints tournamentFranchisePoints = tournamentFranchisePointsRepository.findOne(id);
        return tournamentFranchisePointsMapper.toDto(tournamentFranchisePoints);
    }

    /**
     * Delete the tournamentFranchisePoints by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TournamentFranchisePoints : {}", id);
        tournamentFranchisePointsRepository.delete(id);
    }
}
