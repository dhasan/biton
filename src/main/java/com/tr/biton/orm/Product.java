package com.tr.biton.orm;

import java.sql.Blob;
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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import javax.persistence.Table;


@Entity
@Table(name="PRODUCTS")
public class Product {
	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	int	id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="name")
	//@Length(max=128)
	private String name;
	
	@Column(name="description")
	//@Length(max=2048)
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

	@Lob
	@Column(name="thumbimage", columnDefinition="mediumblob")
	private Blob thumbimage;
	
	@Column(name="singleprice")
	private long singleprice;
	
	@Column(name="volumefractpart")
	private int volumefractpart;
	
	@ManyToMany(mappedBy="products", cascade = CascadeType.ALL)
	private List<Order> orders;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="product_parcel")
	@MapKeyColumn(name="amountlimits")
	private Map<Double, Parcel> parcels;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="subcategory_id")
	private Subcategory subcategory;
	
	@Column(name="isused")
	private boolean used;
	
	@Column(name="brandname")
	private String brandname;
	
	
	
	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public Blob getThumbimage() {
		return thumbimage;
	}

	public void setThumbimage(Blob thumbimage) {
		this.thumbimage = thumbimage;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public Map<Double, Parcel> getParcels() {
		return parcels;
	}

	public void setParcels(Map<Double, Parcel> parcels) {
		this.parcels = parcels;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public List<Double> getVolumes() {
		return volumes;
	}

	public void setVolumes(List<Double> volumes) {
		this.volumes = volumes;
	}

	public List<Blob> getImages() {
		return images;
	}

	public void setImages(List<Blob> images) {
		this.images = images;
	}

	public long getSingleprice() {
		return singleprice;
	}

	public void setSingleprice(long singleprice) {
		this.singleprice = singleprice;
	}

	public int getVolumefractpart() {
		return volumefractpart;
	}

	public void setVolumefractpart(int volumefractpart) {
		this.volumefractpart = volumefractpart;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
		
	
}
