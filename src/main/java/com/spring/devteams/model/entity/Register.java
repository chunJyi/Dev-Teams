package com.spring.devteams.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class Register implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Student student;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Course course;
    private Boolean enable;
    private Boolean look;
    private LocalDate date;

    public  Register(){
        enable=false;
        look = false;
    }

    public boolean isEnable() {
        return enable;
    }
}
