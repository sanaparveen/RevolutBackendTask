package com.revolut.backend.task.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.backend.task.config.DBConnectConfig;

public class DBConnectConfigTest extends DBConnectConfig {

	private static final Logger logger = LoggerFactory.getLogger(DBConnectConfig.class);

	@Test
	public void shouldInitializeDB() {
		Connection conn = getCurrentConnection();
		try {
			RunScript.execute(conn, new FileReader("src/test/resources/data.sql"));
		} catch (FileNotFoundException e) {
			try {
				RunScript.execute(conn, new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/data.sql"))));
			} catch (SQLException sqlException) {
				logger.error("Error in Re-try Running SQL Script Execution: {}", e);
			}
		} catch (SQLException e) {
			logger.error("Error in Running SQL Script Execution: {}", e);
		}
	}

}
