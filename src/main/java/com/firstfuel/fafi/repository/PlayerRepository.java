package com.firstfuel.fafi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.firstfuel.fafi.domain.Player;
import com.firstfuel.fafi.service.dto.PlayerPointsDTO;

/**
 * Spring Data JPA repository for the Player entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>, JpaSpecificationExecutor<Player> {

    @Query("select new com.firstfuel.fafi.service.dto.PlayerPointsDTO(mp.seasonsFranchisePlayer.player.id, mp.seasonsFranchisePlayer.player.firstName, mp.seasonsFranchisePlayer.player.lastName, mp.seasonsFranchisePlayer.player.active , sum(mp.playerPointsEarned), count(mp), mp.seasonsFranchisePlayer.seasonsFranchise.franchise.name) from MatchPlayers mp WHERE mp.match.completed = true group by mp.seasonsFranchisePlayer.player order by sum(mp.playerPointsEarned) desc")
    List<PlayerPointsDTO> getPlayerPoints();

    @Query("select new com.firstfuel.fafi.service.dto.PlayerPointsDTO(mp.seasonsFranchisePlayer.player.id, mp.seasonsFranchisePlayer.player.firstName, mp.seasonsFranchisePlayer.player.lastName, mp.seasonsFranchisePlayer.player.active , sum(mp.playerPointsEarned), count(mp), mp.seasonsFranchisePlayer.seasonsFranchise.franchise.name) from MatchPlayers mp WHERE mp.match.completed = true and mp.match.tournament.id = :tournamentId group by mp.seasonsFranchisePlayer.player order by sum(mp.playerPointsEarned) desc")
    List<PlayerPointsDTO> getPlayerPoints(@Param("tournamentId") Long tournamentId);

    @Query("select new com.firstfuel.fafi.service.dto.PlayerPointsDTO(mp.seasonsFranchisePlayer.player.id, mp.seasonsFranchisePlayer.player.firstName, mp.seasonsFranchisePlayer.player.lastName, mp.seasonsFranchisePlayer.player.active , sum(mp.playerPointsEarned), count(mp), mp.seasonsFranchisePlayer.seasonsFranchise.franchise.name) from MatchPlayers mp WHERE mp.match.completed = true and mp.match.id =:matchId and mp.match.tournament.id = :tournamentId group by mp.seasonsFranchisePlayer.player order by sum(mp.playerPointsEarned) desc")
    List<PlayerPointsDTO> getPlayerPoints(@Param("tournamentId") Long tournamentId, @Param("matchId") Long matchId);
}
