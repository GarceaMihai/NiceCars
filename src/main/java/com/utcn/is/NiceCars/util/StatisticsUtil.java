package com.utcn.is.NiceCars.util;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.utcn.is.NiceCars.dto.AnnouncementDto;
import com.utcn.is.NiceCars.model.Announcement;

public class StatisticsUtil {
	public static Map<String, String> getGeneralStatistics(List<Announcement> announcements) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("Number of announcements", Integer.toString(announcements.size()));
		map.put("Number of promoted announcements", Integer.toString(findNumberOfPromotedAnnouncements(announcements)));
		map.put("Highest price", Float.toString(findHighestPrice(announcements)));
		map.put("Average price", Float.toString(computeAveragePrice(announcements)));
		map.put("Lowest price", Float.toString(findLowestPrice(announcements)));
		map.put("Most recent fabrication year",
				findMostRecentFabricationDate(announcements).toString().substring(0, 4));
		map.put("Average fabrication year", Integer.toString(computeAverageFabricationDate(announcements)));
		map.put("Oldest fabrication year", findOldestFabricationDate(announcements).toString().substring(0, 4));
		return map;
	}

	public static Map<String, Integer> getMakeStatistics(List<Announcement> announcements) {
		Map<String, Integer> map = new LinkedHashMap<>();
		for (String make : AnnouncementDto.CAR_MAKES) {
			map.put(make, 0);
		}
		for (Announcement announcement : announcements) {
			map.replace(announcement.getMake(), map.get(announcement.getMake()) + 1);
		}
		return sortByValue(map);
	}

	public static Map<String, Integer> getCountyStatistics(List<Announcement> announcements) {
		Map<String, Integer> map = new LinkedHashMap<>();
		for (String county : AnnouncementDto.COUNTIES) {
			map.put(county, 0);
		}
		for (Announcement announcement : announcements) {
			map.replace(announcement.getCounty(), map.get(announcement.getCounty()) + 1);
		}
		return sortByValue(map);
	}

	public static Map<String, Integer> getFuelStatistics(List<Announcement> announcements) {
		Map<String, Integer> map = new LinkedHashMap<>();
		for (String fuel : AnnouncementDto.FUELS) {
			map.put(fuel, 0);
		}
		for (Announcement announcement : announcements) {
			if (!announcement.getFuel().isEmpty()) {
				map.replace(announcement.getFuel(), map.get(announcement.getFuel()) + 1);
			}
		}
		return sortByValue(map);
	}

	public static Map<String, Integer> getBodyStatistics(List<Announcement> announcements) {
		Map<String, Integer> map = new LinkedHashMap<>();
		for (String body : AnnouncementDto.BODIES) {
			map.put(body, 0);
		}
		for (Announcement announcement : announcements) {
			if (!announcement.getBody().isEmpty()) {
				map.replace(announcement.getBody(), map.get(announcement.getBody()) + 1);
			}
		}
		return sortByValue(map);
	}

	public static Map<String, Integer> getGearboxStatistics(List<Announcement> announcements) {
		Map<String, Integer> map = new LinkedHashMap<>();
		for (String gearbox : AnnouncementDto.GEARBOXES) {
			map.put(gearbox, 0);
		}
		for (Announcement announcement : announcements) {
			if (!announcement.getGearbox().isEmpty()) {
				map.replace(announcement.getGearbox(), map.get(announcement.getGearbox()) + 1);
			}
		}
		return sortByValue(map);
	}

	public static Map<String, Integer> getPowertrainStatistics(List<Announcement> announcements) {
		Map<String, Integer> map = new LinkedHashMap<>();
		for (String powertrain : AnnouncementDto.POWERTRAINS) {
			map.put(powertrain, 0);
		}
		for (Announcement announcement : announcements) {
			if (!announcement.getPowertrain().isEmpty()) {
				map.replace(announcement.getPowertrain(), map.get(announcement.getPowertrain()) + 1);
			}
		}
		return sortByValue(map);
	}

	public static Map<String, Integer> getEmissionsStatistics(List<Announcement> announcements) {
		Map<String, Integer> map = new LinkedHashMap<>();
		for (String emission : AnnouncementDto.EMISSIONS) {
			map.put(emission, 0);
		}
		for (Announcement announcement : announcements) {
			if (!announcement.getEmissions().isEmpty()) {
				map.replace(announcement.getEmissions(), map.get(announcement.getEmissions()) + 1);
			}
		}
		return sortByValue(map);
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
		Collections.reverse(list); // to be in the order declared in the list from AnnouncementDto
		list.sort(Entry.comparingByValue());
		Collections.reverse(list); // to sort descending by value

		Map<K, V> result = new LinkedHashMap<>();
		for (Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	public static int findNumberOfPromotedAnnouncements(List<Announcement> announcements) {
		int number = 0;
		for (Announcement announcement : announcements) {
			if (announcement.isPromoted()) {
				number++;
			}
		}
		return number;
	}

	public static float findHighestPrice(List<Announcement> announcements) {
		float max = 0;
		for (Announcement announcement : announcements) {
			if (announcement.getPrice() > max) {
				max = announcement.getPrice();
			}
		}
		return max;
	}

	public static float computeAveragePrice(List<Announcement> announcements) {
		float average = 0;
		for (Announcement announcement : announcements) {
			average += announcement.getPrice();
		}
		return average / (float) announcements.size();
	}

	public static float findLowestPrice(List<Announcement> announcements) {
		float min = Float.MAX_VALUE;
		for (Announcement announcement : announcements) {
			if (announcement.getPrice() < min) {
				min = announcement.getPrice();
			}
		}
		return min;
	}

	public static YearMonth findMostRecentFabricationDate(List<Announcement> announcements) {
		YearMonth mostRecentFabricationDate = YearMonth.of(1900, 1);
		for (Announcement announcement : announcements) {
			if (announcement.getFabricationDate().compareTo(mostRecentFabricationDate) > 0) {
				mostRecentFabricationDate = announcement.getFabricationDate();
			}
		}
		return mostRecentFabricationDate;
	}

	public static int computeAverageFabricationDate(List<Announcement> announcements) {
		int average = 0;
		for (Announcement announcement : announcements) {
			average += announcement.getFabricationDate().getYear();
		}
		average = announcements.isEmpty() ? average : average / announcements.size();
		return average;
	}

	public static YearMonth findOldestFabricationDate(List<Announcement> announcements) {
		YearMonth oldestFabricationDate = YearMonth.now();
		for (Announcement announcement : announcements) {
			if (announcement.getFabricationDate().compareTo(oldestFabricationDate) < 0) {
				oldestFabricationDate = announcement.getFabricationDate();
			}
		}
		return oldestFabricationDate;
	}

}
