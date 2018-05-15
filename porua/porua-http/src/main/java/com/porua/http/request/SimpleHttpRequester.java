package com.porua.http.request;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.porua.core.context.PoruaContext;
import com.porua.core.processor.MessageProcessor;

/**
 * Spring Bean. Populated by Spring.
 */
public class SimpleHttpRequester extends MessageProcessor {

	private SimpleHttpRequesterConfiguration config;
	private ExecutorService es = Executors.newFixedThreadPool(2);

	public SimpleHttpRequester() {
	}

	@Override
	public void process() {
		es.submit(new DataProcessor(getPoruaContext()));
	}

	public SimpleHttpRequesterConfiguration getConfig() {
		return config;
	}

	public void setConfig(SimpleHttpRequesterConfiguration config) {
		this.config = config;
	}

	// Test 
	public class DataProcessor extends MessageProcessor implements Runnable {
		public DataProcessor(PoruaContext context) {
			super.setPoruaContext(context);
		}

		public void run() {
			try {
				Thread.sleep(5000);
				Integer num = (Integer) getPoruaContext().getPayload();
				num = num + 1;
				super.getPoruaContext().setPayload(num);
				super.process();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
