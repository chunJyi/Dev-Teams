package com.spring.devteams.model.repo;

import com.spring.devteams.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.NamedQuery;
import java.time.LocalDate;
import java.util.List;

public interface CourseRepo extends JpaRepository<Course,Long> {
    List<Course> findByCategoryId(Long id);

    List<Course> findByCategoryName(String name);
    List<Course> findByStartBefore(LocalDate date);

  @Query("select c from Course c where c.courseName  LIKE '%?1%'")
//    @Query("select c from Course c where c.courseName LIKE %?1 or c.courseName like ?2%")
    List<Course> findCourseName(String search,String search2);
}
