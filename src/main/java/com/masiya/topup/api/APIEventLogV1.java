package com.masiya.topup.api;

import java.security.Principal;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.jersey.server.ContainerRequest;

import com.masiya.topup.TopupResponseCollection;
import com.masiya.topup.filter.Authorizer;
import com.masiya.topup.manager.EventLogManager;
import com.masiya.topup.model.EventLog;
//import com.wordnik.swagger.annotations.Api;
import com.masiya.topup.model.User;

@Path("/v1/eventlogs")
//@Api(value = "/users", description = "EventLog APIs!")
public class APIEventLogV1 {

	@Inject
	EventLogManager mngrEventLog;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin_all", "admin_customer", "user"})
	public TopupResponseCollection<EventLog> getEventLogs(@QueryParam("search") String search, @QueryParam("type") String type, @Context SecurityContext context, @Context ContainerRequestContext crc) {
		User usr = (User) crc.getProperty("User");
		List<EventLog> lstUsr = null;
		if((type != null && !type.isEmpty()) &&
			(search != null && !search.isEmpty())) {
			lstUsr = mngrEventLog.selectAllByTypeCondition(type, usr, search);
		} else if((type != null && !type.isEmpty()) &&
				(search == null || search.isEmpty())) {
			lstUsr = mngrEventLog.selectAllByType(type, usr);
		} else {
			lstUsr = mngrEventLog.selectAll(search);
		}
		
		TopupResponseCollection<EventLog> trc = new TopupResponseCollection<EventLog>(lstUsr);
		return trc;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public EventLog getEventLogById(@PathParam("id") Integer id, @Context SecurityContext context) {
		return mngrEventLog.selectById(id);
	}
}
