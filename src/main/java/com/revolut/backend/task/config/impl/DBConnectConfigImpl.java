package com.revolut.backend.task.config.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.backend.task.config.DBConnectConfig;

/**
 * @author sanaparveen
 * 
 *         This class is being used to connect to DataBase and execute script to
 *         create tables for the required entity.
 */
public class DBConnectConfigImpl extends DBConnectConfig {

	private static final Logger logger = LoggerFactory.getLogger(DBConnectConfigImpl.class);

	@Override
	public void dbConnect() throws SQLException {
		Connection connection = getCurrentConnection();
		try {
			RunScript.execute(connection,
					new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/data.sql"))));
		} catch (SQLException e) {
			logger.error("Error in DB Connection Initialization: {}", e);
			connection.close();
		}
	}
}
