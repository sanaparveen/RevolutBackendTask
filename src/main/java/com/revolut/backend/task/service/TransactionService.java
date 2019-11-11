/**
 * 
 */
package com.revolut.backend.task.service;

import com.revolut.backend.task.exception.RevolutAPIException;
import com.revolut.backend.task.model.TransactionDTO;

/**
 * @author sanaparveen
 *
 */
public interface TransactionService {

	public TransactionDTO transferAmount(TransactionDTO transactionDTO) throws RevolutAPIException;
}
