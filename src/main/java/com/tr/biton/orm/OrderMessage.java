package com.tr.biton.orm;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


enum OrderStatus{
	PAYMENTRECEIVED,
	SHIPPED,
	RECEIVED,
	DIDNTRECEIVEDITEM,
	DIDNTRECEIVEPAYMENT
}

@Entity
@Table(name="ordermessages")
public class OrderMessage {

	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	int	id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="order_id")
	private Order order;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="orderstatus")
	private OrderStatus orderstatus;
	
	@Column(name="stars")
	private int stars;
	
	@Column(name="message")
	private String message;

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

	public OrderStatus getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(OrderStatus orderstatus) {
		this.orderstatus = orderstatus;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
