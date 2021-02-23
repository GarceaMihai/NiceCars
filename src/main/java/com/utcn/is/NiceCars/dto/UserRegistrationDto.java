package com.utcn.is.NiceCars.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.utcn.is.NiceCars.util.FieldMatch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {
	@NotBlank(message = "Email cannot be blank.")
	@Email
	private String email;

	@NotBlank(message = "Username cannot be blank.")
	private String username;

	@NotBlank(message = "Password cannot be blank.")
	private String password;

	@NotBlank(message = "This field cannot be blank.")
	private String confirmPassword;

	@NotBlank(message = "Telephone number cannot be blank.")
	private String telephoneNumber;
}
