package com.lloyds.goalsobjectives.repository;

import com.lloyds.goalsobjectives.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByCategory(String category);
    Optional<Goal> findById(Long id);

}