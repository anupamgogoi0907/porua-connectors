package com.porua.core.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.porua.core.flow.Flow;

public class TestCoreComponent {

	@Test
	public void testCore() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("core-test.xml");
		Flow flow = ctx.getBean(Flow.class);
		assertNotNull(flow.getProcessors());
		ctx.close();
	}
}
