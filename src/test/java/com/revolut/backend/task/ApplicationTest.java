package com.revolut.backend.task;

import static org.junit.Assert.assertNull;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revolut.backend.task.config.DBConnectConfig;

import io.javalin.Javalin;

class ApplicationTest {

	@InjectMocks
	private Application application;

	@Mock
	private Javalin restApp;

	@Mock
	private DBConnectConfig dbConnectConfig;

	@BeforeEach
	private void setup() {
		application = new Application();
		restApp = Mockito.mock(Javalin.class);
		dbConnectConfig = Mockito.mock(DBConnectConfig.class);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldRunMainMethodWithoutAnyException() throws Exception {
		Mockito.doNothing().when(dbConnectConfig).dbConnect();
		Application.main(null);
	}

	@SuppressWarnings("static-access")
	@Test
	public void shouldThrowExceptionWhenJavalinFailedToStart() throws SQLException {
		Mockito.doThrow(new IllegalStateException("error")).when(restApp).create();
		Assertions.assertThrows(Exception.class, () -> {
			application.main(null);
		});
	}

	@Test
	public void shouldStopApplicationIfJavalinAppStartedOnStopMethodCall() {
		application.stop();
		Mockito.verify(restApp, Mockito.times(1)).stop();
	}

	@Test
	public void shouldNotStopApplicationIfJavalinAppStartedOnStopMethodCall() {
		restApp = null;
		application.stop();
		assertNull(restApp);
	}

	@AfterEach
	private void afterTestRun() {
		application.stop();
	}

}
