package com.firstfuel.fafi.repository;

import com.firstfuel.fafi.domain.TieTeam;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TieTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TieTeamRepository extends JpaRepository<TieTeam, Long>, JpaSpecificationExecutor<TieTeam> {

}
