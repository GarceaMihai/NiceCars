package com.utcn.is.NiceCars.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.utcn.is.NiceCars.model.Announcement;
import com.utcn.is.NiceCars.model.Photo;
import com.utcn.is.NiceCars.repository.PhotoRepository;

@Service
public class PhotoService {
	@Autowired
	private PhotoRepository photoRepository;

	public Photo save(Announcement announcement, MultipartFile file, boolean isCover) {
		Photo photo = new Photo();
		try {
			photo.setPhoto(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		photo.setAnnouncement(announcement);
		photo.setCover(isCover);
		return photoRepository.save(photo);
	}

	public Photo findById(Long id) {
		return photoRepository.findById(id).get();
	}

}
