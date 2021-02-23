package com.utcn.is.NiceCars.util;

import org.apache.commons.lang3.StringUtils;

import com.utcn.is.NiceCars.dto.FilterDto;
import com.utcn.is.NiceCars.model.Announcement;

public class SearchUtil {
	public static boolean search(Announcement announcement, String term) {
		if (StringUtils.containsIgnoreCase(announcement.getTitle(), term)) {
			return true;
		}
		if (announcement.getModel().equalsIgnoreCase(term)) {
			return true;
		}
		if (announcement.getMake().equalsIgnoreCase(term)) {
			return true;
		}
		return false;
	}

	public static boolean filter(Announcement announcement, FilterDto filterDto) {
		if (announcement.getPrice() > filterDto.getMaxPrice()) {
			return false;
		}
		if (announcement.getFabricationDate().getYear() < filterDto.getOldestFabricationYear()) {
			return false;
		}
		if (announcement.getKilometers() > filterDto.getMaxKilometers()) {
			return false;
		}
		if (announcement.getDisplacement() > filterDto.getMaxDisplacement()) {
			return false;
		}
		if (announcement.getHorsepower() < filterDto.getMinHorsepower()) {
			return false;
		}
		if (announcement.getConsumption() > filterDto.getMaxConsumption()) {
			return false;
		}
		boolean okMakes = true;
		if (!filterDto.getMakes().isEmpty()) {
			okMakes = false;
			for (String s : filterDto.getMakes()) {
				if (announcement.getMake().equals(s)) {
					okMakes = true;
					break;
				}
			}
		}
		boolean okCounty = true;
		if (!filterDto.getCounty().isEmpty()) {
			okCounty = false;
			for (String s : filterDto.getCounty()) {
				if (announcement.getCounty().equals(s)) {
					okCounty = true;
					break;
				}
			}
		}
		boolean okFuel = true;
		if (!filterDto.getFuel().isEmpty()) {
			okFuel = false;
			for (String s : filterDto.getFuel()) {
				if (announcement.getFuel().equals(s)) {
					okFuel = true;
					break;
				}
			}
		}
		boolean okBody = true;
		if (!filterDto.getBody().isEmpty()) {
			okBody = false;
			for (String s : filterDto.getBody()) {
				if (announcement.getBody().equals(s)) {
					okBody = true;
					break;
				}
			}
		}
		boolean okGB = true;
		if (!filterDto.getGearbox().isEmpty()) {
			okGB = false;
			for (String s : filterDto.getGearbox()) {
				if (announcement.getGearbox().equals(s)) {
					okGB = true;
					break;
				}
			}
		}
		boolean okPT = true;
		if (!filterDto.getPowertrain().isEmpty()) {
			okPT = false;
			for (String s : filterDto.getPowertrain()) {
				if (announcement.getPowertrain().equals(s)) {
					okPT = true;
					break;
				}
			}
		}
		boolean okEmss = true;
		if (!filterDto.getEmissions().isEmpty()) {
			okEmss = false;
			for (String s : filterDto.getEmissions()) {
				if (announcement.getEmissions().equals(s)) {
					okEmss = true;
					break;
				}
			}
		}
		return okMakes && okCounty && okFuel && okBody && okGB && okPT && okEmss;
	}
}
