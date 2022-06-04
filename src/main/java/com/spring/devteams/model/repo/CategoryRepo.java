package com.spring.devteams.model.repo;

import com.spring.devteams.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {

}
