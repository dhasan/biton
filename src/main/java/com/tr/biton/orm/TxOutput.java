package com.tr.biton.orm;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

enum TxOutputType {
	PAYTOADDRESS,
	PAYTOSCRIPT
}

@Entity
@Table(name = "TXOUTPUTS")
public class TxOutput {
	
	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	int	id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Transaction transaction;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="intx_id") 
	private TxInput intx;
	
	@Enumerated(EnumType.ORDINAL)
	private TxOutputType txoutputtype;
	
	@Column(name="toAddress")
	private String toAddress;
	
	@Column(name="satoshis")
	private long satoshis;
	
	@Column(name="n")
	private int n;
	
	@Column(name="lockscript")
	private byte[] lockscript;
	
	@Column(name="spend")
	private boolean spend;

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

	public TxInput getIntx() {
		return intx;
	}

	public void setIntx(TxInput intx) {
		this.intx = intx;
	}

	public TxOutputType getTxoutputtype() {
		return txoutputtype;
	}

	public void setTxoutputtype(TxOutputType txoutputtype) {
		this.txoutputtype = txoutputtype;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public long getSatoshis() {
		return satoshis;
	}

	public void setSatoshis(long satoshis) {
		this.satoshis = satoshis;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public byte[] getLockscript() {
		return lockscript;
	}

	public void setLockscript(byte[] lockscript) {
		this.lockscript = lockscript;
	}

	public boolean isSpend() {
		return spend;
	}

	public void setSpend(boolean spend) {
		this.spend = spend;
	}
	
	

}
