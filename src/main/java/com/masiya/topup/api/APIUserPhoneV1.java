package com.masiya.topup.api;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.masiya.topup.TopupResponseCollection;
import com.masiya.topup.manager.UserManager;
import com.masiya.topup.manager.UserPhoneManager;
import com.masiya.topup.model.User;
import com.masiya.topup.model.UserPhone;

@Path("/v1/users/{uid}/phones")
public class APIUserPhoneV1 {

	@Inject
	UserManager mngrUser;
	
	@Inject
	UserPhoneManager mngrUserPhone;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public UserPhone addPhone(@NotNull UserPhone userPhone, @Context SecurityContext context) {
		return mngrUserPhone.add(userPhone);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin_all", "admin_customer", "user"})
	public UserPhone editPhone(@NotNull UserPhone userPhone, @Context SecurityContext context) {
		UserPhone up = mngrUserPhone.selectById(userPhone.getUserPhoneId());
		if(up.getUserPhoneNo().equals(userPhone.getUserPhoneNo()) 
			&& up.getUserPhoneType().equals(userPhone.getUserPhoneType())) {
			return up;
		}
		return mngrUserPhone.update(userPhone);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin_all", "admin_customer", "user"})
	public TopupResponseCollection<UserPhone> getUserPhones(@PathParam("uid") Integer uid, @QueryParam("search") String search, @Context SecurityContext context) {
		User user = mngrUser.selectById(uid);
		List<UserPhone> lst = mngrUserPhone.selectAllByUser(user);
		TopupResponseCollection<UserPhone> trc = new TopupResponseCollection<UserPhone>(lst);
		return trc;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public UserPhone getUserPhoneById(@PathParam("id") Integer id, @Context SecurityContext context) {
		return mngrUserPhone.selectById(id);
	}

}
