package com.tr.biton.orm;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

import com.tr.biton.app.BitCoin;
import com.tr.biton.app.Util;



@Entity
@Table(name="users")
public class User {
	
	public enum UserType {
		BUYER,
		SELLER,
		ESCROW,
		MASTER
	}
	
//	public User(){
//		privkey = new byte[256];
//	}

	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	int	id;
	
	//@Email
	@Column(name="email", unique = true)
	private String email;
	
	@Column(name="username")
	private String username;
	
//	@Size(min=8)
	@ColumnTransformer(
	          read="pgp_sym_decrypt(password::bytea, 'mySecretKey')", 
	          write="pgp_sym_encrypt(?, 'mySecretKey')")
	@Column(name="password"/*, columnDefinition="bytea"*/)
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name="usertype")
	private UserType usertype;
	
	
	@ColumnTransformer(
	          read="pgp_sym_decrypt(privkey::bytea, 'mySecretKey')", 
	          write="pgp_sym_encrypt(?, 'mySecretKey')")	
	@Column(name="privkey" /*, columnDefinition="bytea" */)
	private String privkey;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "user_order",
		joinColumns = { 
				@JoinColumn(name = "user_id") 
		},inverseJoinColumns = { 
				@JoinColumn(name = "order_id") 
		})
	private List<Order> orders;
	
	@OneToMany(mappedBy="user",  cascade = CascadeType.ALL)
	private List<Product> products; //for sellers
	
	
	@ColumnTransformer(
	          read="pgp_sym_decrypt(localbalance::bytea, 'mySecretKey')", 
	          write="pgp_sym_encrypt(?, 'mySecretKey')"
	          )
	@Column(name="localbalance"/*, columnDefinition="bytea"*/)
	private String localbalance;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="location_id")
	private Location location;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "user_shipsto",
	joinColumns = { 
			@JoinColumn(name = "user_id") 
	},inverseJoinColumns = { 
			@JoinColumn(name = "location_id") 
	})
	private Set<Location> shipsto;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "user_carriernames")
	private Set<String> carriernames;
	
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
	private List<Address> addresses;
	
	@Column(name="stars")
	private double stars;
	
	@Column(name="active")
	private boolean active;
	
	@Column(name="token") //Activation token
	private String token;
	
	
	

	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public double getStars() {
		return stars;
	}

	public void setStars(double stars) {
		this.stars = stars;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Set<Location> getShipsto() {
		return shipsto;
	}

	public void setShipsto(Set<Location> shipsto) {
		this.shipsto = shipsto;
	}

	public Set<String> getCarriernames() {
		return carriernames;
	}

	public void setCarriernames(Set<String> carriernames) {
		this.carriernames = carriernames;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUsertype() {
		return usertype;
	}

	public void setUsertype(UserType usertype) {
		this.usertype = usertype;
	}
	
	public String getPrivkey() {
		return privkey;
	}
	
	public byte[] getPrivkeyAsByteArray() {
		return Util.hexStringToByteArray(privkey);
	}

	public void setPrivkey(String privkey) {
		this.privkey = privkey;
	}
	
	public void setPrivkeyAsByteArray(byte[] privkey) {
		this.privkey = Util.byteArrayToHex(privkey);
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getLocalbalance() {
		return localbalance;
	}
	
	public long getLocalbalanceAsLong() {
		return Util.byteArrayToLong(Util.hexStringToByteArray(localbalance));
	}

	public void setLocalbalance(String localbalance) {
		this.localbalance = localbalance;
	} 
	
	public void setLocalbalanceAsLong(long localbalance) {
		this.localbalance = Util.byteArrayToHex(Util.longToByteArray(localbalance));
	} 
	
	
}
