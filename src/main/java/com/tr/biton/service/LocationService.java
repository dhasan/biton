package com.tr.biton.service;

import java.util.List;
import java.util.Map;

import com.tr.biton.orm.Location;

public interface LocationService {
	public List<Location> getLocations(Integer first, Integer count, Map<String, Object> args);
	public Long getLocations_count(Map<String, Object> args);
}
