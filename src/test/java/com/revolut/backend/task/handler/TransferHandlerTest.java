package com.revolut.backend.task.handler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revolut.backend.task.repository.AccountRepository;
import com.revolut.backend.task.repository.TransactionRepository;
import com.revolut.backend.task.repository.impl.AccountRepositoryImpl;
import com.revolut.backend.task.repository.impl.TransactionRepositoryImpl;
import com.revolut.backend.task.service.AccountService;
import com.revolut.backend.task.service.TransactionService;
import com.revolut.backend.task.service.impl.AccountServiceImpl;
import com.revolut.backend.task.service.impl.TransactionServiceImpl;

public class TransferHandlerTest {

	private TransferHandler transferHandler;

	private Injector injector;

	@Before
	public void setUp() throws Exception {
		injector = Guice.createInjector(new AbstractModule() {
			protected void configure() {
				bind(TransactionRepository.class).to(TransactionRepositoryImpl.class);
				bind(AccountRepository.class).to(AccountRepositoryImpl.class);
				bind(AccountService.class).to(AccountServiceImpl.class);
				bind(TransactionService.class).to(TransactionServiceImpl.class);
			}
		});
		MockitoAnnotations.initMocks(TransferHandlerTest.class);
		transferHandler = injector.getInstance(TransferHandler.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowErrorWhenHandleMethodCalledWithContextAsNull() {
		transferHandler.handle(null);

	}

}
