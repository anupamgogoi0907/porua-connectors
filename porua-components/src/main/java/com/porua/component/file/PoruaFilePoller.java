package com.porua.component.file;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.porua.core.context.PoruaContext;
import com.porua.core.flow.Flow;
import com.porua.core.listener.MessageListener;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;

@Connector(tagName = "file-poller", tagNamespace = "http://www.porua.org/components", tagSchemaLocation = "http://www.porua.org/components/components.xsd", imageName = "core-file-poller.png")
public class PoruaFilePoller extends MessageListener {
	enum FILE_POLL_OPERATIONS {
		CREATE, MODIFY, DELETE, ALL
	}

	@ConfigProperty(enumClass = FILE_POLL_OPERATIONS.class)
	private String operation;

	@ConfigProperty
	private String directory;

	@ConfigProperty
	private Long timeout;

	private Logger logger = LogManager.getLogger(PoruaFilePoller.class);
	private WatchService watchService;

	@Override
	public void startListener(Flow flow) {
		logger.info("Starting file poller for: " + directory);
		try {
			watchService = FileSystems.getDefault().newWatchService();
			Path path = Paths.get(directory);
			if ("CREATE".equalsIgnoreCase(operation)) {
				path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

			} else if ("MODIFY".equalsIgnoreCase(operation)) {
				path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

			} else if ("DELETE".equalsIgnoreCase(operation)) {
				path.register(watchService, StandardWatchEventKinds.ENTRY_DELETE);
			} else if ("ALL".equalsIgnoreCase(operation)) {
				path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
						StandardWatchEventKinds.ENTRY_MODIFY);
			}
			poll(flow);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public void stopListener() {
		logger.info("Stopping file poller on: " + directory);
		try {
			if (watchService != null) {
				watchService.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Polling implementation.
	 */
	void poll(Flow flow) {
		logger.info("Polling on " + directory);
		try {
			WatchKey key = null;
			if (0 == timeout) {
				while (true) {
					key = watchService.poll();
					if (key != null) {
						processEvent(flow, key);
					}
				}
			} else {
				key = watchService.poll(timeout, TimeUnit.SECONDS);
				if (key != null) {
					processEvent(flow, key);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Processing events.
	 * 
	 * @param events
	 */
	void processEvent(Flow flow, WatchKey key) {
		key.pollEvents().forEach((event) -> {
			try {
				logger.info("Process event: " + event.kind().toString());
				if (StandardWatchEventKinds.ENTRY_CREATE == event.kind()
						|| StandardWatchEventKinds.ENTRY_MODIFY == event.kind()) {

					// Get the created/modified file.
					Path filePath = Paths.get(directory).resolve((Path) event.context());
					logger.info("Read file: " + filePath);
					InputStream is = new FileInputStream(filePath.toFile());

					// Making context and triggering flow.
					PoruaContext context = new PoruaContext(flow.getProcessors());
					context.setPayload(is);

					// Start the processor chain.
					MessageProcessor processor = context.getProcessors().remove();
					processor.process();
				}
				// Reset key.
				key.reset();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		});
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

}
