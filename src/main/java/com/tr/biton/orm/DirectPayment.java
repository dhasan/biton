package com.tr.biton.orm;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("DIRECTTYPE")
@Table(name="DIRECTPAYMENT")
public class DirectPayment extends Payment {
	
	@Column(name="directaddress")
	private String directaddress;

}
