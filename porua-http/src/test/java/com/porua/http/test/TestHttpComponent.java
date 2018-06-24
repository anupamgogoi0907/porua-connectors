package com.porua.http.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.porua.http.request.SimpleHttpRequester;
import com.porua.http.server.SimpleHttpServer;

public class TestHttpComponent {

	@Test
	public void testHttp() {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("http-test.xml");
		SimpleHttpServer server = ctx.getBean(SimpleHttpServer.class);
		Assert.assertNotNull(server.getConfig());
		SimpleHttpRequester requester = ctx.getBean(SimpleHttpRequester.class);
		requester.process();
		ctx.close();
	}

	// @Test
	public void testServer() {
		Map<String, Object> mapHeader = new HashMap<>();
		try {
			HttpServer server = HttpServer.createSimpleServer(null, "localhost", 8080);
			server.getServerConfiguration().addHttpHandler(new HttpHandler() {

				@Override
				public void service(Request req, Response res) throws Exception {
					InputStream is = req.getInputStream();
					convertToString(is);
					// Extract Headers.
					Iterable<String> headers = req.getHeaderNames();
					for (String h : headers) {
						mapHeader.put(h, req.getHeader(h));
					}

					// Extract Parameters.
					Map<String, String[]> mapParam = req.getParameterMap();
					for (String p : mapParam.keySet()) {
						String[] params = mapParam.get(p);
						System.out.println(params.length);
					}

				}
			}, "/hello");
			server.start();
			System.out.println("started...");
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String convertToString(InputStream inputStream) {
		String res = "";
		try {
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			res = result.toString("UTF-8");
			System.out.println(res);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return res;

	}
}
