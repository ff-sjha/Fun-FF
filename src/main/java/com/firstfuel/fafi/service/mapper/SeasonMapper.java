package com.firstfuel.fafi.service.mapper;

import com.firstfuel.fafi.domain.*;
import com.firstfuel.fafi.service.dto.SeasonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Season and its DTO SeasonDTO.
 */
@Mapper(componentModel = "spring", uses = {FranchiseMapper.class})
public interface SeasonMapper extends EntityMapper<SeasonDTO, Season> {

    @Mapping(source = "winner.id", target = "winnerId")
    @Mapping(source = "winner.name", target = "winnerName")
    SeasonDTO toDto(Season season);

    @Mapping(target = "tournaments", ignore = true)
    @Mapping(source = "winnerId", target = "winner")
    Season toEntity(SeasonDTO seasonDTO);

    default Season fromId(Long id) {
        if (id == null) {
            return null;
        }
        Season season = new Season();
        season.setId(id);
        return season;
    }
}
