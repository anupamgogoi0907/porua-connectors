package com.porua.component;

import static org.junit.Assert.assertNotNull;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.porua.core.flow.Flow;

public class Test {

	@org.junit.Test
	public void testCore() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("core-comp-test.xml");
		Flow flow = ctx.getBean(Flow.class);
		assertNotNull(flow.getProcessors());

		ctx.close();
	}

}
