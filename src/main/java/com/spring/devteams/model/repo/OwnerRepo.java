package com.spring.devteams.model.repo;

import com.spring.devteams.model.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepo extends JpaRepository<Owner,Long> {
}
