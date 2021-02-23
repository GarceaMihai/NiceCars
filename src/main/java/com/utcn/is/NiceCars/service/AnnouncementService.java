package com.utcn.is.NiceCars.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.utcn.is.NiceCars.dto.AnnouncementDto;
import com.utcn.is.NiceCars.dto.CommentDto;
import com.utcn.is.NiceCars.dto.FilterDto;
import com.utcn.is.NiceCars.dto.PhotoDto;
import com.utcn.is.NiceCars.model.Announcement;
import com.utcn.is.NiceCars.model.Comment;
import com.utcn.is.NiceCars.model.Photo;
import com.utcn.is.NiceCars.model.User;
import com.utcn.is.NiceCars.repository.AnnouncementRepository;
import com.utcn.is.NiceCars.util.Mapper;
import com.utcn.is.NiceCars.util.SearchUtil;
import com.utcn.is.NiceCars.util.StatisticsUtil;

@Service
public class AnnouncementService {
	@Autowired
	private AnnouncementRepository announcementRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private CommentService commentService;

	@Transactional
	public Announcement save(AnnouncementDto announcementDto, String username) {
		Announcement announcement = new Announcement();
		announcement = (Announcement) Mapper.mapAnnouncementDto(announcement, announcementDto, false);
		announcement.setCreated(Instant.now());
		announcement.setActive(true);
		announcement.setPromoted(false);
		User user = userService.findByUsername(username);
		announcement.setUser(user);
		announcement = announcementRepository.save(announcement);
		List<Photo> photos = new ArrayList<>();
		boolean isCover = true;
		for (MultipartFile file : announcementDto.getPhotos()) {
			Photo photo = photoService.save(announcement, file, isCover);
			photos.add(photo);
			isCover = false;
		}
		announcement.setPhotos(photos);
		return announcementRepository.save(announcement);
	}

	public List<Announcement> findAll() {
		List<Announcement> announcements = announcementRepository.findAllActiveAnnouncements();
		List<Announcement> promotedAnnouncements = new ArrayList<>();
		ListIterator<Announcement> iterator = announcements.listIterator();
		while (iterator.hasNext()) {
			Announcement announcement = (Announcement) iterator.next();
			if (announcement.isPromoted()) {
				promotedAnnouncements.add(announcement);
				iterator.remove();
			}
		}
		Collections.sort(announcements, Collections.reverseOrder());
		Collections.sort(promotedAnnouncements, Collections.reverseOrder());
		promotedAnnouncements.addAll(announcements);
		return promotedAnnouncements;
	}

	public Optional<Announcement> findById(Long id) {
		return announcementRepository.findById(id);
	}

	public List<Announcement> search(String term) {
		List<Announcement> announcements = announcementRepository.findAllActiveAnnouncements();
		List<Announcement> foundAnnouncements = new ArrayList<>();
		List<Announcement> promotedAnnouncements = new ArrayList<>();
		for (Announcement announcement : announcements) {
			if (SearchUtil.search(announcement, term)) {
				if (announcement.isPromoted()) {
					promotedAnnouncements.add(announcement);
				} else {
					foundAnnouncements.add(announcement);
				}
			}
		}
		Collections.sort(promotedAnnouncements, Collections.reverseOrder());
		Collections.sort(foundAnnouncements, Collections.reverseOrder());
		promotedAnnouncements.addAll(foundAnnouncements);
		return promotedAnnouncements;
	}

	public void postComment(CommentDto commentDto, String username, Long announcementId) {
		Comment comment = new Comment();
		comment = Mapper.mapCommentDto(comment, commentDto);
		comment.setCreated(Instant.now());
		comment.setUser(userService.findByUsername(username));
		Announcement announcement = announcementRepository.getOne(announcementId);
		comment.setAnnouncement(announcement);
		commentService.save(comment);
		comment.alert();
	}

	public void promote(Long id) {
		Announcement announcement = announcementRepository.getOne(id);
		announcement.setPromoted(true);
		announcementRepository.save(announcement);
	}

	public Announcement update(Long id, AnnouncementDto announcementDto) {
		Announcement announcement = announcementRepository.getOne(id);
		Mapper.mapUpdatedAnnouncement(announcement, announcementDto);
		return announcementRepository.save(announcement);
	}

	public void deletePhoto(Long photoId) {
		Photo photo = photoService.findById(photoId);
		Announcement announcement = photo.getAnnouncement();
		announcement.getPhotos().remove(photo);
		announcementRepository.save(announcement);
	}

	public void changeCover(Long photoId) {
		Photo photo = photoService.findById(photoId);
		Announcement announcement = photo.getAnnouncement();
		for (Photo temp : announcement.getPhotos()) {
			if (temp.isCover()) {
				temp.setCover(false);
			}
		}
		photo.setCover(true);
		announcementRepository.save(announcement);
	}

	public Announcement addPhotos(Long id, PhotoDto photoDto) {
		Announcement announcement = announcementRepository.getOne(id);
		List<Photo> photos = new ArrayList<>();
		for (MultipartFile file : photoDto.getFiles()) {
			if (!file.isEmpty()) {
				Photo photo = photoService.save(announcement, file, false);
				photos.add(photo);
			}
		}
		announcement.getPhotos().addAll(photos);
		return announcementRepository.save(announcement);
	}

	public Announcement deactivate(Long id) {
		Announcement announcement = announcementRepository.getOne(id);
		announcement.setActive(false);
		return announcementRepository.save(announcement);
	}

	public Announcement activate(Long id) {
		Announcement announcement = announcementRepository.getOne(id);
		announcement.setActive(true);
		return announcementRepository.save(announcement);
	}

	public void deleteComment(Long id) {
		commentService.delete(id);
	}

	public Map<String, String> getGeneralStatistics() {
		return StatisticsUtil.getGeneralStatistics(announcementRepository.findAllActiveAnnouncements());
	}

	public Map<String, Integer> getMakeStatistics() {
		return StatisticsUtil.getMakeStatistics(announcementRepository.findAllActiveAnnouncements());
	}

	public Map<String, Integer> getCountyStatistics() {
		return StatisticsUtil.getCountyStatistics(announcementRepository.findAllActiveAnnouncements());
	}

	public Map<String, Integer> getFuelStatistics() {
		return StatisticsUtil.getFuelStatistics(announcementRepository.findAllActiveAnnouncements());
	}

	public Map<String, Integer> getBodyStatistics() {
		return StatisticsUtil.getBodyStatistics(announcementRepository.findAllActiveAnnouncements());
	}

	public Map<String, Integer> getGearboxStatistics() {
		return StatisticsUtil.getGearboxStatistics(announcementRepository.findAllActiveAnnouncements());
	}

	public Map<String, Integer> getPowertrainStatistics() {
		return StatisticsUtil.getPowertrainStatistics(announcementRepository.findAllActiveAnnouncements());
	}

	public Map<String, Integer> getEmissionsStatistics() {
		return StatisticsUtil.getEmissionsStatistics(announcementRepository.findAllActiveAnnouncements());
	}

	public List<Announcement> filter(FilterDto filterDto) {
		List<Announcement> announcements = announcementRepository.findAllActiveAnnouncements();
		List<Announcement> foundAnnouncements = new ArrayList<>();
		List<Announcement> promotedAnnouncements = new ArrayList<>();
		for (Announcement announcement : announcements) {
			if (SearchUtil.filter(announcement, filterDto)) {
				if (announcement.isPromoted()) {
					promotedAnnouncements.add(announcement);
				} else {
					foundAnnouncements.add(announcement);
				}
			}
		}
		Collections.sort(promotedAnnouncements, Collections.reverseOrder());
		Collections.sort(foundAnnouncements, Collections.reverseOrder());
		promotedAnnouncements.addAll(foundAnnouncements);
		return promotedAnnouncements;
	}

}
