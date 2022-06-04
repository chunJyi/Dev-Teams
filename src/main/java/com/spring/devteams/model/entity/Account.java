package com.spring.devteams.model.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class Account implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "Please enter name")
	private String username;
	@NotEmpty(message = "please enter email")
	private String email;
	@NotEmpty(message = "please enter password")
	private String password;
	@NotEmpty(message = "please enter phone")
	private String phone;
	private Boolean enable;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	@Enumerated(EnumType.STRING)
	private Role role;

	public enum Role {
		ROLE_ADMIN {
			@Override
			public String toString() {
				return "Admin";
			}
		},
		ROLE_MEMBER {
			@Override
			public String toString() {
				return "Member";
			}
		};
	}

	public Account() {
		this.role = Role.ROLE_MEMBER;
	}
}
