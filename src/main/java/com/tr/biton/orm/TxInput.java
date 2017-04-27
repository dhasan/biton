package com.tr.biton.orm;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

enum TxInputType {
	PAYFROMADDRESS,
	PAYFROMSCRIPT
}
@Entity
@Table(name	= "TXINPUTS")
public class TxInput {
	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	int	id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Transaction transaction;
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy= "intx")  
	private TxOutput outtx;
	
	@Enumerated(EnumType.ORDINAL)
	private TxInputType txinputtype;
	
	@Column(name = "fromaddress")
	private String fromAddress;
	
	@Column(name = "n")
	private int n;
	
	@Column(name = "unlockscript")
	private byte[] unlockscript;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public TxOutput getOuttx() {
		return outtx;
	}

	public void setOuttx(TxOutput outtx) {
		this.outtx = outtx;
	}

	public TxInputType getTxinputtype() {
		return txinputtype;
	}

	public void setTxinputtype(TxInputType txinputtype) {
		this.txinputtype = txinputtype;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public byte[] getUnlockscript() {
		return unlockscript;
	}

	public void setUnlockscript(byte[] unlockscript) {
		this.unlockscript = unlockscript;
	}
	
}
