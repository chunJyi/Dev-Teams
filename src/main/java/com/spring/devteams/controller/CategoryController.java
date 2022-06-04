package com.spring.devteams.controller;

import com.spring.devteams.model.entity.Category;
import com.spring.devteams.model.entity.Course;
import com.spring.devteams.model.repo.CategoryRepo;
import com.spring.devteams.model.repo.CourseRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@AllArgsConstructor
public class CategoryController {

    private final CategoryRepo categoryRepo;
    private final CourseRepo courseRepo;

    @GetMapping("/category")
    public String find(ModelMap map){
        map.addAttribute("categories",categoryRepo.findAll());
        return "admin/category";
    }

    @PostMapping("category")
    @Transient
    public String save (@ModelAttribute(name = "category") Category category, BindingResult result)
            throws IllegalStateException, IOException {
        if (result.hasErrors()){
            return "admin/category";
        }
        Category cat = new Category();
        cat.setName(category.getName().toUpperCase());
        categoryRepo.save(cat);
        return "redirect:/course/course";
    }
    @PostMapping("editCategory")
    @Transient
    public String editCategory (@ModelAttribute(name = "category") Category category, BindingResult result)
            throws IllegalStateException, IOException {
        if (result.hasErrors()){
            return "admin/category";
        }
        Category cat = new Category();
        cat.setId(category.getId());
        cat.setName(category.getName().toUpperCase());
        categoryRepo.save(cat);
        return "redirect:/category";
    }


    @GetMapping("edit/{id}")
    public String  edit(@PathVariable Long id, ModelMap map){
        map.put("category",categoryRepo.getOne(id));
        return "admin/category";
    }

    @GetMapping("delete/{id}")
    public String CategoryDelete(@PathVariable Long id){
        Category category=categoryRepo.getOne(id);
        List<Course> cate = courseRepo.findByCategoryId(category.getId());
        courseRepo.deleteAll(cate);
        categoryRepo.deleteById(id);
        return "redirect:/category";
    }
}
