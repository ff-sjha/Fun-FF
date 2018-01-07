package com.firstfuel.fafi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.firstfuel.fafi.domain.TournamentFranchisePoints;
import com.firstfuel.fafi.service.dto.FranchisePointsDTO;

/**
 * Spring Data JPA repository for the TournamentFranchisePoints entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TournamentFranchisePointsRepository
        extends JpaRepository<TournamentFranchisePoints, Long>, JpaSpecificationExecutor<TournamentFranchisePoints> {
    @Query("select new com.firstfuel.fafi.service.dto.FranchisePointsDTO(tf.franchise.id, tf.franchise.name, sum(tf.points) ) from TournamentFranchisePoints tf group by tf.franchise order by sum(tf.points) desc")
    List<FranchisePointsDTO> getFranchisePoints();
}
