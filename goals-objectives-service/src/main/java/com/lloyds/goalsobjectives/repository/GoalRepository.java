package com.lloyds.goalsobjectives.repository;

import com.lloyds.goalsobjectives.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
}