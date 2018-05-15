package com.porua.jms.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.porua.jms.server.SimpleJmsServer;

public class TestJmsComponent {

	@Test
	public void test() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jms-test.xml");
		SimpleJmsServer jmsServer=ctx.getBean(SimpleJmsServer.class);
		assertNotNull(jmsServer.getUrl());
		ctx.close();
	}
}
