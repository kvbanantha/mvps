package com.lloyds.iam.service;

import org.springframework.dao.DataAccessResourceFailureException;
import jakarta.persistence.EntityNotFoundException;
import com.lloyds.iam.domain.User;
import com.lloyds.iam.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        User userToCreate = new User();
        userToCreate.setUsername("testUser");
        userToCreate.setPassword("password");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("testUser");
        savedUser.setPassword("password");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User createdUser = userService.createUser(userToCreate);

        assertNotNull(createdUser);
        assertEquals(1L, createdUser.getId());
        assertEquals("testUser", createdUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_dbDown() {
        User userToCreate = new User();
        userToCreate.setUsername("testUser");
        userToCreate.setPassword("password");

        when(userRepository.save(any(User.class))).thenThrow(new DataAccessResourceFailureException("DB down"));

        DataAccessResourceFailureException exception = assertThrows(
                DataAccessResourceFailureException.class,
                () -> userService.createUser(userToCreate)
        );

        assertEquals("DB down", exception.getMessage());
    }

    @Test
    void updateUser_userNotFound() {
        User userToUpdate = new User();
        userToUpdate.setId(1L);
        userToUpdate.setUsername("updatedUser");
        userToUpdate.setPassword("newPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> userService.updateUser(userToUpdate)
        );
        
        assertEquals("User not found with id: 1", exception.getMessage());        
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void updateUser_dbDown() {
        User userToUpdate = new User();
        userToUpdate.setId(1L);
        userToUpdate.setUsername("updatedUser");
        userToUpdate.setPassword("newPassword");

        User existingUser = new User();
        existingUser.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenThrow(new DataAccessResourceFailureException("DB down"));

        DataAccessResourceFailureException exception = assertThrows(
                DataAccessResourceFailureException.class,
                () -> userService.updateUser(userToUpdate)
        );

        assertEquals("DB down", exception.getMessage());
    }

    @Test
    void getUserById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("password");;

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUserById(1L);

        assertNotNull(retrievedUser);
        assertEquals(1L, retrievedUser.getId());
        assertEquals("testUser", retrievedUser.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }
    @Test
    void getUserById_dbDown() {
        when(userRepository.findById(1L)).thenThrow(new DataAccessResourceFailureException("DB down"));

        DataAccessResourceFailureException exception = assertThrows(
                DataAccessResourceFailureException.class,
                () -> userService.getUserById(1L)
        );

        assertEquals("DB down", exception.getMessage());
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));    }

    @Test
    void listUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("testUser1");
        user1.setPassword("password");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("testUser2");
        user2.setPassword("password");

        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> retrievedUsers = userService.listUsers();

        assertNotNull(retrievedUsers);
        assertEquals(2, retrievedUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void listUsers_dbDown() {
        when(userRepository.findAll()).thenThrow(new DataAccessResourceFailureException("DB down"));

        DataAccessResourceFailureException exception = assertThrows(
                DataAccessResourceFailureException.class,
                () -> userService.listUsers()
        );

        assertEquals("DB down", exception.getMessage());
    }


    @Test
    void deleteUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("password");


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_userNotFound() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> userService.deleteUser(1L)
        );

        assertEquals("User not found with id: 1", exception.getMessage());
    }
    @Test
    void deleteUser_dbDown() {
        when(userRepository.findById(1L)).thenThrow(new DataAccessResourceFailureException("DB down"));
        DataAccessResourceFailureException exception = assertThrows(
                DataAccessResourceFailureException.class,
                () -> userService.deleteUser(1L)
        );
        assertEquals("DB down", exception.getMessage());
    }
}