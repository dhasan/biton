package com.tr.biton.orm;

import java.sql.Blob;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Length;

public class Product {
	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	int	id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="name")
	@Length(max=128)
	private String name;
	
	@Column(name="name")
	@Length(max=2048)
	private String description;
	
	@ElementCollection
	@CollectionTable(name = "product_options")
	private List<String> options; //sizes, colors
	
	@ElementCollection
	@CollectionTable(name = "product_volumes")
	private List<Double> volumes; //volume per option
	
	@Lob
	@ElementCollection
	@CollectionTable(name = "product_images",
			joinColumns=@JoinColumn(name = "product_id", columnDefinition="mediumblob"))
	private List<Blob> images;

	
	@Column(name="singleprice")
	private long singleprice;
	
	@Column(name="singleprice")
	private int volumefractpart;
	
	@ManyToMany(mappedBy="products")
	private List<Order> orders;
	
	@OneToMany(mappedBy="product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProductMessage> productmessages;
	
	
}
