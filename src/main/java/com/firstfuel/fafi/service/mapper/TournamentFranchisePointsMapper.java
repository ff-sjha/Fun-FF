package com.firstfuel.fafi.service.mapper;

import com.firstfuel.fafi.domain.*;
import com.firstfuel.fafi.service.dto.TournamentFranchisePointsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TournamentFranchisePoints and its DTO TournamentFranchisePointsDTO.
 */
@Mapper(componentModel = "spring", uses = {TournamentMapper.class, FranchiseMapper.class})
public interface TournamentFranchisePointsMapper extends EntityMapper<TournamentFranchisePointsDTO, TournamentFranchisePoints> {

    @Mapping(source = "tournament.id", target = "tournamentId")
    @Mapping(source = "tournament.type", target = "tournamentType")
    @Mapping(source = "franchise.id", target = "franchiseId")
    @Mapping(source = "franchise.name", target = "franchiseName")
    TournamentFranchisePointsDTO toDto(TournamentFranchisePoints tournamentFranchisePoints); 

    @Mapping(source = "tournamentId", target = "tournament")
    @Mapping(source = "franchiseId", target = "franchise")
    TournamentFranchisePoints toEntity(TournamentFranchisePointsDTO tournamentFranchisePointsDTO);

    default TournamentFranchisePoints fromId(Long id) {
        if (id == null) {
            return null;
        }
        TournamentFranchisePoints tournamentFranchisePoints = new TournamentFranchisePoints();
        tournamentFranchisePoints.setId(id);
        return tournamentFranchisePoints;
    }
}
