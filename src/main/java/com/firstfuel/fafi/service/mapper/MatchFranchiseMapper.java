package com.firstfuel.fafi.service.mapper;

import com.firstfuel.fafi.domain.*;
import com.firstfuel.fafi.service.dto.MatchFranchiseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MatchFranchise and its DTO MatchFranchiseDTO.
 */
@Mapper(componentModel = "spring", uses = {MatchMapper.class, SeasonsFranchiseMapper.class})
public interface MatchFranchiseMapper extends EntityMapper<MatchFranchiseDTO, MatchFranchise> {

    @Mapping(source = "match.id", target = "matchId")
    @Mapping(source = "match.matchNumber", target = "matchMatchNumber")
    @Mapping(source = "seasonsFranchise.id", target = "seasonsFranchiseId")
    @Mapping(source = "seasonsFranchise.franchise.name", target = "seasonsFranchiseFranchise")
    MatchFranchiseDTO toDto(MatchFranchise matchFranchise); 

    @Mapping(source = "matchId", target = "match")
    @Mapping(source = "seasonsFranchiseId", target = "seasonsFranchise")
    MatchFranchise toEntity(MatchFranchiseDTO matchFranchiseDTO);

    default MatchFranchise fromId(Long id) {
        if (id == null) {
            return null;
        }
        MatchFranchise matchFranchise = new MatchFranchise();
        matchFranchise.setId(id);
        return matchFranchise;
    }
}
