package com.tr.biton.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="locations")
public class Location {
	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	int	id;
	
	@Size(max=2)
	@Column(name="iso")
	private String iso;
	
	@Size(max=3)
	@Column(name="iso3")
	private String iso3;
	
	@Column(name="name")
	private String name;
	
	@Column(name="nicename")
	private String nicename;
	
	@Column(name="numcode")
	private int numcode;
	
	@Column(name="phonecode")
	private int phonecode;
	
	@Column(name="continent")
	private String continent;

	
	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getIso3() {
		return iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNicename() {
		return nicename;
	}

	public void setNicename(String nicename) {
		this.nicename = nicename;
	}

	public int getNumcode() {
		return numcode;
	}

	public void setNumcode(int numcode) {
		this.numcode = numcode;
	}

	public int getPhonecode() {
		return phonecode;
	}

	public void setPhonecode(int phonecode) {
		this.phonecode = phonecode;
	}
	
	
}
