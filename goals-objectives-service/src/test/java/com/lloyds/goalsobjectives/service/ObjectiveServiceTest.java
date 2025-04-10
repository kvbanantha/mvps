package com.lloyds.goalsobjectives.service;

import com.lloyds.goalsobjectives.domain.Objective;
import com.lloyds.goalsobjectives.domain.repository.ObjectiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ObjectiveServiceTest {

    @Mock
    private ObjectiveRepository objectiveRepository;

    @InjectMocks
    private ObjectiveServiceImpl objectiveService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createObjective() {
        Objective objective = new Objective();
        when(objectiveRepository.save(any(Objective.class))).thenReturn(objective);
        Objective created = objectiveService.createObjective(objective);
        assertNotNull(created);
        verify(objectiveRepository, times(1)).save(any(Objective.class));
    }

    @Test
    void updateObjective() {
        Objective objective = new Objective();
        when(objectiveRepository.findById(any())).thenReturn(Optional.of(objective));
        when(objectiveRepository.save(any(Objective.class))).thenReturn(objective);
        Objective updated = objectiveService.updateObjective(1L,objective);
        assertNotNull(updated);
        verify(objectiveRepository, times(1)).save(any(Objective.class));
    }

    @Test
    void listObjectives() {
        List<Objective> objectives = new ArrayList<>();
        objectives.add(new Objective());
        when(objectiveRepository.findAll()).thenReturn(objectives);
        List<Objective> list = objectiveService.listObjectives();
        assertEquals(1,list.size());
        verify(objectiveRepository, times(1)).findAll();
    }

    @Test
    void getObjective() {
        Objective objective = new Objective();
        when(objectiveRepository.findById(any())).thenReturn(Optional.of(objective));
        Objective getObjective = objectiveService.getObjective(1L);
        assertNotNull(getObjective);
        verify(objectiveRepository, times(1)).findById(any());
    }
}