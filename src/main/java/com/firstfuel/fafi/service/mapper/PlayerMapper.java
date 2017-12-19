package com.firstfuel.fafi.service.mapper;

import com.firstfuel.fafi.domain.*;
import com.firstfuel.fafi.service.dto.PlayerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Player and its DTO PlayerDTO.
 */
@Mapper(componentModel = "spring", uses = {FranchiseMapper.class})
public interface PlayerMapper extends EntityMapper<PlayerDTO, Player> {

    @Mapping(source = "franchise.id", target = "franchiseId")
    @Mapping(source = "franchise.name", target = "franchiseName")
    PlayerDTO toDto(Player player);

    @Mapping(source = "franchiseId", target = "franchise")
    Player toEntity(PlayerDTO playerDTO);

    default Player fromId(Long id) {
        if (id == null) {
            return null;
        }
        Player player = new Player();
        player.setId(id);
        return player;
    }
}
