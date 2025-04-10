package com.lloyds.goalsobjectives.repository;

import com.lloyds.goalsobjectives.domain.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective, Long> {
}