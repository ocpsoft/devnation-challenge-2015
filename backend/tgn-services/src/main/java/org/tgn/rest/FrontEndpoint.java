package org.tgn.rest;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.POST;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.Consumes;
@Path("/frontend")
public class FrontEndpoint {

	@POST
	@Consumes({"text/plain", "application/json"})
	public Response doPost(String entity) {
		return Response.created(
				UriBuilder.fromResource(FrontEndpoint.class).build()).build();
	}
}