package com.firstfuel.fafi.service.impl;

import com.firstfuel.fafi.service.MatchPlayersService;
import com.firstfuel.fafi.domain.MatchPlayers;
import com.firstfuel.fafi.repository.MatchPlayersRepository;
import com.firstfuel.fafi.service.dto.MatchPlayersDTO;
import com.firstfuel.fafi.service.mapper.MatchPlayersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MatchPlayers.
 */
@Service
@Transactional
public class MatchPlayersServiceImpl implements MatchPlayersService{

    private final Logger log = LoggerFactory.getLogger(MatchPlayersServiceImpl.class);

    private final MatchPlayersRepository matchPlayersRepository;

    private final MatchPlayersMapper matchPlayersMapper;

    public MatchPlayersServiceImpl(MatchPlayersRepository matchPlayersRepository, MatchPlayersMapper matchPlayersMapper) {
        this.matchPlayersRepository = matchPlayersRepository;
        this.matchPlayersMapper = matchPlayersMapper;
    }

    /**
     * Save a matchPlayers.
     *
     * @param matchPlayersDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MatchPlayersDTO save(MatchPlayersDTO matchPlayersDTO) {
        log.debug("Request to save MatchPlayers : {}", matchPlayersDTO);
        MatchPlayers matchPlayers = matchPlayersMapper.toEntity(matchPlayersDTO);
        matchPlayers = matchPlayersRepository.save(matchPlayers);
        return matchPlayersMapper.toDto(matchPlayers);
    }

    /**
     * Get all the matchPlayers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MatchPlayersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MatchPlayers");
        return matchPlayersRepository.findAll(pageable)
            .map(matchPlayersMapper::toDto);
    }

    /**
     * Get one matchPlayers by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MatchPlayersDTO findOne(Long id) {
        log.debug("Request to get MatchPlayers : {}", id);
        MatchPlayers matchPlayers = matchPlayersRepository.findOne(id);
        return matchPlayersMapper.toDto(matchPlayers);
    }

    /**
     * Delete the matchPlayers by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MatchPlayers : {}", id);
        matchPlayersRepository.delete(id);
    }
}
