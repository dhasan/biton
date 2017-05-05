package com.tr.biton.service;

import java.util.List;

import com.tr.biton.orm.Location;

public interface LocationService {
	public List<Location> getLocations(int first, int count);
	public Long getLocations_count();
}
