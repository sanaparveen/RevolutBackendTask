DROP TABLE IF EXISTS account;

CREATE TABLE account (
	accountId LONG PRIMARY KEY AUTO_INCREMENT  NOT NULL,
	balance DECIMAL(10,2) NOT NULL
);

DROP TABLE IF EXISTS transaction;

CREATE TABLE transaction (
	transactionId LONG PRIMARY KEY AUTO_INCREMENT  NOT NULL,
	toAccountId VARCHAR(128) NOT NULL,
	fromAccountId VARCHAR(128) NOT NULL,
	amount DECIMAL(10,2) NOT NULL,
);

INSERT INTO account (accountId,balance) VALUES (1,2500.00);
INSERT INTO account (accountId,balance) VALUES (2,2000.00);
