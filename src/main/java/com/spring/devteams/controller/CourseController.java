package com.spring.devteams.controller;

import com.spring.devteams.model.entity.Category;
import com.spring.devteams.model.entity.Course;
import com.spring.devteams.model.repo.CourseRepo;
import com.spring.devteams.model.repo.RegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final RegisterRepo registerRepo;
    private final CourseRepo courseRepo;

    public CourseController(RegisterRepo registerRepo, CourseRepo courseRepo) {
        this.registerRepo = registerRepo;
        this.courseRepo = courseRepo;
    }

    @GetMapping()
    public String addCourse(ModelMap map){
        map.addAttribute("courses",courseRepo.findAll());
        return "admin/addCourse";
    }

    @GetMapping("/course")
    public String home(){
        return "admin/course";
    }

    @GetMapping("/updateCourse{id}")
    public String updateCourse(@PathVariable("id") Long id,ModelMap map){
        Course course = courseRepo.getOne(id);
        map.addAttribute("course",course);
        return "admin/course";
    }


    @PostMapping("add")
    public String add(@RequestParam MultipartFile files, @ModelAttribute(name="course") Course course, BindingResult result)   throws IllegalStateException, IOException {
        if (result.hasErrors()){
            return "admin/category";
        }
        String fileName = StringUtils.cleanPath(files.getOriginalFilename());

        course.setCourseName(course.getCourseName().toUpperCase());
        String content = course.getCourse_content();
        course.setLocation(Base64.getEncoder().encodeToString(files.getBytes()));
        course.setImageName(fileName);
        course.setCourse_content(content.toString().replace("\r",","));
        courseRepo.save(course);
        return "redirect:/";
    }
    @GetMapping("/find")
    public String find(ModelMap map){
//        map.addAttribute("courses",courseRepo.findAll());
        return "admin/course";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable Long id ,ModelMap map){
        map.addAttribute("course",courseRepo.getOne(id));
        return "admin/course";
    }
    @GetMapping("/delete/{id}")
    @Transactional
    public String delete(@PathVariable Long id ){
        registerRepo.findByCourseId(id).forEach( r -> registerRepo.deleteById(r.getId()));
        courseRepo.deleteById(id);
        return "redirect:/";
    }

    @ModelAttribute(name = "course")
    public Course course(){ return new Course();
    }

    @ModelAttribute(name = "category")
    public Category category(){ return new Category();
    }
    @PostMapping("/findCategoryName")
    public String find(@RequestParam String name, RedirectAttributes attributes){
        String  category = name.toUpperCase();
        attributes.addFlashAttribute("courses",courseRepo.findByCategoryName(category));
        return "redirect:/";
    }

    @GetMapping ("category/{id}")
    public String searchCourseByCategory(@PathVariable long id,ModelMap map){
        map.addAttribute("courses", courseRepo.findByCategoryId(id));
        return "home";
    }
   

}
