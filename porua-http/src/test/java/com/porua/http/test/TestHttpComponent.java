package com.porua.http.test;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.porua.http.request.SimpleHttpRequester;
import com.porua.http.server.SimpleHttpServer;

public class TestHttpComponent {
	static ClassPathXmlApplicationContext ctx = null;

	@BeforeClass
	public static void loadContext() throws Exception {
		ctx = new ClassPathXmlApplicationContext("http-test.xml");
	}

	@Test
	public void testHttpServer() throws IOException {
		SimpleHttpServer server = ctx.getBean(SimpleHttpServer.class);
		Assert.assertNotNull(server.getConfig());

	}

	@Test
	public void testHttpRequester() throws Exception {
		SimpleHttpRequester requester = ctx.getBean(SimpleHttpRequester.class);
		assertNotNull(requester);
	}

	@AfterClass
	public static void closeContext() throws Exception {
		if (ctx != null) {
			ctx.close();
		}
	}
}
