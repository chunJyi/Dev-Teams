package com.spring.devteams.common;

import com.spring.devteams.model.entity.*;
import com.spring.devteams.model.repo.*;

import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;


@ControllerAdvice
@AllArgsConstructor
public class CommonAdvice {

    private final CategoryRepo repo;
    private final CourseRepo courseRepo;
    private final RegisterRepo registerRepo;
    private final AccountRepo accountRepo;
    private List<Course> course;
//    @Autowired
//    private PhotoRepo photoRepo;

//    @ModelAttribute( name="img")
//    public Photo photoList(){
//        long idd = 1;
//        Photo img = photoRepo.findByAccountId(idd);
//        return img;
//    }



    public UserDetails loginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails;
    }

    @ModelAttribute(name = "account")
    public Account account(){
        return new Account();
    }

    @ModelAttribute("catego")
    public List<Category> categories() {
        return repo.findAll();
    }

    @ModelAttribute("courses")
    public List<Course> addCourse(){
      course=courseRepo.findAll();
      return course;
    }
    @ModelAttribute("openCourses")
    public List<Course> openCourse(){
//      return courseRepo.findByStartBefore(LocalDate.now());
        return courseRepo.findAll();
    }
    @ModelAttribute(name = "category")
    public Category category(){ return new Category();

    }
    @ModelAttribute("look")
    public int look(){
        List<Register> register = registerRepo.findByLook(false);
        return register.size();
    }

    public String member(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account a= accountRepo.findByEmail(userDetails.getUsername());
        String  user = a.getUsername();
        return user;


    }
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }












}
