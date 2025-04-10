package com.lloyds.lloydsiam.controller;

import com.lloyds.lloydsiam.Permission;
import com.lloyds.lloydsiam.domain.Role;
import com.lloyds.lloydsiam.domain.User;
import com.lloyds.lloydsiam.service.RoleService;
import com.lloyds.lloydsiam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Permission(role = "SuperAdmin")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    @Permission(role = "SuperAdmin")
    public ResponseEntity<Long> updateUser(@RequestBody User user) {        
        Long updatedUserId = userService.updateUser(user).getId();
        return new ResponseEntity<>(updatedUserId, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @Permission(role = "SuperAdmin")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    @Permission(role = "SuperAdmin")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.listUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @Permission(role = "SuperAdmin")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/roles")
    @Permission(role = "SuperAdmin")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.createRole(role);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @PutMapping("/roles/{id}")
    @Permission(role = "SuperAdmin")
    public ResponseEntity<Long> updateRole(@RequestBody Role role) {
        Long updatedRoleId = roleService.updateRole(role).getId();
        return new ResponseEntity<>(updatedRoleId, HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    @Permission(role = "SuperAdmin")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        Optional<Role> roleOptional = roleService.getRole(id);
        Role role = roleOptional.orElseThrow(() -> 
                new NoSuchElementException("Role not found with id: " + id));
        
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
    

    @GetMapping("/roles")
    @Permission(role = "SuperAdmin")
    public ResponseEntity<List<Role>> listRoles() {
        List<Role> roles = roleService.listRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @DeleteMapping("/roles/{id}")
    @Permission(role = "SuperAdmin")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/addAdmin")
    @Permission(role = "SuperAdmin")
    public ResponseEntity<String> addAdminToEndpoint(@RequestParam String endpoint, @RequestParam String role) {
       return new ResponseEntity<>("Admin added successfully to the endpoint", HttpStatus.OK);
    }
}