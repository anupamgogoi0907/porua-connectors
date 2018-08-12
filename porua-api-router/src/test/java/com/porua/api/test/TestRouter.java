package com.porua.api.test;

import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.porua.api.router.ApiRouter;
import com.porua.api.router.RouterConfig;
import com.porua.core.flow.Flow;

public class TestRouter {

	static ClassPathXmlApplicationContext ctx = null;

	@BeforeClass
	public static void loadContext() throws Exception {
		ctx = new ClassPathXmlApplicationContext("router-test.xml");
	}

	@Test
	public void testApiRouter() {
		ApiRouter router = ctx.getBean(ApiRouter.class);
		assertNotNull(router);
	}

	@Test
	public void testApiRouterConfig() {
		RouterConfig routerConfig = ctx.getBean(RouterConfig.class);
		assertNotNull(routerConfig);
	}

	@AfterClass
	public static void closeCtx() throws Exception {
		ctx.close();
	}

	// @Test
	public void test() throws Exception {
		// ApiGen.generateApiClass("api.yaml");

		RouterConfig config = new RouterConfig();
		config.setProtocol("http");
		config.setHost("localhost");
		config.setPort(8080);
		config.setServerPath("/test");

		ApiRouter router = new ApiRouter();
		router.setApiPath("api.yaml");
		router.setConsolePath("/console");
		router.setResources("com.porua.api.test");
		router.setConfig(config);

		router.startListener(new Flow());
		Thread.currentThread().join();
	}

}
