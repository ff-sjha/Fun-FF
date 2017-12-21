package com.firstfuel.fafi.repository;

import com.firstfuel.fafi.domain.TieMatch;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TieMatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TieMatchRepository extends JpaRepository<TieMatch, Long>, JpaSpecificationExecutor<TieMatch> {

}
