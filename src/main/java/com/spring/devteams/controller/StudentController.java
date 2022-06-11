package com.spring.devteams.controller;

import com.spring.devteams.model.entity.Account;
import com.spring.devteams.model.entity.Course;
import com.spring.devteams.model.entity.Register;
import com.spring.devteams.model.entity.Student;
import com.spring.devteams.model.repo.AccountRepo;
import com.spring.devteams.model.repo.CourseRepo;
import com.spring.devteams.model.repo.RegisterRepo;
import com.spring.devteams.model.repo.StudentRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;


@Controller
public class StudentController {


    private final StudentRepo studentRepo;

    private final CourseRepo courseRepo;

    private final AccountRepo accountRepo;

    private final RegisterRepo registerRepo;

    public StudentController(StudentRepo studentRepo, CourseRepo courseRepo, AccountRepo accountRepo, RegisterRepo registerRepo) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.accountRepo = accountRepo;
        this.registerRepo = registerRepo;
    }



    /*  Request for join Class (Start Method)*/
    @GetMapping("register/{id}")
    public String  joinClass(@PathVariable Long id, ModelMap map, RedirectAttributes attributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account a = accountRepo.findByEmail(userDetails.getUsername());
//        first find student
        Student stu = studentRepo.findByAccountId(a.getId());
        if (stu == null) {
            map.addAttribute("student_id", a.getId());
            map.addAttribute("course_id", id);
            return "member/register";
        }

        Course course = courseRepo.getById(id);
        int studentCount  = course.getCount();
        LocalDate endDate = course.getStart().plusDays(7);
        LocalDate date = LocalDate.now();
/*
        check expire date for join class
*/
        if( date.isAfter(endDate)){
            map.addAttribute("message","Please Check The Date.");
            map.addAttribute("id",course.getId());
            return "notification/registerErrorPage";
        }

        List<Register> registers= registerRepo.findByCourseIdAndEnable(id,true);

        int confirmedStudent =registers.size();
        if(confirmedStudent >= studentCount){
            map.addAttribute("message","Sorry,this course is not available!.");
            map.addAttribute("id",course.getId());
            return "notification/registerErrorPage";
        }
        Register regi = registerRepo.findByStudentIdAndCourseId(stu.getId(), id);
        if (regi != null) {
            map.addAttribute("message","Can't join the same course");
            return "notification/registerErrorPage";
        }
        Register register = new Register();
        register.setStudent(studentRepo.getById(stu.getId()));
        register.setCourse(courseRepo.getById(id));
        registerRepo.save(register);
        attributes.addFlashAttribute("success",true);
        map.addAttribute("message","yay, everything is working.");
        return "notification/registerSuccessPage";
    }

    /*  Request for join Class (End Method)*/

    /*Create Object for Student (Start Method) -> acceptProfile.html*/
    @ModelAttribute(name = "student")
    public Student student(){
        return new Student();
    }
    /*Create Object for Student (Start Method)*/


    /*Request Student From (Start Method) <- member/register.html*/
    @PostMapping("regi")
    public String register(@ModelAttribute("student") Student student,@RequestParam Course course ,ModelMap map ){
        studentRepo.save(student);
        Register register = new Register();
        register.setStudent(student);
        register.setCourse(course);
        registerRepo.save(register);
        map.addAttribute("success",true);
        map.addAttribute("message","yay, everything is working.");
        return "notification/registerSuccessPage";
    }


    @GetMapping("deleteRegi/{id}") //cancel by student
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        Register register = registerRepo.getById(id);
        if(register.getEnable().equals(false)){
        registerRepo.deleteById(id);
        return "redirect:/profile";
        }
        redirectAttributes.addFlashAttribute("n",true);
        return "redirect:/profile";
    }



}
