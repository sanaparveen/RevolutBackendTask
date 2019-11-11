/**
 * 
 */
package com.revolut.backend.task.utility;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.revolut.backend.task.entity.Account;
import com.revolut.backend.task.entity.Transaction;
import com.revolut.backend.task.exception.AccountNotFoundException;
import com.revolut.backend.task.exception.InsufficientBalanceException;
import com.revolut.backend.task.exception.InvalidAccountException;
import com.revolut.backend.task.exception.InvalidTransferAmountException;

/**
 * @author Sana_Parveen
 *
 */
public class TransactionUtilTest {

	private Transaction transaction;
	private TransactionUtil transactionUtil;

	@Before
	public void setUp() throws Exception {
		transactionUtil = new TransactionUtil();
	}

	@Test
	public void testValidRequestForValidAccountAndAmount() throws InvalidTransferAmountException, InvalidAccountException {
		transaction = new Transaction(1l, 2l, new BigDecimal(123));
		transactionUtil.validateRequest(transaction);
	}

	@Test(expected = InvalidTransferAmountException.class)
	public void testValidRequestForInvalidAmount() throws InvalidTransferAmountException, InvalidAccountException {
		transaction = new Transaction(1l, 2l, new BigDecimal(123));
		transaction.setAmount(new BigDecimal(-1));
		transactionUtil.validateRequest(transaction);
	}

	@Test(expected = InvalidAccountException.class)
	public void testValidRequestForInvalidAccountId() throws InvalidTransferAmountException, InvalidAccountException {
		transaction = new Transaction(1l, 1l, new BigDecimal(123));
		transaction.setAmount(new BigDecimal(1));
		transactionUtil.validateRequest(transaction);
	}

	@Test
	public void testValidateAccountsWithValidAccount() throws AccountNotFoundException {
		Optional<Account> sender = Optional.of(new Account(1, new BigDecimal(1)));
		Optional<Account> receiver = Optional.of(new Account(2, new BigDecimal(2)));
		transactionUtil.validateAccounts(sender, receiver);
	}
 
	@Test(expected = AccountNotFoundException.class)
	public void testValidateAccountsWithInvalidSenderAccount() throws AccountNotFoundException {
		Optional<Account> sender = Optional.empty();
		Optional<Account> receiver = Optional.of(new Account(2, new BigDecimal(2)));
		transactionUtil.validateAccounts(sender, receiver);
	}

	@Test(expected = AccountNotFoundException.class)
	public void testValidateAccountsWithInvalidReceiverAccount() throws AccountNotFoundException {
		Optional<Account> sender = Optional.of(new Account(1, new BigDecimal(1)));
		Optional<Account> receiver = Optional.empty();
		transactionUtil.validateAccounts(sender, receiver);
	}

	@Test(expected = AccountNotFoundException.class)
	public void testValidateAccountsWithBothAccountsInvalid() throws AccountNotFoundException {
		Optional<Account> sender = Optional.empty();
		Optional<Account> receiver = Optional.empty();
		transactionUtil.validateAccounts(sender, receiver);
	}

	@Test
	public void testAccountBalanceForTransferWithSameAmountPresentInAccount() throws InsufficientBalanceException {
		Account sender = new Account(1, new BigDecimal(123));
		transaction = new Transaction(1l, 2l, new BigDecimal(123));
		transactionUtil.validateAmount(transaction, sender);
	}

	@Test(expected = InsufficientBalanceException.class)
	public void testAccountBalanceForTransferWithInsufficientAmountPresentInAccount() throws InsufficientBalanceException {
		Account sender = new Account(1, new BigDecimal(123.01));
		transaction = new Transaction(1l, 2l, new BigDecimal(123.03));
		transactionUtil.validateAmount(transaction, sender);
	}

	@Test
	public void testAccountBalanceForTransferWithAmountPresentInAccount() throws InsufficientBalanceException {
		Account sender = new Account(1, new BigDecimal(124.0));
		transaction = new Transaction(1l, 2l, new BigDecimal(123.03));
		transactionUtil.validateAmount(transaction, sender);
	}
}
