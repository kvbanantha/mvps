package com.lloyds.lloydsiam.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.lloyds.lloydsiam.LloydsIamServiceApplication;
import com.lloyds.lloydsiam.service.RoleService;
import com.lloyds.lloydsiam.service.UserService;
import com.lloyds.lloydsiam.domain.Role;
import com.lloyds.lloydsiam.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = LloydsIamServiceApplication.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ConfigurableWebApplicationContext context;

    @MockBean
    private UserService userServiceMock;

    @MockBean
    private RoleService roleServiceMock;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        when(userServiceMock.updateUser(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void createUser() throws Exception {

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated());

        doThrow(new DataAccessResourceFailureException("DB error")).when(userServiceMock).createUser(any());

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isInternalServerError());
    }

    @Nested
    class UpdateUserTests {

        @Test
        void updateUser_NotFound() throws Exception {
            doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(userServiceMock)
                    .updateUser(any(User.class));

            mockMvc.perform(put("/api/users/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ }"))
                    .andExpect(status().isNotFound());
        }

        @Test
        void updateUser_DBError() throws Exception {
            doThrow(new DataAccessResourceFailureException("DB error")).when(userServiceMock)
                    .updateUser(any(User.class));
            mockMvc.perform(put("/api/users/1")
                    .contentType(MediaType.APPLICATION_JSON).content("{}"))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void getUser() throws Exception {
            doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(userServiceMock).getUserById(anyLong());
            mockMvc.perform(get("/api/users/1"))
                    .andExpect(status().isNotFound());

            doThrow(new DataAccessResourceFailureException("DB error")).when(userServiceMock).getUserById(anyLong());
            mockMvc.perform(get("/api/users/1"))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void listUsers() throws Exception {
            when(userServiceMock.listUsers()).thenReturn(java.util.Collections.emptyList());
            mockMvc.perform(get("/api/users"))
                    .andExpect(status().isOk());

            doThrow(new DataAccessResourceFailureException("DB error")).when(userServiceMock).listUsers();
            mockMvc.perform(get("/api/users"))
                    .andExpect(status().isInternalServerError());

        }

        @Test
        void deleteUser() throws Exception {

            mockMvc.perform(delete("/api/users/10"))
                    .andExpect(status().isNoContent());

            doThrow(new DataAccessResourceFailureException("DB error")).when(userServiceMock).deleteUser(anyLong());
            mockMvc.perform(delete("/api/users/1"))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void createRole() throws Exception {

            mockMvc.perform(post("/api/roles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
                    .andExpect(status().isCreated());
            doThrow(new DataAccessResourceFailureException("DB error")).when(roleServiceMock)
                    .createRole(any(Role.class));

            mockMvc.perform(post("/api/roles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void updateRole() throws Exception {
            doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(roleServiceMock)
                    .updateRole(any());
            mockMvc.perform(put("/api/users/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ }"))
                    .andExpect(status().isNotFound());          

            doThrow(new DataAccessResourceFailureException("DB error")).when(roleServiceMock)
                    .updateRole(any(Role.class));

            mockMvc.perform(put("/api/roles/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void getRole() throws Exception {
            when(roleServiceMock.getRole(anyLong())).thenReturn(Optional.empty());
            mockMvc.perform(get("/api/roles/1"))
                    .andExpect(status().isNotFound());

            Role role = new Role();
            when(roleServiceMock.getRole(anyLong())).thenReturn(Optional.of(role));
            doThrow(new DataAccessResourceFailureException("DB error")).when(roleServiceMock).getRole(anyLong());
            mockMvc.perform(get("/api/roles/1"))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void listRoles() throws Exception {
            mockMvc.perform(get("/api/roles"))
                    .andExpect(status().isOk());
        }

        @Test
        void listRoles_db_error() throws Exception {
            doThrow(new DataAccessResourceFailureException("DB error")).when(roleServiceMock).listRoles();
            mockMvc.perform(get("/api/roles"))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void deleteRole() throws Exception {
            mockMvc.perform(delete("/api/roles/10"))
                    .andExpect(status().isNoContent());
            doThrow(new DataAccessResourceFailureException("DB error")).when(roleServiceMock).deleteRole(anyLong());
            mockMvc.perform(delete("/api/roles/1"))
                    .andExpect(status().isInternalServerError());

        }

        @Test
        void deleteRole_not_found() throws Exception {
            doThrow(new EmptyResultDataAccessException(1)).when(roleServiceMock).deleteRole(anyLong());
            mockMvc.perform(delete("/api/roles/10"))
                    .andExpect(status().isNotFound());

        }

        @Test
        void addAdminToEndpoint() throws Exception {
            mockMvc.perform(post("/api/addAdmin")
                    .param("endpoint", "someEndpoint")
                    .param("role", "someRole"))
                    .andExpect(status().isBadRequest());
        }
    }
}