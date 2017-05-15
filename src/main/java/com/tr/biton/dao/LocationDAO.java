package com.tr.biton.dao;

import java.util.List;
import java.util.Map;

import com.tr.biton.orm.Location;

public interface LocationDAO {
	public List<Location> getLocations(Integer start, Integer count, Map<String, Object> args);
	public Long getLocations_count(Map<String, Object> args); 
}
