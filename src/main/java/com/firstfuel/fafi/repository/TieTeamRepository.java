package com.firstfuel.fafi.repository;

import com.firstfuel.fafi.domain.TieTeam;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the TieTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TieTeamRepository extends JpaRepository<TieTeam, Long>, JpaSpecificationExecutor<TieTeam> {
    @Query("select distinct tie_team from TieTeam tie_team left join fetch tie_team.tiePlayers")
    List<TieTeam> findAllWithEagerRelationships();

    @Query("select tie_team from TieTeam tie_team left join fetch tie_team.tiePlayers where tie_team.id =:id")
    TieTeam findOneWithEagerRelationships(@Param("id") Long id);

}
