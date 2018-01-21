package com.firstfuel.fafi.service.mapper;

import com.firstfuel.fafi.domain.*;
import com.firstfuel.fafi.service.dto.SeasonsFranchiseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SeasonsFranchise and its DTO SeasonsFranchiseDTO.
 */
@Mapper(componentModel = "spring", uses = { SeasonMapper.class, FranchiseMapper.class, PlayerMapper.class })
public interface SeasonsFranchiseMapper extends EntityMapper<SeasonsFranchiseDTO, SeasonsFranchise> {

	@Mapping(source = "season.id", target = "seasonId")
	@Mapping(source = "season.name", target = "seasonName")
	@Mapping(source = "franchise.id", target = "franchiseId")
	@Mapping(source = "franchise.name", target = "franchiseName")
    @Mapping(source = "franchise.logoPath", target = "logoPath")
    @Mapping(source = "franchise.active", target = "active")
	@Mapping(source = "owner.id", target = "ownerId")
	@Mapping(source = "owner.firstName", target = "ownerFirstName")
	@Mapping(source = "owner.lastName", target = "ownerLastName")
	@Mapping(source = "iconPlayer.id", target = "iconPlayerId")
	@Mapping(source = "iconPlayer.firstName", target = "iconPlayerFirstName")
	@Mapping(source = "iconPlayer.lastName", target = "iconPlayerLastName")
	SeasonsFranchiseDTO toDto(SeasonsFranchise seasonsFranchise);

	@Mapping(source = "seasonId", target = "season")
	@Mapping(source = "franchiseId", target = "franchise")
	@Mapping(source = "ownerId", target = "owner")
	@Mapping(source = "iconPlayerId", target = "iconPlayer")
	SeasonsFranchise toEntity(SeasonsFranchiseDTO seasonsFranchiseDTO);

	default SeasonsFranchise fromId(Long id) {
		if (id == null) {
			return null;
		}
		SeasonsFranchise seasonsFranchise = new SeasonsFranchise();
		seasonsFranchise.setId(id);
		return seasonsFranchise;
	}
}
