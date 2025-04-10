package com.lloyds.goalsobjectives.controller;

import com.lloyds.goalsobjectives.domain.Objective;
import com.lloyds.goalsobjectives.service.ObjectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lloyds.lloydsiam.Permission;
import java.util.List;

@RestController
@RequestMapping("/api/objectives")
public class ObjectivesController {

    private final ObjectiveService objectiveService;

    @Autowired
    public ObjectivesController(ObjectiveService objectiveService) {
        this.objectiveService = objectiveService;
    }

    @Permission(roles = {"Admin"})
    @PostMapping
    public ResponseEntity<Objective> createObjective(@RequestBody Objective objective) {
        return null;
    }

    @Permission(roles = {"Admin"})
    @PutMapping("/{id}")
    public ResponseEntity<Objective> updateObjective(@PathVariable Long id, @RequestBody Objective objective) {
        return null;
    }

    @Permission(roles = {"User", "Admin"})
    @GetMapping("/{id}")
    public ResponseEntity<Objective> getObjective(@PathVariable Long id) {
       return null;
    }

    @Permission(roles = {"User", "Admin"})
    @GetMapping
    public ResponseEntity<List<Objective>> listObjectives(@RequestParam(required = false) Long goalId, @RequestParam(required = false) Long userId) {
        List<Objective> objectives = objectiveService.listObjectives(goalId, userId);
        return ResponseEntity.ok(objectives);
    }
}