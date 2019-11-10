/**
 * 
 */
package com.revolut.backend.task.utility;

import java.math.BigDecimal;
import java.util.Optional;

import org.eclipse.jetty.http.HttpStatus;

import com.revolut.backend.task.entity.Account;
import com.revolut.backend.task.entity.Transaction;
import com.revolut.backend.task.exception.AccountException;
import com.revolut.backend.task.exception.AccountNotFoundException;
import com.revolut.backend.task.exception.InsufficientBalanceException;
import com.revolut.backend.task.exception.InvalidAccountException;
import com.revolut.backend.task.exception.InvalidTransferAmountException;

/**
 * @author sanaparveen
 *
 */
public class TransactionUtil {

	public void validateRequest(Transaction transaction)
			throws InvalidTransferAmountException, InvalidAccountException, AccountException {

		this.validateRequestAmount(transaction);
		this.validateAccountId(transaction);
	}

	public void validateAccounts(Optional<Account> sender, Optional<Account> receiver) throws AccountNotFoundException {

		this.checkAccountsExist(sender, Constants.TRANSACTION_FROM_ACCOUNT_ID_FIELD);
		this.checkAccountsExist(receiver, Constants.TRANSACTION_TO_ACCOUNT_ID_FIELD);

	}

	/**
	 * This method is used to validate account balance of sender to check sufficient
	 * amount exist to make transaction.
	 * 
	 * @param transaction
	 * @param sender
	 * @throws InsufficientBalanceException
	 */
	public void validateAmount(Transaction transaction, Account sender) throws InsufficientBalanceException {
		if (transaction.getAmount().compareTo(sender.getBalance()) > 0) {
			throw new InsufficientBalanceException(APIErrorBuilder.buildAPIError(
					Constants.TRANSACTION_INSUFFICIENT_AMOUNT_CODE, Constants.TRANSACTION_INSUFFICIENT_AMOUNT_MSG,
					Constants.TRANSACTION_AMOUNT_FIELD, HttpStatus.BAD_REQUEST_400));
		}
	}

	/**
	 * This method is used to check if the amount given is valid.
	 * 
	 * @param transaction
	 * @throws InvalidTransferAmountException
	 */
	private void validateRequestAmount(Transaction transaction) throws InvalidTransferAmountException {
		if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
			throw new InvalidTransferAmountException(APIErrorBuilder.buildAPIError(
					Constants.TRANSACTION_INVALID_AMOUNT_CODE, Constants.TRANSACTION_INVALID_AMOUNT_MSG,
					Constants.TRANSACTION_AMOUNT_FIELD, HttpStatus.BAD_REQUEST_400));
		}
	}

	/**
	 * This method is used to check if account given is valid and exist in database.
	 * 
	 * @param transaction
	 * @throws InvalidAccountException
	 * @throws AccountNotFoundException
	 */
	private void validateAccountId(Transaction transaction) throws InvalidAccountException {
		if (transaction.getFromAccountId().equals(transaction.getToAccountId())) {
			throw new InvalidAccountException(APIErrorBuilder.buildAPIError(
					Constants.TRANSACTION_INVALID_ACCOUNT_ID_CODE, Constants.TRANSACTION_INVALID_ACCOUNT_ID_MSG,
					Constants.TRANSACTION_TO_ACCOUNT_ID_FIELD, HttpStatus.BAD_REQUEST_400));
		}
	}

	/**
	 * This method is used to check if account exist in database
	 * 
	 * @param account
	 * @param field
	 * @throws AccountNotFoundException
	 */
	private void checkAccountsExist(Optional<Account> account, String field) throws AccountNotFoundException {
		if (account.isEmpty()) {
			throw new AccountNotFoundException(
					APIErrorBuilder.buildAPIError(Constants.TRANSACTION_ACCOUNT_NOT_FOUND_CODE,
							Constants.TRANSACTION_ACCOUNT_NOT_FOUND_MSG, field, HttpStatus.BAD_REQUEST_400));
		}
	}

}
