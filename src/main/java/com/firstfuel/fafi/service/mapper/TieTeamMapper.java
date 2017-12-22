package com.firstfuel.fafi.service.mapper;

import com.firstfuel.fafi.domain.*;
import com.firstfuel.fafi.service.dto.TieTeamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TieTeam and its DTO TieTeamDTO.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class, FranchiseMapper.class})
public interface TieTeamMapper extends EntityMapper<TieTeamDTO, TieTeam> {

    @Mapping(source = "franchise.id", target = "franchiseId")
    @Mapping(source = "franchise.name", target = "franchiseName")
    TieTeamDTO toDto(TieTeam tieTeam);

    @Mapping(source = "franchiseId", target = "franchise")
    TieTeam toEntity(TieTeamDTO tieTeamDTO);

    default TieTeam fromId(Long id) {
        if (id == null) {
            return null;
        }
        TieTeam tieTeam = new TieTeam();
        tieTeam.setId(id);
        return tieTeam;
    }
}
