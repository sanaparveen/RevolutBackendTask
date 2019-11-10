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
import com.revolut.backend.task.exception.AccountException;
import com.revolut.backend.task.repository.AccountRepository;

/**
 * @author sanaparveen
 *
 */
public class AccountRepositoryImpl extends DBConnectConfig implements AccountRepository {

	Logger logger = LoggerFactory.getLogger(AccountRepositoryImpl.class);

	@Override
	public Optional<Account> findById(Long id) throws AccountException {
		logger.debug("Finding Account By ID: {}", id);

		Account account = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = getCurrentConnection();

		try {
			ps = conn.prepareStatement("SELECT * FROM account WHERE accountId = ?");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				account = new Account();
				account.setAccountId(rs.getLong("accountId"));
				account.setBalance(rs.getBigDecimal("balance"));
			}
		} catch (Exception e) {
			throw new AccountException("An error occure while retrieving the account " + id + "error: " + e);
		} finally {
			close(rs);
			close(ps);
		}
		return Optional.ofNullable(account);
	}

	@Override
	public Account insert(Account account) throws AccountException {
		logger.debug("Creating account " + account);
		PreparedStatement ps = null;
		Connection conn = getCurrentConnection();
		ResultSet insertedKeys = null;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("INSERT INTO account (balance) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			ps.setBigDecimal(1, account.getBalance());
			ps.executeUpdate();
			insertedKeys = ps.getGeneratedKeys();
			if (insertedKeys.next()) {
				account.setAccountId(insertedKeys.getLong(1));
			} else {
				logger.error("Insert failed.");
				throw new AccountException("Insert failed as no ID has returned");
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception rollbackException) {
				throw new AccountException("Can't rollback account creation transaction: " + rollbackException);
			}
			throw new AccountException("Error in account creation transaction: {}" + e);
		} finally {
			close(ps);
			close(insertedKeys);
			close(conn);
		}

		return account;
	}

	@Override
	public Account update(Account account) throws AccountException {
		logger.debug("Account ID: {} is Being Updated", account.getAccountId());
		PreparedStatement ps = null;
		Connection conn = getCurrentConnection();
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("UPDATE account set balance = ? WHERE accountId = ?");
			ps.setBigDecimal(1, account.getBalance());
			ps.setLong(2, account.getAccountId());
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception rollbackException) {
				throw new AccountException("Can't rollback account update transaction" + rollbackException);
			}
			throw new AccountException("Error in account update transaction" + e);
		} finally {
			close(ps);
			close(conn);
		}
		return account;
	}

}
