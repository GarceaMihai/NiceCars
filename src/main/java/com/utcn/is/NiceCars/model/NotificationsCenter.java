package com.utcn.is.NiceCars.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NotificationsCenter implements Observer {
	public static Map<String, Long> messages = new HashMap<>();

	@Override
	public void update(Object id) {
		while (messages.containsValue((Long) id)) {
			messages.values().remove((Long) id);
		}
	}

	public List<String> getKeysByValue(Long value) {
		List<String> keys = new ArrayList<>();
		for (Entry<String, Long> entry : messages.entrySet()) {
			if (value == entry.getValue()) {
				keys.add(entry.getKey());
			}
		}
		return keys;
	}
}
