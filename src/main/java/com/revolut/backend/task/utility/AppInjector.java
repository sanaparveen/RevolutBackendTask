/**
 * 
 */
package com.revolut.backend.task.utility;

import com.google.inject.AbstractModule;
import com.revolut.backend.task.repository.AccountRepository;
import com.revolut.backend.task.repository.TransactionRepository;
import com.revolut.backend.task.repository.impl.AccountRepositoryImpl;
import com.revolut.backend.task.repository.impl.TransactionRepositoryImpl;
import com.revolut.backend.task.service.AccountService;
import com.revolut.backend.task.service.TransactionService;
import com.revolut.backend.task.service.impl.AccountServiceImpl;
import com.revolut.backend.task.service.impl.TransactionServiceImpl;

/**
 * @author sanaparveen
 *
 */
public class AppInjector extends AbstractModule {

	protected void configure() {
		bind(AccountRepository.class).to(AccountRepositoryImpl.class);
		bind(AccountService.class).to(AccountServiceImpl.class);
		bind(TransactionRepository.class).to(TransactionRepositoryImpl.class);
		bind(TransactionService.class).to(TransactionServiceImpl.class);
	}
}
