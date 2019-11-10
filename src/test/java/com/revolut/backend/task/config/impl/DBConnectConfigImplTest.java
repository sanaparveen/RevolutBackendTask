/**
 * 
 */
package com.revolut.backend.task.config.impl;

import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.revolut.backend.task.config.DBConnectConfig;

/**
 * @author sanaparveen
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DBConnectConfigImplTest extends DBConnectConfig {

	@InjectMocks
	private DBConnectConfigImpl dbConnectConfigImpl;

	@Mock
	RunScript runscript;


	@Before
	public void beforeTest() {
		MockitoAnnotations.initMocks(this);
		runscript = Mockito.mock(RunScript.class);
	}

	@Test
	public void testDBConfiguration() throws SQLException {
		dbConnectConfigImpl.dbConnect();
	}

}
