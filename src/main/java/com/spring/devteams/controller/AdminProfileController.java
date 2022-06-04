package com.spring.devteams.controller;

import com.spring.devteams.model.entity.History;
import com.spring.devteams.model.entity.Register;
import com.spring.devteams.model.repo.HistoryRepo;
import com.spring.devteams.model.repo.RegisterRepo;
import com.spring.devteams.model.repo.StudentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping()
@AllArgsConstructor
public class AdminProfileController {

    private final RegisterRepo registerRepo;
    private final StudentRepo studentRepo;
    private final HistoryRepo historyRepo;


    @GetMapping("/requestProfile")
    public String requestProfile(ModelMap map) {
        map.addAttribute("student_request", registerRepo.findByEnable(false));
        return "admin/studentRequest";
    }

    @GetMapping("/acceptProfile")
    public String profile(ModelMap map) {
        map.addAttribute("accept_student", registerRepo.findByEnable(true));
        return "admin/acceptProfile";
    }

    @GetMapping("/student/{id}")
    public String findStudentByCourseName(ModelMap map, @PathVariable Long id) {
        map.addAttribute("accept_student", registerRepo.findByCourseId(id));
        return "admin/acceptProfile";
    }

    @GetMapping("accept/{id}")
    public String accept(@PathVariable Long id, RedirectAttributes attributes) {
        Register regiStudent = registerRepo.getOne(id);

        regiStudent.setEnable(true);
        regiStudent.setDate(LocalDate.now());
        registerRepo.save(regiStudent);

        /* for read only history */
        History history = new History();
        history.setStudentName(regiStudent.getStudent().getName());
        history.setAccount(regiStudent.getStudent().getAccount());
        history.setCourseName(regiStudent.getCourse().getCourseName());
        history.setDate(LocalDate.now());
        historyRepo.save(history);
        String url = "Accept Your Registration";
        String a = regiStudent.getStudent().getAccount().getEmail();
//        mailHelper.sendEmail(a,"Student", url);
        attributes.addFlashAttribute("regiStudent", true);
        return "redirect:/requestProfile";
    }


    /*
         remove request form from user;
    */
    @GetMapping("remove/{id}")
    public String remove(@PathVariable Long id, RedirectAttributes attributes) {
        registerRepo.deleteById(id);
        attributes.addFlashAttribute("remove", true);
        return "redirect:/acceptProfile";
    }

//    @GetMapping("deleteStu/{id}")
//    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
//        registerRepo.deleteById(id);
//        attributes.addFlashAttribute("remove", true);
//        return "redirect:/requestProfile";
//    }


/*
    delete student
*/
    @GetMapping("removeStudent/{id}")
    public String delete(@PathVariable Long id, ModelMap map) {
        Register register = registerRepo.getOne(id);
        register.setEnable(false);
        registerRepo.save(register);
        map.addAttribute("message", "" +
                "removed this student");
        return "registerSuccessPage";
    }

    @GetMapping("info/{id}")
    public String stuInfo(@PathVariable Long id, ModelMap map) {
        List<Register> registers = registerRepo.findByStudentId(id);
        map.addAttribute("student", studentRepo.getOne(id));
        List<Register> request = registers.stream().filter(b -> b.getEnable() == false).collect(Collectors.toList());
        List<Register> record = registers.stream().filter(b -> b.getEnable() == true).collect(Collectors.toList());
        map.addAttribute("request", request);
//        map.addAttribute("courseName",request.stream().filter( req -> req.getCourse()))
        map.addAttribute("record", record);
        return "member/profile";
    }

    @GetMapping("/studentRecord")
    public String studentRecord(ModelMap map) {
        List<History> records = historyRepo.findAll();
        map.addAttribute("records", records);
        return "studentRecords";
    }


}
