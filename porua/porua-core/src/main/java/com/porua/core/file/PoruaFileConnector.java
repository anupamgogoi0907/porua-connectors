package com.porua.core.file;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;

import com.porua.core.processor.MessageProcessor;

public class PoruaFileConnector extends MessageProcessor {
	private String file;
	private String operation;

	@Override
	public void process() {
		if (FileOperations.READ.name().equals(this.operation.toUpperCase())) {
			readFile();
		} else if (FileOperations.WRITE.name().equals(this.operation.toUpperCase())) {
			createFile();
		}
		super.process();
	}

	private void createFile() {
		try {
			Path path = Paths.get(this.file);
			if (super.poruaContext.getPayload() instanceof String) {
				Files.write(path, ((String) super.poruaContext.getPayload()).getBytes());
			} else if (super.poruaContext.getPayload() instanceof InputStream) {
				InputStream is = (InputStream) super.poruaContext.getPayload();
				byte[] bytes = IOUtils.toByteArray(is);
				Files.write(path, bytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readFile() {
		try {
			super.poruaContext.setPayload(Files.newInputStream(Paths.get(this.file)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public enum FileOperations {
		READ, WRITE
	};
}
