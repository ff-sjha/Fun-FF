package com.firstfuel.fafi.service.impl;

import com.firstfuel.fafi.service.MatchFranchiseService;
import com.firstfuel.fafi.domain.MatchFranchise;
import com.firstfuel.fafi.repository.MatchFranchiseRepository;
import com.firstfuel.fafi.service.dto.MatchFranchiseDTO;
import com.firstfuel.fafi.service.mapper.MatchFranchiseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MatchFranchise.
 */
@Service
@Transactional
public class MatchFranchiseServiceImpl implements MatchFranchiseService{

    private final Logger log = LoggerFactory.getLogger(MatchFranchiseServiceImpl.class);

    private final MatchFranchiseRepository matchFranchiseRepository;

    private final MatchFranchiseMapper matchFranchiseMapper;

    public MatchFranchiseServiceImpl(MatchFranchiseRepository matchFranchiseRepository, MatchFranchiseMapper matchFranchiseMapper) {
        this.matchFranchiseRepository = matchFranchiseRepository;
        this.matchFranchiseMapper = matchFranchiseMapper;
    }

    /**
     * Save a matchFranchise.
     *
     * @param matchFranchiseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MatchFranchiseDTO save(MatchFranchiseDTO matchFranchiseDTO) {
        log.debug("Request to save MatchFranchise : {}", matchFranchiseDTO);
        MatchFranchise matchFranchise = matchFranchiseMapper.toEntity(matchFranchiseDTO);
        matchFranchise = matchFranchiseRepository.save(matchFranchise);
        return matchFranchiseMapper.toDto(matchFranchise);
    }

    /**
     * Get all the matchFranchises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MatchFranchiseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MatchFranchises");
        return matchFranchiseRepository.findAll(pageable)
            .map(matchFranchiseMapper::toDto);
    }

    /**
     * Get one matchFranchise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MatchFranchiseDTO findOne(Long id) {
        log.debug("Request to get MatchFranchise : {}", id);
        MatchFranchise matchFranchise = matchFranchiseRepository.findOne(id);
        return matchFranchiseMapper.toDto(matchFranchise);
    }

    /**
     * Delete the matchFranchise by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MatchFranchise : {}", id);
        matchFranchiseRepository.delete(id);
    }
}
