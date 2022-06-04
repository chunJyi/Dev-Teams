package com.spring.devteams.model.repo;

import com.spring.devteams.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<Token,Long> {
    Token findByToken(String token);

}
