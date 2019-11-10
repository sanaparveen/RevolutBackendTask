DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS account;


CREATE TABLE account (
	accountId LONG PRIMARY KEY AUTO_INCREMENT  NOT NULL,
	balance DECIMAL(12,2) NOT NULL
);

CREATE TABLE transaction (
	transactionId LONG PRIMARY KEY AUTO_INCREMENT  NOT NULL,
	toAccountId VARCHAR(128) NOT NULL,
	fromAccountId VARCHAR(128) NOT NULL,
	amount DECIMAL(12,2) NOT NULL,
);

INSERT INTO account (accountId,balance) VALUES (1,2500.00);
INSERT INTO account (accountId,balance) VALUES (2,2000.00);
INSERT INTO account (accountId,balance) VALUES (3,2500.00);
INSERT INTO account (accountId,balance) VALUES (4,10000.00);
INSERT INTO account (accountId,balance) VALUES (5,250000000.00);
INSERT INTO account (accountId,balance) VALUES (6,200035678.00);
