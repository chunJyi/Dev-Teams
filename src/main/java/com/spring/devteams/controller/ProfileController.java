package com.spring.devteams.controller;

import com.spring.devteams.common.CommonAdvice;
import com.spring.devteams.model.entity.*;
import com.spring.devteams.model.repo.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
@AllArgsConstructor
public class ProfileController {

    private final RegisterRepo registerRepo;
    private final StudentRepo studentRepo;
    private final CommonAdvice commonAdvice;
    private final AccountRepo accountRepo;
    

    @GetMapping("/profile")
    public String home(ModelMap map) {

        String authUserName = commonAdvice.loginUser().getUsername();
        Account a = accountRepo.findByEmail(authUserName);
        Student student = studentRepo.findByAccountId(a.getId());
       return lookProfile(map,student,a);

    }

    public String lookProfile ( ModelMap map,Student student,Account a){
        map.addAttribute("account", a);
        if (student != null) {
            List<Register> registers = registerRepo.findByStudentId(student.getId());
            List<Register> request = registers.stream().filter(b -> b.getEnable() == false).collect(Collectors.toList());
            List<Register> accpet = registers.stream().filter(b -> b.getEnable() == true).collect(Collectors.toList());
            map.addAttribute("request", request);
            map.addAttribute("accept", accpet);
        } else {
            List<Register> listObj = new ArrayList<Register>();
            map.addAttribute("request", listObj);
            map.addAttribute("accept", listObj);
        }
        return "member/profile";
    }


}
