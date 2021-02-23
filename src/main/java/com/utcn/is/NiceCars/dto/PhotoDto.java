package com.utcn.is.NiceCars.dto;

import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDto {
	@Size(min = 1, message = "You must add at least 1 photos.")
	@Size(max = 10, message = "You can add at most 10 photos.")
	private MultipartFile[] files;
}
