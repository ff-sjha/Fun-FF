package com.firstfuel.fafi.service.mapper;

import com.firstfuel.fafi.domain.*;
import com.firstfuel.fafi.service.dto.MatchDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Match and its DTO MatchDTO.
 */
@Mapper(componentModel = "spring", uses = {TournamentMapper.class})
public interface MatchMapper extends EntityMapper<MatchDTO, Match> {

    @Mapping(source = "tournament.id", target = "tournamentId")
    MatchDTO toDto(Match match); 

    @Mapping(source = "tournamentId", target = "tournament")
    Match toEntity(MatchDTO matchDTO);

    default Match fromId(Long id) {
        if (id == null) {
            return null;
        }
        Match match = new Match();
        match.setId(id);
        return match;
    }
}
