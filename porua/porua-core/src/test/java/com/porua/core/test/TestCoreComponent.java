package com.porua.core.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.porua.core.flow.Flow;
import com.porua.core.processor.json.JsonToXml;

public class TestCoreComponent {

	@Test
	public void testCore() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("core-test.xml");
		Flow flow = ctx.getBean(Flow.class);
		assertNotNull(flow.getProcessors());
		ctx.close();
	}

	@Test
	public void testJsonToXml() {
		try {
			JsonToXml jx = new JsonToXml();
			String json = "{\"name\":\"anuopam\",\"age\":12}";
			System.out.println(jx.convertAndFormat(json));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
