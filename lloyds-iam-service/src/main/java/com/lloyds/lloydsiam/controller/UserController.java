package com.lloyds.lloydsiam.controller;

import com.lloyds.lloydsiam.annotation.Authorized;
import com.lloyds.lloydsiam.domain.Role;
import com.lloyds.lloydsiam.domain.User;
import com.lloyds.lloydsiam.service.RoleService;
import com.lloyds.lloydsiam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired

    private RoleService roleService;

    @PostMapping("/users")
    @Authorized(role = "SuperAdmin")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    @Authorized(role = "SuperAdmin")
    public ResponseEntity<Long> updateUser(@RequestBody User user) throws EntityNotFoundException {
        User updatedUser = userService.updateUser(user);       
        Long updatedUserId = updatedUser.getId();
        return new ResponseEntity<>(updatedUserId, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @Authorized(role = "SuperAdmin")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    @Authorized(role = "SuperAdmin")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.listUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @Authorized(role = "SuperAdmin")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/roles")
    @Authorized(role = "SuperAdmin")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.createRole(role);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @PutMapping("/roles/{id}")
    @Authorized(role = "SuperAdmin")
    public ResponseEntity<Long> updateRole(@RequestBody Role role) {
        Long updatedRoleId = roleService.updateRole(role).getId();
        return new ResponseEntity<>(updatedRoleId, HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    @Authorized(role = "SuperAdmin")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        Optional<Role> roleOptional = roleService.getRole(id);
        Role role = roleOptional.orElseThrow(() -> 
                new NoSuchElementException("Role not found with id: " + id));
        
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
    

    @GetMapping("/roles")
    @Authorized(role = "SuperAdmin")
    public ResponseEntity<List<Role>> listRoles() {
        List<Role> roles = roleService.listRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @DeleteMapping("/roles/{id}")
    @Authorized(role = "SuperAdmin")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/addAdmin")
    @Authorized(role = "SuperAdmin")
    public ResponseEntity<String> addAdminToEndpoint(@RequestParam(required = false) String endpoint, @RequestParam(required = false) String role) {
       if (endpoint == null || role == null) {
            return new ResponseEntity<>("Both 'endpoint' and 'role' parameters are required.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Admin added successfully to the endpoint", HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}