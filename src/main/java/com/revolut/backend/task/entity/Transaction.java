/**
 * 
 */
package com.revolut.backend.task.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author sanaparveen
 *
 */
@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transactionId;

	@Column(nullable = false)
	private long fromAccountId;

	@Column(nullable = false)
	private long toAccountId;

	@Column(scale = 2)
	private BigDecimal amount;

	public Transaction() {

	}

	public Transaction(long fromAccountId, long toAccountId, BigDecimal amount) {
		super();
		this.toAccountId = toAccountId;
		this.fromAccountId = fromAccountId;
		this.amount = amount;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(Long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public Long getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(Long toAccountId) {
		this.toAccountId = toAccountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", toAccountId=" + toAccountId + ", fromAccountId="
				+ fromAccountId + ", amount=" + amount + "]";
	}

}
