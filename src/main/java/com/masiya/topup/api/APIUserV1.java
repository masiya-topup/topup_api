package com.masiya.topup.api;

import java.security.Principal;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.apache.velocity.VelocityContext;

import com.masiya.topup.TopupConstants;
import com.masiya.topup.TopupResponseCollection;
import com.masiya.topup.exception.GenericTopupException;
import com.masiya.topup.manager.PasswordResetManager;
import com.masiya.topup.manager.UserAuthManager;
import com.masiya.topup.manager.UserManager;
import com.masiya.topup.manager.UserPhoneManager;
import com.masiya.topup.model.EventLog;
import com.masiya.topup.model.PasswordReset;
import com.masiya.topup.model.User;
import com.masiya.topup.model.UserAuth;
import com.masiya.topup.model.UserDetails;
import com.masiya.topup.model.UserPhone;
import com.masiya.topup.util.EventUtil;
import com.masiya.topup.util.MailUtil;
import com.masiya.topup.util.VelocityUtil;
import com.masiya.topup.util.VelocityUtil.TemplateType;
//import com.wordnik.swagger.annotations.Api;

@Path("/v1/users")
//@Api(value = "/users", description = "User APIs!")
public class APIUserV1 {

	@Inject
	UserManager mngrUser;
	
	@Inject
	UserPhoneManager mngrUserPhone;
	
	@Inject
	UserAuthManager mngrUserLogin;
	
	@Inject
	PasswordResetManager mngrPasswordReset;
	
	@Inject
	EventUtil mngrEventUtil;
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public UserAuth postLogin(@NotNull @HeaderParam("Authorization") String authString, @Context SecurityContext context) {
		UserAuth usrLogin = mngrUserLogin.login(authString);
		return usrLogin;
	}
	
	@POST
	@Path("/logout")
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	public boolean postLogout(@NotNull @HeaderParam("Authorization") String authString, @Context SecurityContext context) {
		System.out.println(context.getUserPrincipal().getName());
		System.out.println(context.getClass().getName());
		return mngrUserLogin.logout(authString);
	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public UserAuth postRegister(@NotNull UserDetails ud, @Context SecurityContext context) {
		User usr = mngrUser.selectByEMail(ud.getUser().getUserEmail());
		if(usr != null) {
			throw new GenericTopupException("User already exists with same email.", Status.BAD_REQUEST);
		}
		usr = mngrUser.selectByEMail(ud.getUser().getUserLogin());
		if(usr != null) {
			throw new GenericTopupException("User already exists with same login.", Status.BAD_REQUEST);
		}
		if(ud.getUserPhones().isEmpty()) {
			throw new GenericTopupException("Phone is required", Status.BAD_REQUEST);
		}
		UserPhone up = mngrUserPhone.selectByNumber(ud.getUserPhones().get(0).getUserPhoneNo());
		if(up != null) {
			throw new GenericTopupException("Phone already exists.", Status.BAD_REQUEST);
		}
		up = ud.getUserPhones().get(0);
		
		usr = mngrUser.add(ud.getUser());
		up.setUser(usr);
		mngrUserPhone.add(up);
		UserAuth usrLogin = mngrUserLogin.register(ud.getUser());
		MailUtil.send(usrLogin.getUser().getUserEmail(), "Welcome "+usrLogin.getUser().getUserFirstName(), "Thanks for Registering with us", "text/plain");
		return usrLogin;
	}
	
	@POST
	@Path("/password/reset")
	@PermitAll
	public Response postResetPasswordLink(@NotNull @QueryParam("email") String email, @Context SecurityContext context) {
		User usr = mngrUser.selectByEMail(email);
		Response resp = null;
		if(usr == null) {
			return Response.status(Status.BAD_REQUEST).entity("Wrong email").build();
		}
		PasswordReset pr = new PasswordReset();
		pr.setUser(usr);
		mngrPasswordReset.add(pr);
		pr = mngrPasswordReset.selectById(pr.getPasswordResetId());
		EventLog el = mngrEventUtil.log(EventLog.LogType.user, "Password Reset", "Initiated: "+pr.getPasswordResetHash(), usr, usr);
		VelocityContext vc = new VelocityContext();
		vc.put("username", usr.getUserLogin());
		vc.put("host", TopupConstants.HOST_WEB);
		vc.put("token", pr.getPasswordResetHash());
		String dataText = VelocityUtil.getTemplate(TemplateType.PasswordResetText, vc);
		String dataHtml = VelocityUtil.getTemplate(TemplateType.PasswordResetHtml, vc);
		boolean bSent = MailUtil.send(usr, "Password Reset", dataText, dataHtml);
		if(bSent) {
			resp = Response.ok().entity("Password Reset link sent").build();
		} else {
			throw new GenericTopupException("Password Reset link could not be sent - mail server issue", Status.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	@PUT
	@Path("/password/reset")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@PermitAll
	public Response updateUserPassword(@NotNull @QueryParam("token") String hashKey, @NotNull @FormParam("newPwd") String newPwd, @Context SecurityContext context) {
		PasswordReset pr = mngrPasswordReset.selectByHash(hashKey);
		if(pr == null) {
			return Response.status(Status.BAD_REQUEST).entity("Wrong token").build();
		}
		User usr = pr.getUser();
//		if(!usr.getUserPassword().equals(oldPwd)) {
//			return Response.status(Status.BAD_REQUEST).entity("Wrong password").build();
//		}
		usr.setUserPassword(newPwd);
		mngrUser.update(usr);
		EventLog el = mngrEventUtil.log(EventLog.LogType.user, "Password Reset", "Done: "+pr.getPasswordResetHash(), usr, usr);
		return Response.ok().build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin_all", "admin_customer"})
	public UserAuth postRegister1(@NotNull User user, @Context SecurityContext context) {
		mngrUser.add(user);
		UserAuth usrLogin = mngrUserLogin.register(user);
		MailUtil.send(usrLogin.getUser().getUserEmail(), "Welcome "+usrLogin.getUser().getUserFirstName(), "Thanks for Registering with us", "text/plain");
		return usrLogin;
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin_all", "admin_customer", "user"})
	public User updateUser(@NotNull @PathParam("id") Integer id, @NotNull User user, @QueryParam("oldPwd") String oldPwd, @Context SecurityContext context) {
//		if(context.isUserInRole("user")) {
//			Principal prin = context.getUserPrincipal();
//			User usr = (User) prin;
//		}
		User usrOld = mngrUser.selectById(user.getUserId());
		if(oldPwd != null && !oldPwd.trim().isEmpty() && !usrOld.getUserPassword().equals(oldPwd)) {
			throw new WebApplicationException("Old Password not matching", Status.BAD_REQUEST);
		}
		user = mngrUser.update(user);
		MailUtil.send(user.getUserEmail(), "Profile updated", "Your profile was updated, "+user.getUserFirstName(), "text/plain");
		return user;
	}
	
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin_all", "admin_customer"})
	public Response deleteUser(@NotNull @PathParam("id") Integer id, @NotNull User user, @Context SecurityContext context) {
		boolean bRes = mngrUser.delete(id);
		Response resp = Response.status(Response.Status.BAD_REQUEST).build();
		if(bRes) {
			resp = Response.status(Response.Status.NO_CONTENT).build();
		}
		return resp;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin_all", "admin_customer"})
	public TopupResponseCollection<User> getUsers(@QueryParam("search") String search, @QueryParam("role") String role, @Context SecurityContext context) {
		List<User> lstUsr = null;
		if((role != null && !role.isEmpty()) &&
			(search != null && !search.isEmpty())) {
			lstUsr = mngrUser.selectAllByRoleCondition(role, search);
		} else if((role != null && !role.isEmpty()) &&
				(search == null || search.isEmpty())) {
			lstUsr = mngrUser.selectAllByRole(role);
		} else {
			lstUsr = mngrUser.selectAll(search);
		}
		
		TopupResponseCollection<User> trc = new TopupResponseCollection<User>(lstUsr);
		return trc;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public User getUserById(@PathParam("id") Integer id, @Context SecurityContext context) {
		return mngrUser.selectById(id);
	}
}
