package com.porua.core.processor.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.json.JSONObject;
import org.json.XML;

import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.Connector;
import com.porua.core.utility.PoruaUtility;

/**
 * Spring Bean. Populated by Spring.
 */
@Connector(tagName="json-to-xml",tagNamespace="http://www.porua.org/core",tagSchemaLocation="http://www.porua.org/core/core.xsd",imageName="core-json-to-xml.png")
public class JsonToXml extends MessageProcessor {
	private String mimeType;

	public JsonToXml() {
	}

	@Override
	public void process() {
		InputStream is = null;
		String payload = "";
		try {
			if (super.poruaContext.getPayload() instanceof InputStream) {
				is = (InputStream) super.poruaContext.getPayload();
				if (is.available() != 0) {
					payload = PoruaUtility.convertStreamToString(is);
				}
			} else if (super.poruaContext.getPayload() instanceof String) {
				payload = (String) super.poruaContext.getPayload();
			}
			else {
				throw new Exception("Wrong input format.");
			}
			super.getPoruaContext().setPayload(convertAndFormat(payload));
			super.process();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private String convertAndFormat(String payload) throws Exception {
		JSONObject jsonObject = new JSONObject(payload);
		String xml = XML.toString(jsonObject);
		Document doc = DocumentHelper.parseText(xml);
		StringWriter sw = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter xw = new XMLWriter(sw, format);
		xw.write(doc);
		String result = sw.toString();
		return result;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
}
