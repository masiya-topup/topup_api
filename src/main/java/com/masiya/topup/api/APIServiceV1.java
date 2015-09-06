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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.masiya.topup.TopupResponseCollection;
import com.masiya.topup.manager.ServiceManager;
import com.masiya.topup.model.Service;

@Path("/v1/services")
public class APIServiceV1 {
	
	@Inject
	ServiceManager mngrService;

	public APIServiceV1() {
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Service addService(Service service) {
		return mngrService.add(service);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Service updateService(@PathParam("id") Integer id, Service service) {
		service.setServiceId(id);
		return mngrService.update(service);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteService(@PathParam("id") Integer id) {
		boolean bRes = mngrService.delete(id);
		Response resp = Response.status(Response.Status.BAD_REQUEST).build();
		if(bRes) {
			resp = Response.status(Response.Status.NO_CONTENT).build();
		}
		return resp;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public TopupResponseCollection<Service> getServices(@QueryParam("search") String search) {
		List<Service> lst = mngrService.selectAll(search);
		TopupResponseCollection<Service> trc = new TopupResponseCollection<Service>(lst);
		return trc;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Service getServiceById(@PathParam("id") Integer id) {
		return mngrService.selectById(id);
	}

}
