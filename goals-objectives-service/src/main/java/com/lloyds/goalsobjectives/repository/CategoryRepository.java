package com.lloyds.goalsobjectives.repository;

import com.lloyds.goalsobjectives.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}