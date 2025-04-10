package com.lloyds.goalsobjectives.service;

import com.lloyds.goalsobjectives.domain.Goal;
import java.util.List;
import java.util.Optional;

public interface GoalService {
    /**
     * Create a new goal
     */
    Goal createGoal(Goal goal);

    /**
     * Update an existing goal.
     */
    Goal updateGoal(Goal goal);

    Optional<Goal> getGoal(Long id);

    List<Goal> listGoalsByCategory(List<String> categories);

    List<Goal> listAllGoals();
}