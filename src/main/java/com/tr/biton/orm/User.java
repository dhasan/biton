package com.tr.biton.orm;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.validator.constraints.Email;

enum UserType {
	BUYER,
	SELLER,
	ESCROW,
	MASTER
}

public class User {

	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	int	id;
	
	@Email
	@Column(name="email", unique = true)
	private String email;
	
	@Column(name="fullnames")
	private String fullnames;
	
	@Size(min=8)
	@Column(name="password")
	@ColumnTransformer(
	          read="pgp_sym_decrypt(name::bytea, 'mySecretKey')", 
	          write="pgp_sym_encrypt(?, 'mySecretKey')")
	private String password;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="usertype")
	private UserType usertype;
	
	@Column(name="password")
	@ColumnTransformer(
	          read="pgp_sym_decrypt(name::bytea, 'mySecretKey')", 
	          write="pgp_sym_encrypt(?, 'mySecretKey')")
	private byte[] privkey;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "user_order",
		joinColumns = { 
				@JoinColumn(name = "user_id") 
		},inverseJoinColumns = { 
				@JoinColumn(name = "order_id") 
		})
	private List<Order> orders;
	
	@OneToMany(mappedBy="user")
	private List<Product> products; //for sellers
	
	@Column(name="localbalance")
	private long localbalance; 
	
	@Column(name="localbalance")
	private long lockedbalance; // after witdhraw request
}
