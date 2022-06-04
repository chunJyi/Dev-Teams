package com.spring.devteams.controller;

import com.spring.devteams.common.CommonAdvice;
import com.spring.devteams.model.entity.Course;
import com.spring.devteams.model.entity.History;
import com.spring.devteams.model.entity.Register;
import com.spring.devteams.model.repo.CourseRepo;
import com.spring.devteams.model.repo.HistoryRepo;
import com.spring.devteams.model.repo.RegisterRepo;
import com.spring.devteams.model.repo.StudentRepo;
import com.spring.devteams.service.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class HistoryController {


    private final HistoryService historyService;
    private final CommonAdvice commonAdvice;
    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    private final HistoryRepo historyRepo;
    private final RegisterRepo registerRepo;

    @GetMapping("/allRecords")
    public String StudentHistory(ModelMap map){
        return listByPage(map,1);
    }

//    @GetMapping("/student/find")
//    public String searchRecord(String studentName, ModelMap map){
//        List<History> records = historyRepo.findSearchStudentName(studentName, studentName);
//
//        map.addAttribute("records",records);
//        return "studentRecords";
//    }

    @GetMapping("/page/{pageNumber}")
    public String listByPage(ModelMap map, @PathVariable("pageNumber") int currentPage){

        Page<History> page = historyService.listAll(currentPage);
        List<Register> registers = registerRepo.findAll();
        int request = registers.stream().filter(b -> b.getEnable() == false).collect(Collectors.toList()).size();
        int accept = registers.stream().filter(b -> b.getEnable() == true).collect(Collectors.toList()).size();
        List<History> records =page.getContent();
        List<Course> courses=commonAdvice.openCourse();
        map.addAttribute("student",studentRepo.findAll().size());
        map.addAttribute("courseSize",courseRepo.findAll().size());
        map.addAttribute("student",studentRepo.findAll().size());
        map.addAttribute("request",request);

        map.addAttribute("accept",accept);
        map.addAttribute("courses",courses);
        map.addAttribute("records",records);
        map.addAttribute("totalRecordElements",page.getTotalElements());
        map.addAttribute("totalRecordPages",page.getTotalPages());
        map.addAttribute("currentRecordPage",currentPage);

        return "admin/studentRecord";

    }


}
