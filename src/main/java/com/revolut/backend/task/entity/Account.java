package com.revolut.backend.task.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

/**
 * @author sanaparveen
 * Account Entity Class for account information.
 */
@Entity
@Table(name = "account")
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Account {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accountId;

	@Column(nullable = false, scale = 2)
	private BigDecimal balance;

	public Account() {

	}

	public Account(long accountId, BigDecimal balance) {
		this.accountId = accountId;
		this.balance = balance;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal amount) {
		this.balance = amount;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance + "]";
	}	

}
