package com.tr.biton.orm;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
enum PAYFOR {
	ITEMS,
	CARGO,
	FEE
}
public class Order {
	
	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	int	id;
	
	@ManyToMany(mappedBy="orders")
	private List<User> users;
	
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
	@CollectionTable(name = "order_requestedpayments")
    @MapKey(name = "payfor")
    @MapKeyEnumerated
    private Map<PAYFOR, Long> requestedpayments;

	@OneToOne(mappedBy="order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Shipment shipment;
	
	@OneToMany(mappedBy="order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderMessage> ordermessages;
}
