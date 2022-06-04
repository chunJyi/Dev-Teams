package com.spring.devteams.model.repo;

import com.spring.devteams.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AccountRepo extends JpaRepository<Account,Long> {


    Account findByEmail(String email);
    Account findByEmailAndUsernameAndBirthday(String email, String name, LocalDate birthday);
}
