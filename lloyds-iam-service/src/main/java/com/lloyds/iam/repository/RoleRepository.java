package com.lloyds.iam.repository;

import com.lloyds.iam.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}