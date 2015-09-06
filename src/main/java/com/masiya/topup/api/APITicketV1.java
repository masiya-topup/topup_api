package com.masiya.topup.api;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.masiya.topup.TopupResponseCollection;
import com.masiya.topup.manager.TicketManager;
import com.masiya.topup.model.Ticket;
import com.masiya.topup.model.User;

@Path("/v1/tickets")
public class APITicketV1 {
	
	@Inject
	TicketManager mngrTicket;

	public APITicketV1() {
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Ticket addTicket(Ticket ticket, @Context SecurityContext context, @Context ContainerRequestContext crc) {
		User usr = (User) crc.getProperty("User");
		ticket.setOwner(usr);
		return mngrTicket.add(ticket);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Ticket updateTicket(@PathParam("id") Integer id, Ticket ticket, @Context SecurityContext context, @Context ContainerRequestContext crc) {
		User usr = (User) crc.getProperty("User");
		ticket.setOwner(usr);
		ticket.setTicketId(id);
		return mngrTicket.update(ticket);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTicket(@PathParam("id") Integer id, @Context SecurityContext context, @Context ContainerRequestContext crc) {
		boolean bRes = mngrTicket.delete(id);
		Response resp = Response.status(Response.Status.BAD_REQUEST).build();
		if(bRes) {
			resp = Response.status(Response.Status.NO_CONTENT).build();
		}
		return resp;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public TopupResponseCollection<Ticket> getCompanies(@QueryParam("search") String search, @Context SecurityContext context, @Context ContainerRequestContext crc) {
		List<Ticket> lstComp = mngrTicket.selectAll(search);
		TopupResponseCollection<Ticket> respColl = new TopupResponseCollection<Ticket>(lstComp);
		return respColl;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Ticket getTicketById(@PathParam("id") Integer id, @Context SecurityContext context, @Context ContainerRequestContext crc) {
		return mngrTicket.selectById(id);
	}

}
