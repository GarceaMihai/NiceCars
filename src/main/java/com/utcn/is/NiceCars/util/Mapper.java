package com.utcn.is.NiceCars.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.utcn.is.NiceCars.dto.AnnouncementDto;
import com.utcn.is.NiceCars.dto.CommentDto;
import com.utcn.is.NiceCars.dto.UserRegistrationDto;
import com.utcn.is.NiceCars.model.Announcement;
import com.utcn.is.NiceCars.model.Comment;
import com.utcn.is.NiceCars.model.Photo;
import com.utcn.is.NiceCars.model.User;

public class Mapper {
	public static Object mapUserRegistrationDto(User user, UserRegistrationDto userRegistrationDto,
			BCryptPasswordEncoder passwordEncoder, boolean mapToDto) {
		if (mapToDto) {
			return UserRegistrationDto.builder().email(user.getEmail()).username(user.getUsername())
					.telephoneNumber(user.getTelephoneNumber()).password(user.getPassword())
					.confirmPassword(user.getPassword()).build();
		} else {
			return User.builder().password(passwordEncoder.encode(userRegistrationDto.getPassword()))
					.username(userRegistrationDto.getUsername())
					.telephoneNumber(userRegistrationDto.getTelephoneNumber()).email(userRegistrationDto.getEmail())
					.build();
		}
	}

	public static void mapUpdatedUser(User user, UserRegistrationDto userRegistrationDto) {
		user.setEmail(userRegistrationDto.getEmail());
		user.setTelephoneNumber(userRegistrationDto.getTelephoneNumber());
	}

	public static Object mapAnnouncementDto(Announcement announcement, AnnouncementDto announcementDto,
			boolean mapToDto) {
		if (mapToDto) {
			MultipartFile[] files = new MultipartFile[10];
			int i = 0;
			for (Photo photo : announcement.getPhotos()) {
				files[i] = photo;
				i++;
			}
			return AnnouncementDto.builder().title(announcement.getTitle()).description(announcement.getDescription())
					.price(announcement.getPrice()).make(announcement.getMake()).model(announcement.getModel())
					.county(announcement.getCounty()).locality(announcement.getLocality())
					.modelGeneration(announcement.getModelGeneration()).color(announcement.getColor())
					.fabricationDate(announcement.getFabricationDate()).kilometers(announcement.getKilometers())
					.horsepower(announcement.getHorsepower()).displacement(announcement.getDisplacement())
					.features(announcement.getFeatures()).consumption(announcement.getConsumption())
					.fuel(announcement.getFuel()).body(announcement.getBody()).gearbox(announcement.getGearbox())
					.powertrain(announcement.getPowertrain()).emissions(announcement.getEmissions()).photos(files)
					.build();
		} else {
			return Announcement.builder().title(announcementDto.getTitle())
					.description(announcementDto.getDescription()).price(announcementDto.getPrice())
					.make(announcementDto.getMake()).model(announcementDto.getModel())
					.county(announcementDto.getCounty()).locality(announcementDto.getLocality())
					.modelGeneration(announcementDto.getModelGeneration()).color(announcementDto.getColor())
					.fabricationDate(announcementDto.getFabricationDate()).kilometers(announcementDto.getKilometers())
					.horsepower(announcementDto.getHorsepower()).displacement(announcementDto.getDisplacement())
					.features(announcementDto.getFeatures()).consumption(announcementDto.getConsumption())
					.fuel(announcementDto.getFuel()).body(announcementDto.getBody())
					.gearbox(announcementDto.getGearbox()).powertrain(announcementDto.getPowertrain())
					.emissions(announcementDto.getEmissions()).build();
		}
	}

	public static void mapUpdatedAnnouncement(Announcement announcement, AnnouncementDto announcementDto) {
		announcement.setTitle(announcementDto.getTitle());
		announcement.setDescription(announcementDto.getDescription());
		announcement.setPrice(announcementDto.getPrice());
		announcement.setMake(announcementDto.getMake());
		announcement.setModel(announcementDto.getModel());
		announcement.setCounty(announcementDto.getCounty());
		announcement.setLocality(announcementDto.getLocality());
		announcement.setModelGeneration(announcementDto.getModelGeneration());
		announcement.setColor(announcementDto.getColor());
		announcement.setFabricationDate(announcementDto.getFabricationDate());
		announcement.setKilometers(announcementDto.getKilometers());
		announcement.setHorsepower(announcementDto.getHorsepower());
		announcement.setDisplacement(announcementDto.getDisplacement());
		announcement.setFeatures(announcementDto.getFeatures());
		announcement.setConsumption(announcementDto.getConsumption());
		announcement.setFuel(announcementDto.getFuel());
		announcement.setBody(announcementDto.getBody());
		announcement.setGearbox(announcementDto.getGearbox());
		announcement.setPowertrain(announcementDto.getPowertrain());
		announcement.setEmissions(announcementDto.getEmissions());
	}

	public static Comment mapCommentDto(Comment comment, CommentDto commentDto) {
		return Comment.builder().body(commentDto.getBody()).build();
	}

}
