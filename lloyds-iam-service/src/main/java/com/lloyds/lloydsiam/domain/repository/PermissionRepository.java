package com.lloyds.lloydsiam.domain.repository;

import com.lloyds.lloydsiam.domain.Permission;
import java.util.List;
import java.util.Optional;

public interface PermissionRepository {
    Optional<Permission> findById(Long id);

    List<Permission> findAll();

    Permission save(Permission permission);
    
    void deleteById(Long id);
}