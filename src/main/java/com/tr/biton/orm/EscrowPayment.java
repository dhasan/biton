package com.tr.biton.orm;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

@Entity
@DiscriminatorValue("ESCROWTYPE")
@Table(name="ESCROWPAYMENT")
public class EscrowPayment extends Payment   {

	@Column(name="escrowaddress")
	public String escrowaddress;
	
	@Column(name="redeemscript")
	public byte[] redeemscript;
	
	@Column(name="escrowprvkey")
	@ColumnTransformer(
	          read="pgp_sym_decrypt(name::bytea, 'mySecretKey')", 
	          write="pgp_sym_encrypt(?, 'mySecretKey')")
	public byte[] escrowprvkey;
}
