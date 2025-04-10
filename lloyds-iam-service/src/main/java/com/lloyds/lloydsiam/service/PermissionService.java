package com.lloyds.lloydsiam.service;

import com.lloyds.lloydsiam.domain.Permission;
import com.lloyds.lloydsiam.domain.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public Permission updatePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public Optional<Permission> getPermission(Long id) {
        return permissionRepository.findById(id);
    }

    public List<Permission> listPermissions() {
        return permissionRepository.findAll();
    }

    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }
}