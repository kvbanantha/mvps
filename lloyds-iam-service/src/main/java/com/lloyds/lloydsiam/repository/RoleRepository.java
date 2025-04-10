package com.lloyds.lloydsiam.repository;

import com.lloyds.lloydsiam.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}