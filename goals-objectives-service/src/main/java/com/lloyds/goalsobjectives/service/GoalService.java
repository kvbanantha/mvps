package com.lloyds.goalsobjectives.service;

import com.lloyds.goalsobjectives.domain.Goal;
import com.lloyds.goalsobjectives.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public List<Goal> listAllGoals() {
        return goalRepository.findAll();
    }

    
    public Goal getGoalById(Long id) {
        Optional<Goal> goalOptional = goalRepository.findById(id);
        return goalOptional.orElse(null);
    }
    
    public List<Goal> listGoalsByCategory(List<String> categories) {
        return goalRepository.findAll().stream()
                .filter(goal -> categories.contains(goal.getCategory()))
                .toList();
    }

    public Goal createGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public Goal updateGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public void deleteGoal(long id) {
        goalRepository.deleteById(id);
    }
}