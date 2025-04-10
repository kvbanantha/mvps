package com.lloyds.goalsobjectives.controller;

import com.lloyds.goalsobjectives.domain.Goal;
import com.lloyds.goalsobjectives.service.GoalService;
import com.lloyds.lloydsiam.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalsController {

    private final GoalService goalService;

    @Autowired
    public GoalsController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    @Permission(action = "READ_GOALS")
    public ResponseEntity<List<Goal>> listGoals() {
        return ResponseEntity.ok(goalService.listAllGoals());
    }

    @GetMapping("/{id}")
    @Permission(action = "READ_GOAL")
    public ResponseEntity<Goal> getGoal(@PathVariable Long id) {
        return ResponseEntity.ok(goalService.getGoalById(id));
    }

    @GetMapping("/byCategory")
    @Permission(action = "READ_GOALS_BY_CATEGORY")
    public ResponseEntity<List<Goal>> listGoalsByCategory(@RequestParam List<String> categories) {
        return ResponseEntity.ok(goalService.listGoalsByCategory(categories));
    }
}
