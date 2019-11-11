package com.revolut.backend.task.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sanaparveen
 *
 */
public class TransactionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + (int) (fromAccountId ^ (fromAccountId >>> 32));
		result = prime * result + (int) (toAccountId ^ (toAccountId >>> 32));
		result = prime * result + (int) (transactionId ^ (transactionId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionDTO other = (TransactionDTO) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (fromAccountId != other.fromAccountId)
			return false;
		if (toAccountId != other.toAccountId)
			return false;
		if (transactionId != other.transactionId)
			return false;
		return true;
	}

	
}