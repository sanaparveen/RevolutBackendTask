package com.revolut.backend.task.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

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
import com.revolut.backend.task.utility.Converter;

public class TransactionServiceImplTest {

	private TransactionServiceImpl transactionServiceImpl;

	private TransactionRepository transactionRepository;
	private AccountService accountService;

	@Before
	public void setUp() throws Exception {

		transactionRepository = Mockito.mock(TransactionRepositoryImpl.class);
		accountService = Mockito.mock(AccountServiceImpl.class);
		transactionServiceImpl = new TransactionServiceImpl(transactionRepository, accountService);
	}

	@Test
	public void testTransferAmount() throws RevolutAPIException, AccountException, TransactionException {
		Account account = new Account(1, new BigDecimal(2500));
		Optional<Account> optionalAccount = Optional.of(account);
		TransactionDTO transactionDTO = new TransactionDTO(1l, 2l, new BigDecimal(2500));
		Transaction transaction = Converter.convertToTransactionEntity(transactionDTO);
		Mockito.when(accountService.getAccountDetail(Mockito.anyLong())).thenReturn(optionalAccount);
		Mockito.when(transactionRepository.transfer(Mockito.any(Transaction.class), Mockito.any(), Mockito.any()))
				.thenReturn(transaction);
		TransactionDTO result = transactionServiceImpl.transferAmount(transactionDTO);
		assertEquals(result, transactionDTO);
		Mockito.verify(accountService, times(2)).getAccountDetail(Mockito.anyLong());
		Mockito.verify(transactionRepository, times(1)).transfer(Mockito.any(Transaction.class), Mockito.any(),
				Mockito.any());
	}

	@Test
	public void shouldNotTransferAmountWhenTransactionFails()
			throws RevolutAPIException, AccountException, TransactionException {
		Account account = new Account(1, new BigDecimal(300));
		Optional<Account> optionalAccount = Optional.of(account);
		TransactionDTO transactionDTO = new TransactionDTO(1l, 2l, new BigDecimal(200));
		Mockito.when(accountService.getAccountDetail(Mockito.anyLong())).thenReturn(optionalAccount);
		try {
			Mockito.when(transactionRepository.transfer(Mockito.any(Transaction.class), Mockito.any(), Mockito.any()))
					.thenThrow(new TransactionException("Can not register the transfer."));
			transactionServiceImpl.transferAmount(transactionDTO);
		} catch (RevolutAPIException e) {
			Mockito.verify(accountService, times(2)).getAccountDetail(Mockito.anyLong());
			Mockito.verify(transactionRepository, times(1)).transfer(Mockito.any(Transaction.class), Mockito.any(),
					Mockito.any());
			assertEquals("Failed to Transfer the Amount", e.getApiError().getMessage());
		}
	}

	@Test
	public void shouldNotTransferAmountWhenFailsToFindAccountDetails()
			throws RevolutAPIException, AccountException, TransactionException {
		TransactionDTO transactionDTO = new TransactionDTO(1l, 2l, new BigDecimal(200));
		Transaction transaction = Converter.convertToTransactionEntity(transactionDTO);

		Mockito.when(transactionRepository.transfer(Mockito.any(Transaction.class), Mockito.any(), Mockito.any()))
				.thenReturn(transaction);
		try {
			Mockito.when(accountService.getAccountDetail(Mockito.anyLong()))
					.thenThrow(new AccountException("Failed To Get Details of Account"));

			transactionServiceImpl.transferAmount(transactionDTO);
		} catch (RevolutAPIException e) {
			Mockito.verify(accountService, times(1)).getAccountDetail(Mockito.anyLong());

			Mockito.verify(transactionRepository, times(0)).transfer(Mockito.any(Transaction.class), Mockito.any(),
					Mockito.any());
			assertEquals("Failed to Transfer the Amount", e.getApiError().getMessage());
		}
	}

}
