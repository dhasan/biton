package com.tr.biton.orm;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
enum PAYFOR {
	ITEMS,
	CARGO,
	FEE
}

@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	int	id;
	
	@ManyToMany(mappedBy="orders")
	private List<User> users;
	
	@Column(name="test")
	private String test;
	
	@OneToOne(mappedBy="order", cascade = CascadeType.ALL)
	private Payment payment;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "order_product",
		joinColumns = { 
				@JoinColumn(name = "order_id") 
		},inverseJoinColumns = { 
				@JoinColumn(name = "product_id") 
		})
	private List<Product> products;
	
	@ElementCollection
	@CollectionTable(name = "order_productvolumes")
	private List<Double> volumes;
	
	@ElementCollection
	@MapKeyEnumerated
    @CollectionTable(name="order_requestedpayments")
    private Map<PAYFOR, Long> requestedpayments;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Double> getVolumes() {
		return volumes;
	}

	public void setVolumes(List<Double> volumes) {
		this.volumes = volumes;
	}

	public Map<PAYFOR, Long> getRequestedpayments() {
		return requestedpayments;
	}

	public void setRequestedpayments(Map<PAYFOR, Long> requestedpayments) {
		this.requestedpayments = requestedpayments;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

//	@OneToOne(mappedBy="order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private Shipment shipment;
//	
	@OneToMany(mappedBy="order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderMessage> ordermessages;
	
	
}
