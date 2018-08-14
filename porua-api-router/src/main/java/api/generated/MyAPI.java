package api.generated;

import com.porua.api.router.ContextMaker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.glassfish.jersey.server.ContainerRequest;

@Path("/")
@Api("Test")
public class MyAPI extends ContextMaker {
	@Context
	Configuration config;

	@Context
	UriInfo uriInfo;

	@Context
	ContainerRequest request;

	@GET
	@Path("/users")
	@Produces({ "application/json", "application/xml" })
	@ApiOperation("getusers")
	@ApiResponses({ @io.swagger.annotations.ApiResponse(code = 200, message = "OK") })
	public void getusers(Object payload, @Suspended AsyncResponse asyncResponse) {
		makeContext(config, uriInfo, request, payload, asyncResponse);
	}

	@GET
	@Path("/inventory")
	@Produces({ "application/json" })
	@ApiOperation("getinventory")
	@ApiResponses({ @io.swagger.annotations.ApiResponse(code = 200, message = "search results matching criteria"),
			@io.swagger.annotations.ApiResponse(code = 400, message = "bad input parameter") })
	public void getinventory(@QueryParam("searchString") String searchString, @QueryParam("skip") String skip,
			@QueryParam("limit") String limit, Object payload, @Suspended AsyncResponse asyncResponse) {
		makeContext(config, uriInfo, request, payload, asyncResponse);
	}

	@POST
	@Path("/inventory")
	@Produces({ "application/json" })
	@Consumes({ "application/json" })
	@ApiOperation("postinventory")
	@ApiResponses({ @io.swagger.annotations.ApiResponse(code = 201, message = "item created"),
			@io.swagger.annotations.ApiResponse(code = 400, message = "invalid input, object invalid"),
			@io.swagger.annotations.ApiResponse(code = 409, message = "an existing item already exists") })
	public void postinventory(Object payload, @Suspended AsyncResponse asyncResponse) {
		makeContext(config, uriInfo, request, payload, asyncResponse);
	}
}
