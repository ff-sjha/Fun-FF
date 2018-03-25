package com.firstfuel.fafi.repository;

import com.firstfuel.fafi.domain.SportInfo;
import com.firstfuel.fafi.domain.enumeration.Games;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SportInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SportInfoRepository extends JpaRepository<SportInfo, Long> {
    
    SportInfo findByGame(Games game);
}
