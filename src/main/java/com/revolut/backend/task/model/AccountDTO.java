package com.revolut.backend.task.model;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sanaparveen
 *
 */
public class AccountDTO {

	@JsonProperty
	private long accountId;

	@JsonProperty
	@DecimalMin(value = "0.0")
	@NotNull
	private BigDecimal balance;

	public AccountDTO() {
	}

	public AccountDTO(BigDecimal balance) {
		this.balance = balance;
	}

	public AccountDTO(long accountId, BigDecimal balance) {
		this.accountId = accountId;
		this.balance = balance;
	}

	public long getId() {
		return accountId;
	}

	public void setId(long accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "AccountDTO{" + "accountId=" + accountId + ", balance=" + balance + '}';
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AccountDTO)) {
			return false;
		}

		AccountDTO that = (AccountDTO) object;
		return (getId() == that.getId()) && getBalance().equals(that.getBalance());

	}

}
