package com.porua.api.utility;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CorsHandler implements ContainerResponseFilter {

	private Logger logger = LogManager.getLogger(CorsHandler.class);

	@Override
	public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {
		logger.debug("Handling CORS");
		res.getHeaders().add("Access-Control-Allow-Origin", "*");
		res.getHeaders().add("Access-Control-Allow-Headers", "*");
		res.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");

	}

}
