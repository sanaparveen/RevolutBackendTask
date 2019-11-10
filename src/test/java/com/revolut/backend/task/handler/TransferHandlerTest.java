package com.revolut.backend.task.handler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revolut.backend.task.service.TransactionService;

class TransferHandlerTest {

	@InjectMocks
	private TransferHandler transferHandler = new TransferHandler();

	@Mock
	private TransactionService transactionService;

	@Before
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(TransferHandlerTest.class);
		transactionService = Mockito.mock(TransactionService.class);
	}

	@Test(expected = IllegalArgumentException.class)
	void shouldThrowErrorWhenHandleMethodCalledWithContextAsNull() {
		transferHandler.handle(null);

	}

}
