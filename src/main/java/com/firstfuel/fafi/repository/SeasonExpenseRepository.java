package com.firstfuel.fafi.repository;

import com.firstfuel.fafi.domain.SeasonExpense;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SeasonExpense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeasonExpenseRepository extends JpaRepository<SeasonExpense, Long>, JpaSpecificationExecutor<SeasonExpense> {

}
