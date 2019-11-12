/**
 * 
 */
package com.revolut.backend.task.repository.impl;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.revolut.backend.task.config.DBConnectConfig;
import com.revolut.backend.task.config.impl.DBConnectConfigImpl;
import com.revolut.backend.task.entity.Account;
import com.revolut.backend.task.entity.Transaction;
import com.revolut.backend.task.exception.TransactionException;
import com.revolut.backend.task.repository.TransactionRepository;

/**
 * @author Sana_Parveen
 *
 */
public class TransactionRepositoryImplTest {

	private TransactionRepository transactionRepository;

	@Before
	public void setup() throws Exception {
		DBConnectConfig dbConnectConfig = new DBConnectConfigImpl();
		dbConnectConfig.dbConnect();
		transactionRepository = new TransactionRepositoryImpl();
	}

	@Test
	public void testTransferMethodWhenTransferIsSuccessfull() throws TransactionException {
		Account sender = new Account(1l, new BigDecimal(250.00));
		Account receiver = new Account(1l, new BigDecimal(0.00));
		Transaction transaction = new Transaction(1l, 2l, new BigDecimal(250.00));
		Transaction transactionResult = transactionRepository.transfer(transaction, sender, receiver);
		assertNotNull(transactionResult.getTransactionId());
	}

	@Test(expected = TransactionException.class)
	public void testTransferMethodWhenTransferIsFailed() throws TransactionException {
		Account sender = new Account(1l, new BigDecimal(250.12));
		Account receiver = new Account(1l, new BigDecimal(0.00));
		Transaction transaction = new Transaction(1l, 2l, new BigDecimal(250.00));

		transactionRepository = Mockito.mock(TransactionRepositoryImpl.class);
		Mockito.when(transactionRepository.transfer(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new TransactionException("Failed To Transfer"));
		transactionRepository.transfer(transaction, sender, receiver);
	}

}
