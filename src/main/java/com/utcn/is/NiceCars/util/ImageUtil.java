package com.utcn.is.NiceCars.util;

import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class ImageUtil {
	public String getImageData(byte[] byteData) {
		return Base64.getMimeEncoder().encodeToString(byteData);
	}
}
