package com.firstfuel.fafi.service.mapper;

import com.firstfuel.fafi.domain.*;
import com.firstfuel.fafi.service.dto.MatchPlayersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MatchPlayers and its DTO MatchPlayersDTO.
 */
@Mapper(componentModel = "spring", uses = {MatchMapper.class, SeasonsFranchisePlayerMapper.class})
public interface MatchPlayersMapper extends EntityMapper<MatchPlayersDTO, MatchPlayers> {

    @Mapping(source = "match.id", target = "matchId")
    @Mapping(source = "match.matchNumber", target = "matchMatchNumber")
    @Mapping(source = "seasonsFranchisePlayer.id", target = "seasonsFranchisePlayerId")
    @Mapping(source = "seasonsFranchisePlayer.player.firstName", target = "seasonsFranchisePlayerFirstName")
    @Mapping(source = "seasonsFranchisePlayer.player.lastName", target = "seasonsFranchisePlayerLastName")
    MatchPlayersDTO toDto(MatchPlayers matchPlayers); 

    @Mapping(source = "matchId", target = "match")
    @Mapping(source = "seasonsFranchisePlayerId", target = "seasonsFranchisePlayer")
    MatchPlayers toEntity(MatchPlayersDTO matchPlayersDTO);

    default MatchPlayers fromId(Long id) {
        if (id == null) {
            return null;
        }
        MatchPlayers matchPlayers = new MatchPlayers();
        matchPlayers.setId(id);
        return matchPlayers;
    }
}
