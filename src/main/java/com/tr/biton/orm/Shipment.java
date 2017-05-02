package com.tr.biton.orm;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="shipments")
public class Shipment {
	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	int	id;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="order_id")
	private Order order;
	
	@Column(name="shippoorderid")
	private String shippoorderid;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="fromaddress_id")
	private Address fromaddress;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="toaddress_id")
	private Address toaddress;
	
	@Column(name="trackingid")
	private String trackingid;
	
	@Column(name="trackingurl")
	private String trackingurl;
	
	@Column(name="lastlocation")
	private String lastlocation;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getShippoorderid() {
		return shippoorderid;
	}

	public void setShippoorderid(String shippoorderid) {
		this.shippoorderid = shippoorderid;
	}

	public Address getFromaddress() {
		return fromaddress;
	}

	public void setFromaddress(Address fromaddress) {
		this.fromaddress = fromaddress;
	}

	public Address getToaddress() {
		return toaddress;
	}

	public void setToaddress(Address toaddress) {
		this.toaddress = toaddress;
	}

	public String getTrackingid() {
		return trackingid;
	}

	public void setTrackingid(String trackingid) {
		this.trackingid = trackingid;
	}

	public String getTrackingurl() {
		return trackingurl;
	}

	public void setTrackingurl(String trackingurl) {
		this.trackingurl = trackingurl;
	}

	public String getLastlocation() {
		return lastlocation;
	}

	public void setLastlocation(String lastlocation) {
		this.lastlocation = lastlocation;
	}
	
	
}
