package com.masiya.topup.filter;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import com.masiya.topup.model.User;

public class Authorizer implements SecurityContext {

	private User topupUser;
	public User getTopupUser() {
		return topupUser;
	}

	public void setTopupUser(User topupUser) {
		this.topupUser = topupUser;
	}

	private Principal principal;

	public Authorizer(final User webuser) {
		this.topupUser = webuser;
		this.principal = new Principal() {

			@Override
			public String getName() {
				return webuser.getUserLogin();
			}
		};
	}

	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.FORM_AUTH;
	}

	@Override
	public Principal getUserPrincipal() {
		return principal;
	}

	@Override
	public boolean isSecure() {
		return true;
	}

	@Override
	public boolean isUserInRole(String role) {
		return role.equals(topupUser.getUserRole());
	}

}