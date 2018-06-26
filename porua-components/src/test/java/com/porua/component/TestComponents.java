package com.porua.component;

import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.porua.component.file.PoruaFileConnector;
import com.porua.component.java.JavaComponent;
import com.porua.component.json.JsonToJava;
import com.porua.component.json.JsonToXml;
import com.porua.component.logger.PoruaLogger;
import com.porua.component.payload.PayloadSetter;
import com.porua.component.variable.VariableSetter;

/**
 * TestComponents custom tags.
 * 
 * @author ac-agogoi
 *
 */
public class TestComponents {
	static ClassPathXmlApplicationContext ctx;

	@BeforeClass
	public static void loadContext() throws Exception {
		ctx = new ClassPathXmlApplicationContext("core-comp-test.xml");
	}

	@Test
	public void testSetPayload() throws Exception {
		PayloadSetter payloadSetter = ctx.getBean(PayloadSetter.class);
		assertNotNull(payloadSetter);
	}

	@Test
	public void testSetVariable() throws Exception {
		VariableSetter varSetter = ctx.getBean(VariableSetter.class);
		assertNotNull(varSetter);
	}

	@org.junit.Test
	public void testPoruaLogger() throws Exception {
		PoruaLogger logger = ctx.getBean(PoruaLogger.class);
		assertNotNull(logger);
	}

	@Test
	public void testFileConnector() throws Exception {
		PoruaFileConnector fileConnector = ctx.getBean(PoruaFileConnector.class);
		assertNotNull(fileConnector);
	}

	@Test
	public void testJsonToXml() throws Exception {
		JsonToXml jsonToXml = ctx.getBean(JsonToXml.class);
		assertNotNull(jsonToXml);
	}

	@Test
	public void testJsonToJava() throws Exception {
		JsonToJava jsonToJava = ctx.getBean(JsonToJava.class);
		assertNotNull(jsonToJava);
	}

	@org.junit.Test
	public void testJavaComp() throws Exception {
		JavaComponent jc = ctx.getBean(JavaComponent.class);
		assertNotNull(jc);
	}

	@AfterClass
	public static void closeContext() throws Exception {
		if (ctx != null) {
			ctx.close();
		}
	}

}
