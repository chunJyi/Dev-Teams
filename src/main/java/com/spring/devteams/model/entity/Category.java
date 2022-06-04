package com.spring.devteams.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Entity
@Data
public class Category implements Serializable {

    private static final Long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "please enter name")
    private String name;

}
