package com.wellness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String password;
//	private String userRole;
}
