package com.lloyds.goalsobjectives.domain.repository;

import com.lloyds.goalsobjectives.domain.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ObjectiveRepository extends JpaRepository<Objective, Long> {
    List<Objective> findByGoalId(Long goalId);
    Objective findObjectiveById(Long id);
    List<Objective> findByCategory(Long categoryId);

}