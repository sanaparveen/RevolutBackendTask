package com.revolut.backend.task;

import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revolut.backend.task.config.DBConnectConfig;

import io.javalin.Javalin;

public class ApplicationTest {

	@InjectMocks
	private Application application;
	@Mock
	private Javalin restApp;
	@Mock
	private DBConnectConfig dbConnectConfig;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		application = new Application();
	}

	@Test
	public void shouldRunMainMethodWithoutAnyException() throws Exception {
		Mockito.doNothing().when(dbConnectConfig).dbConnect();
		Application.main(null);
	}

	@Test
	public void shouldNotStopApplicationOnStopMethodCall() {
		restApp = null;
		application.stop();
		assertNull(restApp);
	}

	@After
	public void afterTestRun() {
		application.stop();
	}

}
