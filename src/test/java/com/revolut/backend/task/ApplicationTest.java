package com.revolut.backend.task;

import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.revolut.backend.task.config.DBConnectConfig;

import io.javalin.Javalin;

@PowerMockIgnore("javax.management.*")
@PrepareForTest({ ApplicationTest.class, Javalin.class })
class ApplicationTest {

	@InjectMocks
	private Application application;

	@Mock
	private Javalin restApp;

	@Mock
	private DBConnectConfig dbConnectConfig;

	@Before
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
	@Test(expected = Exception.class)
	public void shouldThrowExceptionWhenJavalinFailedToStart() throws Exception {
		Mockito.doThrow(new IllegalStateException("error")).when(restApp).create();
		application.main(null);
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

	@After
	private void afterTestRun() {
		application.stop();
	}

}
