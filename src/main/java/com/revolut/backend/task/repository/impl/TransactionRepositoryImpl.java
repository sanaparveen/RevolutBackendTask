/**
 * 
 */
package com.revolut.backend.task.repository.impl;

import java.math.BigDecimal;
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
		logger.debug("Find By Transaction Id: {}", id);
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

	/**
	 * This method is used to transfer amount from one account to another. The
	 * transaction is committed only when the complete transaction is completed
	 * other wise incase of any exception the transaction is rolled back.
	 */
	@Override
	public Transaction transfer(Transaction transaction, Account sender, Account receiver) throws TransactionException {
		logger.debug("Starting Transaction from Account Id: {} to Account Id: {}", sender.getAccountId(),
				receiver.getAccountId());
		Connection conn = getCurrentConnection();

		try {
			conn.setAutoCommit(false);
			Account withdrawAccount = this.withdrawAmount(conn, sender, transaction.getAmount());
			Account depositAccount = this.depositAmount(conn, receiver, transaction.getAmount());

			logger.info("Sender Balance After withdraw: {},  Receiver Balance After Deposit: {}",
					withdrawAccount.getBalance(), depositAccount.getBalance());

			this.insertTransaction(conn, transaction);
			conn.commit();
		} catch (SQLException | AccountException | TransactionException e) {
			try {
				conn.rollback();
			} catch (Exception rollbackException) {
				throw new TransactionException("Cannot rollback account update transaction: " + rollbackException);
			}
			logger.error("Error in Transaction to Proceed: {} ", e);
			throw new TransactionException("Error in Transaction: " + e);
		} finally {
			close(conn);
		}
		return transaction;
	}

	private Account withdrawAmount(Connection conn, Account account, BigDecimal transactionAmount)
			throws AccountException {
		BigDecimal newBalance = account.getBalance().subtract(transactionAmount);
		account.setBalance(newBalance);
		updateAccount(conn, account);
		return account;
	}

	private Account depositAmount(Connection conn, Account account, BigDecimal transactionAmount)
			throws AccountException {
		BigDecimal newBalance = account.getBalance().add(transactionAmount);
		account.setBalance(newBalance);
		updateAccount(conn, account);
		return account;
	}

	private void updateAccount(Connection conn, Account account) throws AccountException {

		try (PreparedStatement insertPS = conn.prepareStatement("UPDATE account set balance = ? WHERE accountId = ?")) {
			insertPS.setBigDecimal(1, account.getBalance());
			insertPS.setLong(2, account.getAccountId());
			insertPS.executeUpdate();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception rollbackException) {
				throw new AccountException("Cannot rollback account update transaction" + rollbackException);
			}
			throw new AccountException("Error in account update transaction" + e);
		}
	}

	private Transaction insertTransaction(Connection conn, Transaction transaction)
			throws SQLException, TransactionException {
		ResultSet insertRS = null;
		try (PreparedStatement insertPS = conn.prepareStatement(
				"INSERT INTO transaction(fromAccountId, toAccountId, amount) VALUES (?,?,?)",
				Statement.RETURN_GENERATED_KEYS)) {
			insertPS.setLong(1, transaction.getFromAccountId());
			insertPS.setLong(2, transaction.getToAccountId());
			insertPS.setBigDecimal(3, transaction.getAmount());
			insertPS.executeUpdate();
			insertRS = insertPS.getGeneratedKeys();
			if (insertRS.next()) {
				transaction.setTransactionId(insertRS.getLong(1));
				logger.debug("Transaction ID: {} ", transaction.getTransactionId());
			} else {
				throw new TransactionException("Cannot register the transfer.");
			}
		} finally {
			close(insertRS);
		}
		return transaction;
	}

}
