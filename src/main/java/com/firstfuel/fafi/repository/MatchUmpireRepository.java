package com.firstfuel.fafi.repository;

import com.firstfuel.fafi.domain.MatchUmpire;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MatchUmpire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchUmpireRepository extends JpaRepository<MatchUmpire, Long>, JpaSpecificationExecutor<MatchUmpire> {

}
