package com.spring.devteams.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Token implements Serializable {

    private static  final Long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @NotNull(message = "Account not null")
    private Account account;
    private String token;
    private LocalTime expireTime;

    public Token(Account account){
        this.account = account;
        this.token = UUID.randomUUID().toString();
        this.expireTime = LocalTime.now().plusMinutes(1);
    }

    public boolean isExpire(){
        int startTime = expireTime.toSecondOfDay();
        int endTime = LocalTime.now().minusMinutes(1).toSecondOfDay();
        return endTime - startTime >= 0;
    }


}
