package com.firstfuel.fafi.service.mapper;

import com.firstfuel.fafi.domain.*;
import com.firstfuel.fafi.service.dto.SeasonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Season and its DTO SeasonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SeasonMapper extends EntityMapper<SeasonDTO, Season> {

    

    @Mapping(target = "tournaments", ignore = true)
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
