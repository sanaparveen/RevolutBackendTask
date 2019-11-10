package com.revolut.backend.task.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(TransferHandlerTest.class);
		transactionService = Mockito.mock(TransactionService.class);
	}

	@Test
	void shouldThrowErrorWhenHandleMethodCalledWithContextAsNull() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			transferHandler.handle(null);
		});
	}

}
