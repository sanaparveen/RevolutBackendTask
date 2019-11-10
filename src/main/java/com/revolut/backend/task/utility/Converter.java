/**
 * 
 */
package com.revolut.backend.task.utility;

import com.revolut.backend.task.entity.Account;
import com.revolut.backend.task.entity.Transaction;
import com.revolut.backend.task.model.AccountDTO;
import com.revolut.backend.task.model.TransactionDTO;

import io.javalin.validation.ConversionException;

/**
 * @author sanaparveen
 *
 */
public class Converter {

	public static TransactionDTO convertToTransactionDto(Transaction transaction) {
		if (transaction == null) {
			throw new ConversionException("Failed to Convert Transaction Entity to Transaction DTO");
		}
		return new TransactionDTO(transaction.getTransactionId(), transaction.getFromAccountId(),
				transaction.getToAccountId(), transaction.getAmount());
	}

	public static Transaction convertToTransactionEntity(TransactionDTO transactionDTO) {
		if (transactionDTO == null) {
			throw new ConversionException("Failed to Convert Transaction DTO to Transaction Entity");
		}
		return new Transaction(transactionDTO.getFromAccountId(), transactionDTO.getToAccountId(),
				transactionDTO.getAmount());
	}

	public static AccountDTO convertToAccountDto(Account account) {
		if (account == null) {
			new ConversionException("Failed to Convert Account Entity to Account DTO");
		}
		return new AccountDTO(account.getAccountId(), account.getBalance());
	}

	public static Account convertToAccountEntity(AccountDTO accountDTO) {
		if (accountDTO == null) {
			throw new ConversionException("Failed to Convert Account DTO to Account Entity");
		}
		return new Account(accountDTO.getId(), accountDTO.getBalance());
	}
}
