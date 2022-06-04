package com.spring.devteams.controller;

import com.spring.devteams.model.entity.Course;
import com.spring.devteams.model.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class DetailsController {
    @Autowired
    private CourseRepo courseRepo;
    @GetMapping("detail/{id}")
    public String detail(@PathVariable Long id, ModelMap map){
        Course course = courseRepo.getOne(id);
        String[] course_content = course.getCourse_content().toString().split(",");
        map.addAttribute("detail",courseRepo.getOne(id));
        map.addAttribute("course_content",course_content);
        return "details";
    }
}
