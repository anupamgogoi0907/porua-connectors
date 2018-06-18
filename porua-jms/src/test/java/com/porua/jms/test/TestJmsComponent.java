package com.porua.jms.test;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.porua.jms.server.SimpleJmsServer;

public class TestJmsComponent {

	@Test
	public void test() throws IOException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jms-test.xml");
		SimpleJmsServer jmsServer=ctx.getBean(SimpleJmsServer.class);
		//System.in.read();
		assertNotNull(jmsServer.getUrl());
		ctx.close();
	}
}
