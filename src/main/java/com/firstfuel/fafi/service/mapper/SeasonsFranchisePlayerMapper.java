package com.firstfuel.fafi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.firstfuel.fafi.domain.SeasonsFranchisePlayer;
import com.firstfuel.fafi.service.dto.SeasonsFranchisePlayerDTO;

/**
 * Mapper for the entity SeasonsFranchisePlayer and its DTO
 * SeasonsFranchisePlayerDTO.
 */
@Mapper(componentModel = "spring", uses = { SeasonsFranchiseMapper.class, PlayerMapper.class })
public interface SeasonsFranchisePlayerMapper extends EntityMapper<SeasonsFranchisePlayerDTO, SeasonsFranchisePlayer> {

	@Mapping(source = "seasonsFranchise.id", target = "seasonsFranchiseId")
	@Mapping(source = "seasonsFranchise.season.name", target = "seasonsFranchiseSeasonName")
	@Mapping(source = "seasonsFranchise.franchise.name", target = "seasonsFranchiseFranchiseName")
	@Mapping(source = "player.id", target = "playerId")
	@Mapping(source = "player.firstName", target = "playerFirstName")
	@Mapping(source = "player.lastName", target = "playerLastName")
	SeasonsFranchisePlayerDTO toDto(SeasonsFranchisePlayer seasonsFranchisePlayer);

	@Mapping(source = "seasonsFranchiseId", target = "seasonsFranchise")
	@Mapping(source = "playerId", target = "player")
	SeasonsFranchisePlayer toEntity(SeasonsFranchisePlayerDTO seasonsFranchisePlayerDTO);

	default SeasonsFranchisePlayer fromId(Long id) {
		if (id == null) {
			return null;
		}
		SeasonsFranchisePlayer seasonsFranchisePlayer = new SeasonsFranchisePlayer();
		seasonsFranchisePlayer.setId(id);
		return seasonsFranchisePlayer;
	}
}
