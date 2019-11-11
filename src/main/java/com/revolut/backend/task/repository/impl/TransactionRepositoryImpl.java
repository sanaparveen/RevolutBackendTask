/**
 * 
 */
package com.revolut.backend.task.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.backend.task.config.DBConnectConfig;
import com.revolut.backend.task.entity.Account;
import com.revolut.backend.task.entity.Transaction;
import com.revolut.backend.task.exception.AccountException;
import com.revolut.backend.task.exception.TransactionException;
import com.revolut.backend.task.repository.AccountRepository;
import com.revolut.backend.task.repository.TransactionRepository;

/**
 * @author sanaparveen
 *
 */
public class TransactionRepositoryImpl extends DBConnectConfig implements TransactionRepository {

	Logger logger = LoggerFactory.getLogger(TransactionRepositoryImpl.class);
	AccountRepository accountDAO = new AccountRepositoryImpl();

	@Override
	public Transaction findById(Long id) throws TransactionException {
		logger.debug("Finding transfer " + id);
		Transaction transaction = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = getCurrentConnection();
		try {
			ps = conn.prepareStatement("SELECT * FROM transaction WHERE transactionId = ?");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				transaction = new Transaction();
				Optional<Account> optionalFromAccount = accountDAO.findById(rs.getLong("fromAccountId"));
				if (optionalFromAccount.isEmpty()) {
					throw new TransactionException("Can not find the from account");
				}
				Account from = optionalFromAccount.get();
				Optional<Account> optionalToAccount = accountDAO.findById(rs.getLong("toAccountId"));
				if (optionalToAccount.isEmpty()) {
					throw new TransactionException("Can not find the to account");
				}
				Account to = optionalToAccount.get();
				transaction.setToAccountId(to.getAccountId());
				transaction.setFromAccountId(from.getAccountId());
				transaction.setTransactionId(rs.getLong("transactionId"));
				transaction.setAmount(rs.getBigDecimal("amount"));
			}
		} catch (SQLException | AccountException e) {
			throw new TransactionException("An error occured while retrieving transfer by id: " + e);
		} finally {
			close(rs);
			close(ps);
			close(conn);
		}

		return transaction;
	}

	@Override
	public Transaction insert(Transaction transaction) throws TransactionException {
		logger.debug("Starting Transaction from Account Id: {} to Account Id: {}", transaction.getFromAccountId(),
				transaction.getToAccountId());

		ResultSet insertRS = null;
		PreparedStatement insertPS = null;
		Connection conn = getCurrentConnection();

		try {
			conn.setAutoCommit(false);

			insertPS = conn.prepareStatement(
					"INSERT INTO transaction(fromAccountId, toAccountId, amount) VALUES (?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			insertPS.setLong(1, transaction.getFromAccountId());
			insertPS.setLong(2, transaction.getToAccountId());
			insertPS.setBigDecimal(3, transaction.getAmount());
			insertPS.executeUpdate();
			insertRS = insertPS.getGeneratedKeys();

			if (insertRS.next()) {
				transaction.setTransactionId(insertRS.getLong(1));
				logger.debug("Transaction ID: {} ", transaction.getTransactionId());
			} else {
				throw new TransactionException("Can not register the transfer.");
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception rollbackException) {
				throw new TransactionException("Can not rollback account update transaction: " + rollbackException);
			}
			logger.error("Error in Transaction to Proceed: {} ", e);
			throw new TransactionException("Error in registering a transfer. " + e);
		} finally {
			close(insertPS);
			close(insertRS);
			close(conn);
		}
		return transaction;
	}

}
