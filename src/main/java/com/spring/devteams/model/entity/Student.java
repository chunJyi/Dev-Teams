package com.spring.devteams.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Student implements Serializable {

    private static final Long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;
    private String name;
    private String stu_email;
    private String PhNo;
    @OneToOne
    private Account account;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    //add
    private Boolean enable;
    @ManyToMany
    private List<Course> course;

}
