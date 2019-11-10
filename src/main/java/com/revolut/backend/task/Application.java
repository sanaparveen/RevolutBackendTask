/**
 * 
 */
package com.revolut.backend.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.backend.task.config.DBConnectConfig;
import com.revolut.backend.task.config.impl.DBConnectConfigImpl;
import com.revolut.backend.task.handler.TransferHandler;
import com.revolut.backend.task.utility.Constants;

import io.javalin.Javalin;

/**
 * @author sanaparveen
 * 
 *         The Application class is the main class which is responsible to run
 *         the server and register the rest API route using Javalin. Also
 *         Initializes the Database as It is an In memory h2 database.
 *
 */
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	private Javalin restApp;

	/**
	 * Setup, configure the database H2 DB and start the application.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		DBConnectConfig dbConnectConfig = new DBConnectConfigImpl();
		dbConnectConfig.dbConnect();
		new Application().startApplication();

	}

	/**
	 * Start Application using Javalin and register API for money transfer.
	 */
	public void startApplication() {
		logger.info("Starting Revolut Rest API Application For Money Transfer");
		restApp = Javalin.create();
		restApp.start();
		registerRestAPI();
		logger.info("Started Revolut Rest API Application For Money Transfer");
	}

	private void registerRestAPI() {
		final TransferHandler transactionController = new TransferHandler();
		restApp.get(Constants.HOME_API, ctx -> ctx.result("~~ ~~~ Revolut Backend Task for Money Transfer ~~~ ~~"));
		restApp.post(Constants.TRANSFER_API, transactionController);
	}

	/**
	 * This method is used to stop the application by stopping the Javalin server.
	 */
	public void stop() {
		logger.info("Stopping Revolut Rest API Application For Money Transfer");
		if (restApp != null) {
			restApp.stop();
		}
		logger.info("Stopped Revolut Rest API Application For Money Transfer");
	}
}
