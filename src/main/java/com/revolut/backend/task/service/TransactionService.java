/**
 * 
 */
package com.revolut.backend.task.service;

import java.util.Optional;

import com.revolut.backend.task.exception.RevolutAPIException;
import com.revolut.backend.task.model.TransactionDTO;

/**
 * @author sanaparveen
 *
 */
public interface TransactionService {

	public Optional<TransactionDTO> transferAmount(TransactionDTO transactionDTO) throws RevolutAPIException;
}
