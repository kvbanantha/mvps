package com.lloyds.lloydsiam.service;

import com.lloyds.lloydsiam.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.lloyds.lloydsiam.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
   
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
    public List<User> listUsers() { return userRepository.findAll();}

    public void deleteUser(Long id) { userRepository.deleteById(id); }
}