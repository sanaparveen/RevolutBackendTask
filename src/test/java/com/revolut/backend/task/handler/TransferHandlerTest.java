package com.revolut.backend.task.handler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revolut.backend.task.exception.RevolutAPIException;
import com.revolut.backend.task.model.TransactionDTO;
import com.revolut.backend.task.service.TransactionService;
import com.revolut.backend.task.service.impl.TransactionServiceImpl;
import com.revolut.backend.task.utility.APIErrorBuilder;

import io.javalin.Context;

public class TransferHandlerTest {

	private TransferHandler transferHandler;
	@Mock
	private TransactionService transactionService;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(TransferHandlerTest.class);
		transactionService = Mockito.mock(TransactionServiceImpl.class);
		transferHandler = new TransferHandler(transactionService);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowErrorWhenHandleMethodCalledWithContextAsNull() throws RevolutAPIException {
		transferHandler.handle(null);
		Mockito.verify(transactionService, times(0)).transferAmount(null);
	}

	@Test
	public void shouldRunHandleContext() throws RevolutAPIException {
		Context context = mock(Context.class);
		TransactionDTO transactionDTO = mock(TransactionDTO.class);
		transactionDTO.setTransactionId(2l);
		Mockito.when(context.bodyAsClass(TransactionDTO.class)).thenReturn(Mockito.any());
		Mockito.when(transactionService.transferAmount(transactionDTO)).thenReturn(transactionDTO);
		transferHandler.handle(context);
		Mockito.verify(transactionService, times(1)).transferAmount(null);
		Mockito.verify(context, times(1)).status(HttpStatus.NO_CONTENT_204);
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowRuntimeExceptionWhenHandle() throws RevolutAPIException {
		transactionService = Mockito.mock(TransactionServiceImpl.class);
		transferHandler = new TransferHandler(transactionService);
		Context context = mock(Context.class);
		TransactionDTO transactionDTO = mock(TransactionDTO.class);
		transactionDTO.setTransactionId(2l);
		Mockito.when(context.bodyAsClass(TransactionDTO.class)).thenThrow(new RuntimeException());
		Mockito.when(transactionService.transferAmount(Mockito.any()))
			.thenThrow(new RevolutAPIException(APIErrorBuilder.buildAPIError("code", "message", 1)));
		transferHandler.handle(context);
	}
}
