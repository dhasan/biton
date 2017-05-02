package com.tr.biton.orm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="payment_type")
@Table(name="PAYMENTS")
public abstract class Payment {
	
	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="payed")
	private boolean payed;
	
	@Column(name="timeoutdate")
	@Type(type="date")
	private Date timeoutdate;
	
	@OneToOne
	@JoinColumn(name="order_id")
	private Order order;
	
	@OneToMany
	@JoinTable(name = "payment_transaction",
	joinColumns = { 
			@JoinColumn(name = "payment_id") 
	},inverseJoinColumns = { 
			@JoinColumn(name = "transaction_id") 
	})
	private List<Transaction> transactions;
	
	@Column(name="receivedmoney")
	private long receivedmoney;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isPayed() {
		return payed;
	}

	public void setPayed(boolean payed) {
		this.payed = payed;
	}

	public Date getTimeoutdate() {
		return timeoutdate;
	}

	public void setTimeoutdate(Date timeoutdate) {
		this.timeoutdate = timeoutdate;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public long getReceivedmoney() {
		return receivedmoney;
	}

	public void setReceivedmoney(long receivedmoney) {
		this.receivedmoney = receivedmoney;
	}
	
	
	
}
