/**
 * 
 */
package com.revolut.backend.task.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.revolut.backend.task.entity.Account;
import com.revolut.backend.task.exception.AccountException;
import com.revolut.backend.task.model.AccountDTO;
import com.revolut.backend.task.repository.AccountRepository;
import com.revolut.backend.task.service.AccountService;
import com.revolut.backend.task.utility.Converter;

/**
 * @author sanaparveen
 *
 */
@Singleton
public class AccountServiceImpl implements AccountService {

	private AccountRepository accountRepository;

	@Inject
	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public Optional<Account> getAccountDetail(Long accountId) throws AccountException {
		return accountRepository.findById(accountId);
	}

	@Override
	public AccountDTO createAccount(AccountDTO accountDTO) throws AccountException {
		Account account = accountRepository.insert(Converter.convertToAccountEntity(accountDTO));
		return Converter.convertToAccountDto(account);
	}

	@Override
	public Account withdraw(Account account, BigDecimal amount) throws AccountException {
		BigDecimal newBalance = account.getBalance().subtract(amount);
		account.setBalance(newBalance);

		return this.updateAccount(account);

	}

	@Override
	public Account deposit(Account account, BigDecimal amount) throws AccountException {
		BigDecimal newBalance = account.getBalance().add(amount);
		account.setBalance(newBalance);

		return this.updateAccount(account);
	}

	private Account updateAccount(Account account) throws AccountException {
		return accountRepository.update(account);
	}

}
