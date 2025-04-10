package com.lloyds.lloydsiam.service;

import com.lloyds.lloydsiam.domain.Role;
import com.lloyds.lloydsiam.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service 
public class RoleService {

    @Autowired 
    private RoleRepository roleRepository;
    
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }

    public Optional<Role> getRole(Long id) {
        return roleRepository.findById(id);
    }

    public List<Role> listRoles() { return roleRepository.findAll(); } 
    public void deleteRole(Long id) { roleRepository.deleteById(id); }
}