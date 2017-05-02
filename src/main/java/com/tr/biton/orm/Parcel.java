package com.tr.biton.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="parcels")
public class Parcel {
	
	public enum MassUnit {
		lb,
		kg,
		g
	}

	public enum DistanceUnit {
		in,
		m,
		sm
	}
	
	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	int	id;
	
	@Column(name="freeshipping")
	private boolean freeshipping;
	
	@Column(name="length")
	private double length;
	
	@Column(name="width")
	private double width;
	
	@Column(name="height")
	private double height;
	
	@Column(name="distance_unit")
	@Enumerated(EnumType.STRING)
	private DistanceUnit distance_unit;
	
	@Column(name="weight")
	private double weight;
	
	@Column(name="mass_unit")
	@Enumerated(EnumType.STRING)
	private MassUnit mass_unit;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isFreeshipping() {
		return freeshipping;
	}

	public void setFreeshipping(boolean freeshipping) {
		this.freeshipping = freeshipping;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public DistanceUnit getDistance_unit() {
		return distance_unit;
	}

	public void setDistance_unit(DistanceUnit distance_unit) {
		this.distance_unit = distance_unit;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public MassUnit getMass_unit() {
		return mass_unit;
	}

	public void setMass_unit(MassUnit mass_unit) {
		this.mass_unit = mass_unit;
	} 
	
	
}
