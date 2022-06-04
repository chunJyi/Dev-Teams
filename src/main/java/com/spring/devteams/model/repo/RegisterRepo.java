package com.spring.devteams.model.repo;

import com.spring.devteams.model.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisterRepo extends JpaRepository<Register,Long> {
    List<Register> findByEnable(boolean enable);
   List<Register> findByStudentId(Long id);
   Register findByStudentIdAndCourseId(Long studentId,Long courseId);
   List<Register> findByCourseIdAndEnable(Long courseId,boolean enable);
   List<Register> findByLook(boolean look);
   List<Register> findByCourseId(Long id);


}
