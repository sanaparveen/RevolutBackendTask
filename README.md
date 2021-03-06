# `Revolut Backend Home Task`

[![Build Status](https://travis-ci.org/sanaparveen/RevolutBackendTask.svg?branch=master)](https://travis-ci.org/sanaparveen/RevolutBackendTask)

Design and implement a RESTful API (including data model and the backing implementation) for money transfers between accounts.

#####  Explicit requirements:
1. You can use Java or Kotlin.
2. Keep it simple and to the point (e.g. no need to implement any authentication).
3. Assume the API is invoked by multiple systems and services on behalf of end users.
4. You can use frameworks/libraries if you like (except Spring), but don't forget about
requirement #2 and keep it simple and avoid heavy frameworks.
5. The datastore should run in-memory for the sake of this test.
6. The final result should be executable as a standalone program (should not require a
pre-installed container/server).
7. Demonstrate with tests that the API works as expected.
##### Implicit requirements:
1. The code produced by you is expected to be of high quality.
2. There are no detailed requirements, use common sense.

__________
__________

### `TASK IMPLEMENTATION DETAILS`
1. Programming Language: Java
2. Application is build in simple form but keeping the scope high.
3. API is build in a form where it can be invoked by multiple systems and services.
4. Frameworks and Libraries:

    ```
    - Javalin
    - Javax validation
    - H2 In-Memory Database
    - Hibernate
    - JUnit
    - Mockito
    ```
5. DataStore is In-Memory, H2 DB is Used.
6. A Jar file can be produced which can be used to run the Application
7. Unit Test Cases Have been added to test the Application

___
### REST API's


* #### Money Transfer Between Accounts
    ```
    URL: /revolut/transfer/
    METHOD: POST
    CONTENT-TYPE: application/json
    BODY: 
        {
        	"fromAccountId": 1,
        	"toAccountId": 2,
        	"amount": 2000
        }
    ```
    Note: Please check Initial Data to Test the Application
___
#### How To Run the Application

 Download/Clone the Project
```
cd <project-folder>
mvn exec:java
```

Verify the application by navigating to your server address in your preferred browser.

```sh
http://localhost:7000/revolut/
```


>  `NOTE:` This Application is *NOT* a full fleched banking application
> During the Application Building the use case is strictly taken into consideration
> Therefore, the major API that was worked upon was transfer API
> which is used for money transfer between accounts.
> 
>
>  Learning Reference for Javalin: https://javalin.io/documentation

### Database 

1. Account Table
    ```
    
    CREATE TABLE account (
    	accountId LONG PRIMARY KEY AUTO_INCREMENT  NOT NULL,
    	balance DECIMAL(12,2) NOT NULL
    );
    ```
2. Transaction Table
    ```
    CREATE TABLE transaction (
    	transactionId LONG PRIMARY KEY AUTO_INCREMENT  NOT NULL,
    	toAccountId VARCHAR(128) NOT NULL,
    	fromAccountId VARCHAR(128) NOT NULL,
    	amount DECIMAL(12,2) NOT NULL,
    );
    ```
3. Initial Data In Table To Test The Application With
    ```
    INSERT INTO account (accountId,balance) VALUES (1,2500.00);
    INSERT INTO account (accountId,balance) VALUES (2,2000.00);
    INSERT INTO account (accountId,balance) VALUES (3,2500.00);
    INSERT INTO account (accountId,balance) VALUES (4,10000.00);
    INSERT INTO account (accountId,balance) VALUES (5,250000000.00);
    INSERT INTO account (accountId,balance) VALUES (6,200035678.00);
    ```

----
----
License
----

##### Eclipse Public License - v 2.0
