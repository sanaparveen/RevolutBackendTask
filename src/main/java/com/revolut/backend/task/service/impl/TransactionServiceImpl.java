/**
 * 
 */
package com.revolut.backend.task.service.impl;

import java.util.Optional;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.revolut.backend.task.entity.Account;
import com.revolut.backend.task.entity.Transaction;
import com.revolut.backend.task.exception.AccountException;
import com.revolut.backend.task.exception.InsufficientBalanceException;
import com.revolut.backend.task.exception.RevolutAPIException;
import com.revolut.backend.task.exception.TransactionException;
import com.revolut.backend.task.model.TransactionDTO;
import com.revolut.backend.task.repository.TransactionRepository;
import com.revolut.backend.task.service.AccountService;
import com.revolut.backend.task.service.TransactionService;
import com.revolut.backend.task.utility.APIErrorBuilder;
import com.revolut.backend.task.utility.Constants;
import com.revolut.backend.task.utility.Converter;
import com.revolut.backend.task.utility.TransactionUtil;

/**
 * @author sanaparveen
 *
 */
public class TransactionServiceImpl implements TransactionService {

	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	private TransactionRepository transactionRepository;
	private final TransactionUtil transactionUtil = new TransactionUtil();
	private AccountService accountService;

	@Inject
	public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService) {
		this.transactionRepository = transactionRepository;
		this.accountService = accountService;
	}

	public TransactionDTO transferAmount(TransactionDTO transactionDTO) throws RevolutAPIException {
		Transaction transaction = Converter.convertToTransactionEntity(transactionDTO);
		try {

			transactionUtil.validateRequest(transaction);
			Optional<Account> sender = accountService.getAccountDetail(transaction.getFromAccountId());
			Optional<Account> receiver = accountService.getAccountDetail(transaction.getToAccountId());

			transactionUtil.validateAccounts(sender, receiver);


			return this.transfer(transaction, sender.get(), receiver.get());
		} catch (TransactionException | AccountException e) {
			throw new RevolutAPIException(APIErrorBuilder.buildAPIError(Constants.TRANSACTION_FAILED_CODE,
					Constants.TRANSACTION_FAILED_MESSAGE, HttpStatus.BAD_REQUEST_400));
		}
	}

	/*
	 * 
	 * Account Lock prevents deadlock when two thread are trying to update bank
	 * accounts of similar opposite transaction.
	 */
	private TransactionDTO transfer(Transaction transaction, Account sender, Account receiver)
			throws TransactionException, AccountException, InsufficientBalanceException {

		logger.info("Sender Balance Before withdraw: {},  Receiver Balance Before Deposit: {}", sender.getBalance(),
				receiver.getBalance());

		/**
		 * Lock the account which has lower id to acquire locks in same order every
		 * time.
		 */
		Account lock1 = sender.getAccountId() < receiver.getAccountId() ? sender : receiver;
		Account lock2 = sender.getAccountId() < receiver.getAccountId() ? receiver : sender;

		synchronized (lock1) {
			synchronized (lock2) {

				transactionUtil.validateAmount(transaction, sender);
				Account withdrawAccount = accountService.withdraw(sender, transaction.getAmount());
				Account depositAccount = accountService.deposit(receiver, transaction.getAmount());
				logger.info("Sender Balance After withdraw: {},  Receiver Balance After Deposit: {}",
						withdrawAccount.getBalance(), depositAccount.getBalance());
				Transaction transactionVal = transactionRepository.insert(transaction);

				return Converter.convertToTransactionDto(transactionVal);
			}
		}
	}
}
