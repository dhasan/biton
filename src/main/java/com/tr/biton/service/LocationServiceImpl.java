package com.tr.biton.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.biton.dao.LocationDAO;
import com.tr.biton.orm.Location;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private	LocationDAO locationDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Location> getLocations(Integer start, Integer count, Map<String, Object> args) {
		return locationDAO.getLocations(start,count,args);
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public Long getLocations_count(Map args){
		return locationDAO.getLocations_count(args);
	}

	public LocationDAO getLocationDAO() {
		return locationDAO;
	}

	public void setLocationDAO(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}
	
	
	

}
