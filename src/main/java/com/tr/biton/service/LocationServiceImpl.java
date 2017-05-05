package com.tr.biton.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.tr.biton.dao.LocationDAO;
import com.tr.biton.orm.Location;

public class LocationServiceImpl implements LocationService {

	@Autowired
	private	LocationDAO locationDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Location> getLocations(int first, int count) {
		return locationDAO.getLocations(first,count);
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public Long getLocations_count(){
		return locationDAO.getLocations_count();
	}

	public LocationDAO getLocationDAO() {
		return locationDAO;
	}

	public void setLocationDAO(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}
	
	
	

}
