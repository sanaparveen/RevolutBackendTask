/**
 * 
 */
package com.revolut.backend.task.repository;

import java.util.Optional;

import com.revolut.backend.task.entity.Account;
import com.revolut.backend.task.exception.AccountException;

/**
 * @author sanaparveen
 *
 */
public interface AccountRepository {

	Optional<Account> findById(Long id) throws AccountException;

	Account insert(Account account) throws AccountException;

	Account update(Account account) throws AccountException;
}
