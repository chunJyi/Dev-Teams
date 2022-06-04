package com.spring.devteams.controller;

import com.spring.devteams.model.entity.Category;
import com.spring.devteams.model.entity.Course;
import com.spring.devteams.model.repo.AccountRepo;
import com.spring.devteams.model.repo.CategoryRepo;
import com.spring.devteams.model.repo.CourseRepo;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class HomeController {

	private final CourseRepo courseRepo;
	private final AccountRepo accountRepo;
	private final CategoryRepo repo;
	private List<Course> course;

	@GetMapping("/")
	private String home(ModelMap map) {
		course = courseRepo.findAll();
		List<Category> cate = repo.findAll();
		map.addAttribute("course",course);
		map.addAttribute("catego", cate);
		return "home";
	}

	@GetMapping("home{id:\\d+}")
	private String find(@PathVariable(name = "id") Long id, ModelMap map) {
		map.addAttribute("courses", courseRepo.findByCategoryId(id));
		return "home";
	}

	@GetMapping("/errorPage")
	private String find() {
		return "registerErrorPage";
	}

	@GetMapping("/successPage")
	private String succes() {
		return "registerSuccessPage";
	}
	 @GetMapping ("/course/search")
	    public String basicCourseNameSearch(String name, ModelMap map){
		 List<Course> courses = course.stream().filter( c -> c.getCourseName().contains(name.toUpperCase())).collect(Collectors.toList());
	         map.addAttribute("courses", courses);
	        return "home";
	    }


}
