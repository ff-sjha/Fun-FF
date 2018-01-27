package com.firstfuel.fafi.service.mapper;

import com.firstfuel.fafi.domain.*;
import com.firstfuel.fafi.service.dto.MatchDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Match and its DTO MatchDTO.
 */
@Mapper(componentModel = "spring", uses = { TournamentMapper.class, SeasonsFranchiseMapper.class })
public interface MatchMapper extends EntityMapper<MatchDTO, Match> {

    @Mapping(source = "tournament.id", target = "tournamentId")
    @Mapping(source = "winningFranchise.id", target = "winningFranchiseId")
    @Mapping(source = "winningFranchise.franchise.name", target = "winningFranchiseFranchiseName")
    @Mapping(source = "matchName", target = "matchName")
    @Mapping(source = "tournament.type", target = "tournamentType")
    @Mapping(source = "team1.id", target = "team1Id")
    @Mapping(source = "team1.franchise.name", target = "team1Franchise")
    @Mapping(source = "team2.id", target = "team2Id")
    @Mapping(source = "team2.franchise.name", target = "team2Franchise")
    @Mapping(source = "team3.id", target = "team3Id")
    @Mapping(source = "team3.franchise.name", target = "team3Franchise")
    @Mapping(source = "team4.id", target = "team4Id")
    @Mapping(source = "team4.franchise.name", target = "team4Franchise")
    MatchDTO toDto(Match match);

    @Mapping(source = "tournamentId", target = "tournament")
    @Mapping(source = "winningFranchiseId", target = "winningFranchise")
    @Mapping(source = "team1Id", target = "team1")
    @Mapping(source = "team2Id", target = "team2")
    @Mapping(source = "team3Id", target = "team3")
    @Mapping(source = "team4Id", target = "team4")
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
