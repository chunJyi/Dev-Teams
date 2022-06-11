package com.spring.devteams.controller;

import com.spring.devteams.common.CommonAdvice;
import com.spring.devteams.model.entity.Account;
import com.spring.devteams.model.repo.AccountRepo;
import com.spring.devteams.model.repo.TokenRepo;
import com.spring.devteams.util.MailHelper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.beans.Transient;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Controller
public class AccountController {


    private final  CommonAdvice commonAdvice;
    private final  AccountRepo accountRepo;
    private final  PasswordEncoder encoder;

    public AccountController(CommonAdvice commonAdvice, AccountRepo accountRepo, PasswordEncoder encoder) {
        this.commonAdvice = commonAdvice;
        this.accountRepo = accountRepo;
        this.encoder = encoder;
    }

    @GetMapping("sign-up")
    public String home(){
        return "sign-up";
    }
    @PostMapping("sign-up")
    @Transient
    public String save(@ModelAttribute(name = "account") @Valid Account account, BindingResult result
                                                , Model model) throws IOException {
        if(result.hasErrors()){
            return "sign-up";
        }
        if(accountRepo.findByEmail(account.getEmail()) != null){
            model.addAttribute("fail",true);
            return "sign-up";
        }
        if (account.getBirthday().isAfter(LocalDate.now())){
            return "sign-up";
        }

        account.setEnable(true);
        account.setPassword(encoder.encode(account.getPassword()));
        account.setRole(Account.Role.ROLE_MEMBER);
        accountRepo.save(account);
        model.addAttribute("check",true);  // use own
        return "login";
    }

    /*Confirm Code from email verfirm*/
   /* @PostMapping("/confirm")
    public String confirm(@RequestParam String token,RedirectAttributes attributes){
        Token confirmToken = tokenRepo.findByToken(token);

        if(Objects.isNull(confirmToken)) {
//            throw new EntityNotFoundException("Token not found!");
           attributes.addFlashAttribute("token",true);
            return "redirect:/login";
        }
        Account account = confirmToken.getAccount();
        Long tokenId = confirmToken.getId();
        if(confirmToken.isExpire() && account.getEnable().equals(Boolean.FALSE)){


            tokenRepo.deleteById(tokenId);
//            questionRepo.deleteByAccount(account);
            accountRepo.deleteById(account.getId());
//            throw new RuntimeException("Try again with new account!");
            attributes.addFlashAttribute("delete",true);
            return "redirect:/login";
        }

        account.setEnable(Boolean.TRUE);
        accountRepo.save(account);
        tokenRepo.deleteById(tokenId);
        attributes.addFlashAttribute("create",true);
        return "redirect:/login";
    }*/
//    @GetMapping("/confirm")
//    public String getConfirm(){
//        return "confirm";
//    }
    @GetMapping("/forget")
     public String forget(@RequestParam String email,Model model,RedirectAttributes attributes){

        Account account = accountRepo.findByEmail(email);

        if(Objects.isNull(account)) {
//            throw new EntityNotFoundException("account not found!");
            attributes.addFlashAttribute("notfound",true);
            return "redirect:/login";
        }
//        model.addAttribute("forgetAccount",account);
        model.addAttribute("email",email);
        return "forgetpassword";

    }
    @PostMapping("/forgetPassword")
    public String forgetPassword(@ModelAttribute Account account,@RequestParam String email,Model model,RedirectAttributes attributes){
        Account forgetaccount = accountRepo.findByEmailAndUsernameAndBirthday(email,account.getUsername(),account.getBirthday());
        if (Objects.isNull(forgetaccount)){
//            throw new EntityNotFoundException("account not found!");
            attributes.addFlashAttribute("notfound",true);
            return "redirect:/login";
        }
        model.addAttribute("restartaccount",forgetaccount);
        return "restartPassword";
    }
    @PostMapping("/restartPassword")
    public String restartPassword(@ModelAttribute(name = "restartaccount")  Account restartaccount,@RequestParam String password,RedirectAttributes attributes){
        Account account = accountRepo.getOne(restartaccount.getId());
        account.setPassword(encoder.encode(password));
        accountRepo.save(account);
        attributes.addFlashAttribute("change",true);
        return "redirect:/login";
    }

    
    
	/* Start Change Password */
    @GetMapping("/changePassword")
    public String restartPassword(@RequestParam String oldpwd,Model model){
        String username = commonAdvice.loginUser().getUsername();
        Account user = accountRepo.findByEmail(username);
        if (encoder.matches(oldpwd,user.getPassword())){
            model.addAttribute("account",user);
            return "changePassword";
        }
        return "redirect:/profile";
    }

    @PostMapping("/changePassword")
    public String restartPassword(@ModelAttribute(name = "account") Account account ){
    	Account a = accountRepo.getById(account.getId());
    	a.setPassword(encoder.encode(account.getPassword()));
        accountRepo.save(a);
        return "redirect:/profile";
    }
    
    /* end Change Password */
    
    

    @PostMapping("/editAccount")
    public String editProfile(@ModelAttribute(name = "account")  Account account){
        Account acc=accountRepo.getById(account.getId());
        acc.setUsername(account.getUsername());
        acc.setEmail(account.getEmail());
        acc.setPhone(account.getPhone());
        accountRepo.save(acc);
        return "redirect:/profile";
    }

    @ModelAttribute(name = "account")
    public Account account(){
        return new Account();
    }

}
