package com.firstfuel.fafi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.firstfuel.fafi.domain.Match;
import com.firstfuel.fafi.domain.MatchPlayers;


/**
 * Spring Data JPA repository for the MatchPlayers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchPlayersRepository extends JpaRepository<MatchPlayers, Long>, JpaSpecificationExecutor<MatchPlayers> {

    List<MatchPlayers> findByMatch(Match m);

}
