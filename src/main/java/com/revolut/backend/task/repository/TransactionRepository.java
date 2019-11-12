package com.revolut.backend.task.repository;

import com.revolut.backend.task.entity.Account;
import com.revolut.backend.task.entity.Transaction;
import com.revolut.backend.task.exception.TransactionException;

public interface TransactionRepository {

	Transaction findById(Long id) throws TransactionException;

	Transaction transfer(Transaction transaction, Account sender, Account receiver) throws TransactionException;
}
