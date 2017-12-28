package com.firstfuel.fafi.repository;

import com.firstfuel.fafi.domain.SeasonsFranchise;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SeasonsFranchise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeasonsFranchiseRepository extends JpaRepository<SeasonsFranchise, Long>, JpaSpecificationExecutor<SeasonsFranchise> {

}
