package com.firstfuel.fafi.service.mapper;

import com.firstfuel.fafi.domain.*;
import com.firstfuel.fafi.service.dto.FranchiseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Franchise and its DTO FranchiseDTO.
 */
@Mapper(componentModel = "spring", uses = {SeasonMapper.class})
public interface FranchiseMapper extends EntityMapper<FranchiseDTO, Franchise> {

    @Mapping(source = "season.id", target = "seasonId")
    @Mapping(source = "season.name", target = "seasonName")
    FranchiseDTO toDto(Franchise franchise);

    @Mapping(source = "seasonId", target = "season")
    @Mapping(target = "players", ignore = true)
    Franchise toEntity(FranchiseDTO franchiseDTO);

    default Franchise fromId(Long id) {
        if (id == null) {
            return null;
        }
        Franchise franchise = new Franchise();
        franchise.setId(id);
        return franchise;
    }
}
