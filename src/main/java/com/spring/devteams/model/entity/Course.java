package com.spring.devteams.model.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
public class Course implements Serializable {
    private static Long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    private String about;
    private int price;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;
    private int count;
    private String course_content;
    private String imageName;
    @Lob
    private String location;


//    public LocalDate Expire(){
//       LocalDate startTime = start;
//        LocalDate endTime = start.plusDays(7);
//        return endTime;
//    }

}
