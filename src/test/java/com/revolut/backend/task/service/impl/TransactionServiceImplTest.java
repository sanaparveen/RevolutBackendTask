package com.revolut.backend.task.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.revolut.backend.task.entity.Account;
import com.revolut.backend.task.entity.Transaction;
import com.revolut.backend.task.exception.AccountException;
import com.revolut.backend.task.exception.RevolutAPIException;
import com.revolut.backend.task.exception.TransactionException;
import com.revolut.backend.task.model.TransactionDTO;
import com.revolut.backend.task.repository.TransactionRepository;
import com.revolut.backend.task.repository.impl.TransactionRepositoryImpl;
import com.revolut.backend.task.service.AccountService;

public class TransactionServiceImplTest {

	private TransactionServiceImpl transactionServiceImpl;

	private TransactionRepository transactionRepository;
	private AccountService accountService;

	@Before
	public void setUp() throws Exception {
//		transactionServiceImpl = new TransactionServiceImpl();
		transactionRepository = Mockito.mock(TransactionRepositoryImpl.class);
		accountService = Mockito.mock(AccountServiceImpl.class);
	}

//	@Test
//	public void testTransferAmount() throws RevolutAPIException, AccountException, TransactionException {
//		Account account = new Account(1, new BigDecimal(2500));
//		Optional<Account> optionalAccount = Optional.of(account);
//		Mockito.when(accountService.getAccountDetail(Mockito.anyLong())).thenReturn(optionalAccount);
//		Mockito.when(accountService.withdraw(Mockito.any(), Mockito.any())).thenReturn(account);
//		Mockito.when(accountService.deposit(Mockito.any(), Mockito.any())).thenReturn(account);
//		Mockito.when(transactionRepository.insert(Mockito.any(Transaction.class)))
//				.thenReturn(Mockito.any(Transaction.class));
//		transactionServiceImpl.transferAmount(new TransactionDTO(1l, 2l, new BigDecimal(2500)));
//	}

}
