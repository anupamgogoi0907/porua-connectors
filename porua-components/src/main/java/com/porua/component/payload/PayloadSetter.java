package com.porua.component.payload;

import java.io.InputStream;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.porua.core.context.PoruaClassLoader;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;

@Connector(tagName = "set-payload", tagNamespace = "http://www.porua.org/components", tagSchemaLocation = "http://www.porua.org/components/components.xsd", imageName = "core-payload-setter.png")
public class PayloadSetter extends MessageProcessor {

	@ConfigProperty
	private String payload;

	@ConfigProperty
	private String file;

	@Override
	public void process() {
		// Processing the file input gets high priority when both properties are filled.
		if (file != null && !file.equals("")) {
			PoruaClassLoader loader = super.springContext.getBean(PoruaClassLoader.class);
			InputStream payloadStream = loader.getResourceAsStream(file);
			super.poruaContext.setPayload(payloadStream);
			payload = null;
		}
		if (payload != null) {
			parsePayload(payload);
		}
		super.process();
	}

	private void parsePayload(String payload) {
		ExpressionParser parser = super.springContext.getBean(SpelExpressionParser.class);
		Object res = parser.parseExpression(payload).getValue(super.poruaContext);
		super.poruaContext.setPayload(res);
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
