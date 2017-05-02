package com.tr.biton.orm;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("directtype")
@Table(name="directpayments")
public class DirectPayment extends Payment {
	
	@Column(name="directaddress")
	private String directaddress;

	public String getDirectaddress() {
		return directaddress;
	}

	public void setDirectaddress(String directaddress) {
		this.directaddress = directaddress;
	}
	
	

}
