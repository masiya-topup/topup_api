package com.masiya.topup.api;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.masiya.topup.manager.CountryManager;
import com.masiya.topup.model.Country;

@Path("/v1/countries")
public class APICountryV1 {
	
	@Inject
	CountryManager mngrCountry;

	public APICountryV1() {
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Country> getCountries(@QueryParam("search") String search) {
		return mngrCountry.selectAll(search);
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Country getCountryById(@PathParam("id") Integer id) {
		return mngrCountry.selectById(id);
	}

}
