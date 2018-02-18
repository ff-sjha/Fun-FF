package com.firstfuel.fafi.service.mapper;

import com.firstfuel.fafi.domain.*;
import com.firstfuel.fafi.service.dto.TournamentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tournament and its DTO TournamentDTO.
 */
@Mapper(componentModel = "spring", uses = {SeasonMapper.class, SeasonsFranchiseMapper.class, SeasonsFranchisePlayerMapper.class})
public interface TournamentMapper extends EntityMapper<TournamentDTO, Tournament> {

    @Mapping(source = "season.id", target = "seasonId")
    @Mapping(source = "season.name", target = "seasonName")
    @Mapping(source = "winningFranchise.id", target = "winningFranchiseId")
    @Mapping(source = "winningFranchise.franchise.name", target = "winningFranchiseFranchiseName")
    @Mapping(source = "playerOfTournament.id", target = "playerOfTournamentId")
    @Mapping(source = "playerOfTournament.player.firstName", target = "playerOfTournamentPlayerFirstName")
    @Mapping(source = "playerOfTournament.player.lastName", target = "playerOfTournamentPlayerLastName")
    TournamentDTO toDto(Tournament tournament); 

    @Mapping(source = "seasonId", target = "season")
    @Mapping(target = "matches", ignore = true)
    @Mapping(source = "winningFranchiseId", target = "winningFranchise")
    @Mapping(source = "playerOfTournamentId", target = "playerOfTournament")
    Tournament toEntity(TournamentDTO tournamentDTO);

    default Tournament fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tournament tournament = new Tournament();
        tournament.setId(id);
        return tournament;
    }
}
