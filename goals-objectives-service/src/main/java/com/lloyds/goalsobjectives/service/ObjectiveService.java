package com.lloyds.goalsobjectives.service;

import com.lloyds.goalsobjectives.domain.Objective;
import com.lloyds.goalsobjectives.repository.ObjectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectiveService {

    private final ObjectiveRepository objectiveRepository;

    @Autowired
    public ObjectiveService(ObjectiveRepository objectiveRepository) {
        this.objectiveRepository = objectiveRepository;
    }

    public List<Objective> listObjectives() {
        return objectiveRepository.findAll();
    }

    public Objective createObjective(Objective objective) {
        return objectiveRepository.save(objective);
    }

    public Objective updateObjective(Objective objective) {
        return objectiveRepository.save(objective);
    }

    public Objective getObjective(long l) {
        return objectiveRepository.findById(l).orElse(null);
    }
}