package com.lloyds.goalsobjectives.service;

import com.lloyds.goalsobjectives.domain.Goal;
import com.lloyds.goalsobjectives.repository.GoalRepository;
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

class GoalServiceTest {

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private GoalService goalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGoal() {
        Goal goal = new Goal();
        goal.setName("Test Goal");

        when(goalRepository.save(any(Goal.class))).thenReturn(goal);

        Goal createdGoal = goalService.createGoal(goal);

        assertNotNull(createdGoal);
        assertEquals("Test Goal", createdGoal.getName());
        verify(goalRepository, times(1)).save(any(Goal.class));
    }

    @Test
    void updateGoal() {
        Goal existingGoal = new Goal();
        existingGoal.setId(1L);
        existingGoal.setName("Old Goal");

        Goal updatedGoal = new Goal();
        updatedGoal.setId(1L);
        updatedGoal.setName("Updated Goal");

        when(goalRepository.findById(1L)).thenReturn(Optional.of(existingGoal));
        when(goalRepository.save(any(Goal.class))).thenReturn(updatedGoal);

        Goal result = goalService.updateGoal(updatedGoal);

        assertNotNull(result);
        assertEquals("Updated Goal", result.getName());
        verify(goalRepository, times(1)).findById(1L);
        verify(goalRepository, times(1)).save(any(Goal.class));
    }

    @Test
    void listGoals() {
        List<Goal> goals = new ArrayList<>();
        goals.add(new Goal());
        goals.add(new Goal());

        when(goalRepository.findAll()).thenReturn(goals);

        List<Goal> result = goalService.listAllGoals();

        assertEquals(2, result.size());
        verify(goalRepository, times(1)).findAll();
    }

    @Test
    void getGoalById() {
        Goal goal = new Goal();
        goal.setId(1L);

        when(goalRepository.findById(1L)).thenReturn(Optional.of(goal));

        Goal result = goalService.getGoalById(1L);

        assertTrue(result != null);
        assertEquals(1L, result.getId());
        verify(goalRepository, times(1)).findById(1L);
    }

    @Test
    void deleteGoal() {
        Goal goal = new Goal();
        goal.setId(1L);
        when(goalRepository.findById(1L)).thenReturn(Optional.of(goal));
        doNothing().when(goalRepository).delete(any(Goal.class));
        goalService.deleteGoal(1L);
        verify(goalRepository, times(1)).findById(1L);
        verify(goalRepository, times(1)).delete(any(Goal.class));
    }
}