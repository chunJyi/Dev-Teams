package com.spring.devteams.model.repo;

import com.spring.devteams.model.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepo extends JpaRepository <History,Long> {

    @Query("select h from History h where h.studentName like %?1 or h.studentName like ?2%")
      List<History>  findSearchStudentName(String name,String name2);

}
