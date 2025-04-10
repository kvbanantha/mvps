package com.lloyds.goalsobjectives.controller;

import com.lloyds.goalsobjectives.service.GoalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

class GoalsControllerTest {

    @Mock
    private GoalService goalService;

    @InjectMocks
    private GoalsController goalsController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(goalsController).build();
    }

    @Test
    void testCreateGoal() throws Exception {
        mockMvc.perform(post("/goals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated());
        verify(goalService, times(1)).createGoal(any());
    }

    @Test
    void testUpdateGoal() throws Exception {
        mockMvc.perform(put("/goals/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
        verify(goalService, times(1)).updateGoal(any());
    }

    @Test
    void testListGoals() throws Exception {
        mockMvc.perform(get("/goals"))
                .andExpect(status().isOk());
        verify(goalService, times(1)).listAllGoals();
    }

    @Test
    void testGetGoal() throws Exception {
        mockMvc.perform(get("/goals/1"))
                .andExpect(status().isOk());
        verify(goalService, times(1)).getGoalById(1L);
    }
}