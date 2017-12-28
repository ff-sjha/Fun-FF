package com.firstfuel.fafi.repository;

import com.firstfuel.fafi.domain.MatchPlayers;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MatchPlayers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchPlayersRepository extends JpaRepository<MatchPlayers, Long>, JpaSpecificationExecutor<MatchPlayers> {

}
