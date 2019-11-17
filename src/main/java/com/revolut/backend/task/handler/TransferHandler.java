package com.revolut.backend.task.handler;

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
				TransactionDTO transaction = transactionService
						.transferAmount(context.bodyAsClass(TransactionDTO.class));
				logger.info("Transaction ID: {}", transaction.getTransactionId());
				context.json(transaction);
				context.status(HttpStatus.CREATED_201);
			} catch (RevolutAPIException e) {
				logger.error("Error in Request Processiing: {}", e);
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
