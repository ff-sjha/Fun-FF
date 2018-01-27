package com.firstfuel.fafi.repository;

import com.firstfuel.fafi.domain.TieMatchSets;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TieMatchSets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TieMatchSetsRepository extends JpaRepository<TieMatchSets, Long>, JpaSpecificationExecutor<TieMatchSets> {

}
