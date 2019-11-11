package com.revolut.backend.task.handler;

import java.util.Optional;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.revolut.backend.task.exception.RevolutAPIException;
import com.revolut.backend.task.model.TransactionDTO;
import com.revolut.backend.task.service.TransactionService;

import io.javalin.Context;
import io.javalin.Handler;

/**
 * @author sanaparveen
 *
 */
public class TransferHandler implements Handler {

	private static final Logger logger = LoggerFactory.getLogger(TransferHandler.class);
	private TransactionService transactionService;

	@Inject
	public TransferHandler(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@Override
	public void handle(Context context) {

		logger.info("Transfer Handler starting Execution with Request Body: {}", context);

		if (this.isContextValid(context)) {
			try {
				TransactionDTO transactionDTO = context.bodyAsClass(TransactionDTO.class);
				Optional<TransactionDTO> transaction = transactionService.transferAmount(transactionDTO);
				logger.info("Transaction ID: {}", transaction.get().getTransactionId());
				context.status(HttpStatus.NO_CONTENT_204);
			} catch (RevolutAPIException e) {
				logger.info("Error in Request Processiing: {}", e.getApiError());
				context.json(e.getApiError());
				context.status(HttpStatus.BAD_REQUEST_400);
			}
		} else {
			throw new IllegalArgumentException("Rest API Context was found to be Empty");
		}

	}

	private boolean isContextValid(Context context) {
		return context == null ? false : true;
	}

}
