package com.spring.devteams.model.repo;

import com.spring.devteams.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;




public interface StudentRepo extends JpaRepository<Student,Long> {

 Student findByAccountId(long id);

}
