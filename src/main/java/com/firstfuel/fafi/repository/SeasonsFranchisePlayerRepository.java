package com.firstfuel.fafi.repository;

import com.firstfuel.fafi.domain.SeasonsFranchisePlayer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SeasonsFranchisePlayer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeasonsFranchisePlayerRepository extends JpaRepository<SeasonsFranchisePlayer, Long>, JpaSpecificationExecutor<SeasonsFranchisePlayer> {

}
