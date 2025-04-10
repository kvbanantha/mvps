package com.lloyds.lloydsiam.service;

import com.lloyds.lloydsiam.domain.User;
import com.lloyds.lloydsiam.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
   
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) throws DataAccessResourceFailureException{
        try {
            return userRepository.save(user);
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error accessing database while creating user", e);
        }
    }

    public User updateUser(User user) throws DataAccessResourceFailureException {
        try {
            Optional<User> userOptional = userRepository.findById(user.getId());
            if (userOptional.isPresent()) {
                User existingUser = userOptional.get();
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(user.getPassword());
                return userRepository.save(existingUser);
            }
            throw new EntityNotFoundException("User not found with id: " + user.getId());
        } catch (DataAccessException e) {
             throw new DataAccessResourceFailureException("Error accessing database while updating user", e);
        }
       
    }

    public User getUserById(Long id) throws DataAccessResourceFailureException {
        try {
            Optional<User> user = userRepository.findById(id);
            return user.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error accessing database while getting user by ID", e);
        }
    }

    public List<User> listUsers() throws DataAccessResourceFailureException {
        try{
            return userRepository.findAll();
        }catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error accessing database while listing users", e);
        }
    }

    public void deleteUser(Long id) throws DataAccessResourceFailureException {
        try{
           userRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error accessing database while deleting user", e);
        }
    }
}