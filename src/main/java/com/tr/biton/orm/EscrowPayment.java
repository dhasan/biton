package com.tr.biton.orm;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

@Entity
@DiscriminatorValue("escrowtype")
@Table(name="escrowpayments")
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
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="escrowuser")
	public User escrowuser;

	public String getEscrowaddress() {
		return escrowaddress;
	}

	public void setEscrowaddress(String escrowaddress) {
		this.escrowaddress = escrowaddress;
	}

	public byte[] getRedeemscript() {
		return redeemscript;
	}

	public void setRedeemscript(byte[] redeemscript) {
		this.redeemscript = redeemscript;
	}

	public byte[] getEscrowprvkey() {
		return escrowprvkey;
	}

	public void setEscrowprvkey(byte[] escrowprvkey) {
		this.escrowprvkey = escrowprvkey;
	}

	public User getEscrowuser() {
		return escrowuser;
	}

	public void setEscrowuser(User escrowuser) {
		this.escrowuser = escrowuser;
	}
	
	
}
