/**
 * 
 */
package com.revolut.backend.task.service;

import java.util.Optional;

import com.revolut.backend.task.entity.Account;
import com.revolut.backend.task.exception.AccountException;
import com.revolut.backend.task.model.AccountDTO;

/**
 * @author sanaparveen
 *
 */
public interface AccountService {

	public Optional<Account> getAccountDetail(Long accountId) throws AccountException;

	public AccountDTO createAccount(AccountDTO accountDTO) throws AccountException;

}
