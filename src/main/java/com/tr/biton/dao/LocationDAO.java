package com.tr.biton.dao;

import java.util.List;

import com.tr.biton.orm.Location;

public interface LocationDAO {
	public List<Location> getLocations(int first, int count);
	public Long getLocations_count(); 
}
