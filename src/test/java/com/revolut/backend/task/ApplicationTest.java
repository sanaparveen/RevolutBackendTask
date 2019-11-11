package com.revolut.backend.task;

import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revolut.backend.task.config.DBConnectConfig;
import com.revolut.backend.task.repository.AccountRepository;
import com.revolut.backend.task.repository.TransactionRepository;
import com.revolut.backend.task.repository.impl.AccountRepositoryImpl;
import com.revolut.backend.task.repository.impl.TransactionRepositoryImpl;
import com.revolut.backend.task.service.AccountService;
import com.revolut.backend.task.service.TransactionService;
import com.revolut.backend.task.service.impl.AccountServiceImpl;
import com.revolut.backend.task.service.impl.TransactionServiceImpl;

import io.javalin.Javalin;

class ApplicationTest {

	@InjectMocks
	private Application application;

	@Mock
	private Javalin restApp;

	@Mock
	private DBConnectConfig dbConnectConfig;

//	private Injector injector;

	@Before
	public void setup() {
//		injector = Guice.createInjector(new AbstractModule() {
//			protected void configure() {
//				bind(TransactionRepository.class).to(TransactionRepositoryImpl.class);
//				bind(AccountRepository.class).to(AccountRepositoryImpl.class);
//				bind(AccountService.class).to(AccountServiceImpl.class);
//				bind(TransactionService.class).to(TransactionServiceImpl.class);
//			}
//		});
//		application = new Application();
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
	public void afterTestRun() {
		application.stop();
	}

}
