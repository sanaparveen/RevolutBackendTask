package com.revolut.backend.task.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.revolut.backend.task.utility.Constants;

/**
 * @author sanaparveen
 *
 */
public abstract class DBConnectConfig {

	private static final ComboPooledDataSource poolConnection = new ComboPooledDataSource();
	private static final Logger logger = LoggerFactory.getLogger(DBConnectConfig.class);
	

	static {
		try {
			poolConnection.setDriverClass(Constants.DB_DRIVER);
			poolConnection.setJdbcUrl(Constants.DB_URL);
			poolConnection.setUser(Constants.DB_USERNAME);
			poolConnection.setPassword(Constants.DB_PASSWORD);
		} catch (Exception e) {
			logger.error("Error in Setting Connection: {}", e);
		}

	}

	protected static Connection getCurrentConnection() {
		try {
			return poolConnection.getConnection();
		} catch (Exception e) {
			logger.error("Error in Database Connection: {}", e);
			return null;
		}
	}

	public void close(Object obj) {
		if (obj != null) {
			try {

				if (obj instanceof PreparedStatement) {
					((PreparedStatement) obj).close();
				} else if (obj instanceof ResultSet) {
					((ResultSet) obj).close();
				} else if (obj instanceof Connection) {
					((Connection) obj).close();
				}

			} catch (Exception e) {
				logger.error("Error in Connection Close: {}", e);
			}

		}
	}

	public void dbConnect() throws SQLException  {}

}
