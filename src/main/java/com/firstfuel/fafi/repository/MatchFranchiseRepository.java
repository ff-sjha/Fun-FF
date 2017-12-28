package com.firstfuel.fafi.repository;

import com.firstfuel.fafi.domain.MatchFranchise;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MatchFranchise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchFranchiseRepository extends JpaRepository<MatchFranchise, Long>, JpaSpecificationExecutor<MatchFranchise> {

}
