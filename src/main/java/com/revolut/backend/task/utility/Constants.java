package com.revolut.backend.task.utility;

/**
 * @author sanaparveen
 * 
 */
public class Constants {

	public static final String HOME_API = "/revolut/";
	public static final String TRANSFER_API = "/revolut/transfer";

	public static final String DB_DRIVER = "org.h2.Driver";
	public static final String DB_URL = "jdbc:h2:mem:revolut_tf;DB_CLOSE_DELAY=-1;MVCC=TRUE";
	public static final String DB_USERNAME = "";
	public static final String DB_PASSWORD = "";

	public static final String TRANSACTION_INVALID_CODE = "TRANSACTION_INVAID";
	public static final String TRANSACTION_INVALID_MSG = "Transction is Invalid";

	public static final String TRANSACTION_INVALID_ACCOUNT_ID_CODE = "TRANSACTION_INVALID_ACCOUNT_ID";
	public static final String TRANSACTION_INVALID_ACCOUNT_ID_MSG = "Transaction given Account Id is Invalid";

	public static final String TRANSACTION_ACCOUNT_NOT_FOUND_CODE = "TRANSACTION_ACCOUNT_NOT_FOUND";
	public static final String TRANSACTION_ACCOUNT_NOT_FOUND_MSG = "Transaction Account not Found";

	public static final String TRANSACTION_INVALID_AMOUNT_CODE = "TRANSACTION_INVALID_AMOUNT";
	public static final String TRANSACTION_INVALID_AMOUNT_MSG = "The given amount is invalid";

	public static final String TRANSACTION_INSUFFICIENT_AMOUNT_CODE = "TRANSACTION_INSUFFICIENT_AMOUNT";
	public static final String TRANSACTION_INSUFFICIENT_AMOUNT_MSG = "The amount in your account is insufficient to make the transfer";

	public static final String TRANSACTION_FAILED_CODE = "TRANSACTION_FAILED";
	public static final String TRANSACTION_FAILED_MESSAGE = "Failed to Transfer the Amount";

	public static final String TRANSACTION_FROM_ACCOUNT_ID_FIELD = "fromAccountId";
	public static final String TRANSACTION_TO_ACCOUNT_ID_FIELD = "toAccountId";
	public static final String TRANSACTION_AMOUNT_FIELD = "amount";
}
