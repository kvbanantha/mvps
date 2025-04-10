package com.lloyds.goalsobjectives.controller;

import com.lloyds.goalsobjectives.domain.Objective;
import com.lloyds.goalsobjectives.security.Permission;
import com.lloyds.goalsobjectives.service.ObjectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objectives")
public class ObjectivesController {

    @Autowired
    private ObjectiveService objectiveService;

    @Permission(roles = {"Admin"})
    @PostMapping
    public ResponseEntity<Objective> createObjective(@RequestBody Objective objective) {
        Objective createdObjective = objectiveService.createObjective(objective);
        return new ResponseEntity<>(createdObjective, HttpStatus.CREATED);
    }

    @Permission(roles = {"Admin"})
    @PutMapping("/{id}")
    public ResponseEntity<Objective> updateObjective(@PathVariable Long id, @RequestBody Objective objective) {
        objective.setId(id);
        Objective updatedObjective = objectiveService.updateObjective(objective);
        if (updatedObjective == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedObjective, HttpStatus.OK);
    }

    @Permission(roles = {"User", "Admin"})
    @GetMapping("/{id}")
    public ResponseEntity<Objective> getObjective(@PathVariable Long id) {
        Objective objective = objectiveService.getObjective(id);
        return objective == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(objective, HttpStatus.OK);
    }

    @Permission(roles = {"User", "Admin"})
    @GetMapping
    public ResponseEntity<List<Objective>> listObjectives(@RequestParam(required = false) Long goalId, @RequestParam(required = false) Long categoryId) {
        List<Objective> objectives = objectiveService.listObjectives(goalId, categoryId);
        return new ResponseEntity<>(objectives, HttpStatus.OK);
    }
}