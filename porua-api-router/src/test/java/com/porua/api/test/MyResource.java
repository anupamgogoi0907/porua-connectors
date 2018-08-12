package com.porua.api.test;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/")
@Api(value = "My Controller")
public class MyResource {

	@Context
	Configuration config;

	@Context
	UriInfo uriInfo;

	@Context
	HttpHeaders httpHeaders;

	@GET
	@Path("/hello")
	@ApiOperation(value = "Say hello")
	public String sayHello() {
		return "Hello";
	}

	@GET
	@Path("/async")
	public void asyncGet(@Suspended AsyncResponse asyncResponse) {
		asyncResponse.resume("Operation success");
	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public void asyncPost(Object data) {
		System.out.println();
	}
}
