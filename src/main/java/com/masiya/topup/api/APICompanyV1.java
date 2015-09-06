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
import com.masiya.topup.manager.CompanyManager;
import com.masiya.topup.model.Company;

@Path("/v1/companies")
public class APICompanyV1 {
	
	@Inject
	CompanyManager mngrCompany;

	public APICompanyV1() {
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Company addCompany(Company company) {
		return mngrCompany.add(company);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Company updateCompany(@PathParam("id") Integer id, Company company) {
		company.setCompanyId(id);
		return mngrCompany.update(company);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCompany(@PathParam("id") Integer id) {
		boolean bRes = mngrCompany.delete(id);
		Response resp = Response.status(Response.Status.BAD_REQUEST).build();
		if(bRes) {
			resp = Response.status(Response.Status.NO_CONTENT).build();
		}
		return resp;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public TopupResponseCollection<Company> getCompanies(@QueryParam("search") String search) {
		List<Company> lstComp = mngrCompany.selectAll(search);
		TopupResponseCollection<Company> respColl = new TopupResponseCollection<Company>(lstComp);
		return respColl;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompanyById(@PathParam("id") Integer id) {
		return mngrCompany.selectById(id);
	}

}
