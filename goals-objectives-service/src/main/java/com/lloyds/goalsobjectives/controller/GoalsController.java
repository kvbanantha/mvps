package com.lloyds.goalsobjectives.controller;

import com.lloyds.goalsobjectives.domain.Goal;
import com.lloyds.goalsobjectives.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Permission
@RestController
@RequestMapping("/api/goals")
public class GoalsController {

    @Autowired
    private GoalService goalService;

    // Create a new Goal (Admin only)
    @PostMapping
    @Permission(roles = "Admin")
    public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
        Goal createdGoal = goalService.createGoal(goal);
        return new ResponseEntity<>(createdGoal, HttpStatus.CREATED);
    }

    // Update an existing Goal (Admin only)
    @PutMapping("/{id}")
    @Permission(roles = "Admin")
    public ResponseEntity<Goal> updateGoal(@PathVariable Long id, @RequestBody Goal goal) {
        if (!id.equals(goal.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Goal updatedGoal = goalService.updateGoal(goal);
        if(updatedGoal == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedGoal, HttpStatus.OK);
    }

    // Get a Goal by ID (User, Admin)
    @GetMapping("/{id}")
    @Permission(roles = {"User","Admin"})
    public ResponseEntity<Goal> getGoalById(@PathVariable Long id) {
        Goal goal = goalService.getGoalById(id);
        return goal != null ? new ResponseEntity<>(goal, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // List Goals by Categories (User, Admin)
    @GetMapping
    @Permission(roles = {"User","Admin"})
    public ResponseEntity<List<Goal>> listGoalsByCategory(@RequestParam(name = "category", required = false) List<String> categories) {
        return new ResponseEntity<>(goalService.listGoalsByCategory(categories), HttpStatus.OK);
    }
}
