package com.firstfuel.fafi.repository;

import com.firstfuel.fafi.domain.TieMatchPlayers;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TieMatchPlayers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TieMatchPlayersRepository extends JpaRepository<TieMatchPlayers, Long>, JpaSpecificationExecutor<TieMatchPlayers> {

}
