package com.tr.biton.orm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name	= "TRANSACTIONS")
public class Transaction {
	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	int	id;
	
	@Column(name="hash")
	private byte[] hash;
	
	@ManyToMany(mappedBy="transactions")
	private List<Wallet> wallets;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date date;
	
	@OneToMany(mappedBy="transaction")
	private List<TxInput> inputs;
	
	@OneToMany(mappedBy="transaction")
	private List<TxOutput> outputs;
	
	@Column(name="conformations")
	private int conformations;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getHash() {
		return hash;
	}

	public void setHash(byte[] hash) {
		this.hash = hash;
	}

	public List<TxInput> getInputs() {
		return inputs;
	}

	public void setInputs(List<TxInput> inputs) {
		this.inputs = inputs;
	}

	public List<TxOutput> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<TxOutput> outputs) {
		this.outputs = outputs;
	}

	public int getConformations() {
		return conformations;
	}

	public void setConformations(int conformations) {
		this.conformations = conformations;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
