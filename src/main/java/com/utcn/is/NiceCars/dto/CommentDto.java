package com.utcn.is.NiceCars.dto;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
	@Size(min=10, message = "Minimum length for comment is 10 characters.")
	private String body;
	
	private Long userId;
}
