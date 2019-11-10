package com.revolut.backend.task.model;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sanaparveen
 *
 */
public class TransactionDTO {

	@JsonProperty
	private long transactionId;

	@JsonProperty
	@Min(value = 0L)
	@NotNull
	private long fromAccountId;

	@JsonProperty
	@Min(value = 0L)
	@NotNull
	private long toAccountId;

	@JsonProperty
	@DecimalMin(value = "0.0")
	@NotNull
	private BigDecimal amount;


	public TransactionDTO() {

	}

	public TransactionDTO(long fromAccountId, long toAccountId, BigDecimal amount) {
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amount = amount;
	}

	public TransactionDTO(long transactionId, long fromAccountId, long toAccountId, BigDecimal amount) {
		this.transactionId = transactionId;
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amount = amount;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public long getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(long toAccountId) {
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
		return "TransactionDTO [fromAccountId=" + fromAccountId + ", toAccountId=" + toAccountId + ", amount=" + amount
				+ "]";
	}
}