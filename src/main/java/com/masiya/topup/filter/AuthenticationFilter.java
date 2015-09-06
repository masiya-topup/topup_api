package com.masiya.topup.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.Priorities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.server.ContainerRequest;

import com.masiya.topup.exception.InvalidTokenException;
import com.masiya.topup.manager.EventLogManager;
import com.masiya.topup.manager.UserAuthManager;
import com.masiya.topup.model.EventLog;
import com.masiya.topup.model.UserAuth;
import com.masiya.topup.util.EventUtil;

@Provider
//@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	final static Logger logger = LogManager.getLogger(AuthenticationFilter.class);
	
	@Inject
	UriInfo uriInfo;
	
	@Inject
	UserAuthManager mngrUserAuth;
	
	@Inject
	EventLogManager mngrEventLog;
	
	@Inject
	EventUtil mngrEventUtil;
	
	@Override
	public void filter(ContainerRequestContext context) throws WebApplicationException {
		ContainerRequest request = (ContainerRequest) context.getRequest();

		// GET, POST, PUT, DELETE, ...
		String method = request.getMethod();
		String path = request.getPath(true);
		
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
		StringBuilder apiParams = new StringBuilder();
		if (queryParams != null && !queryParams.isEmpty()) {
			for (Map.Entry<String, List<String>> keyValuePair : queryParams.entrySet()) {
				List<String> values = keyValuePair.getValue();
				if (values != null && !values.isEmpty()) {
					for (String value : values) {
						apiParams.append(keyValuePair.getKey()).append("=").append(value).append("&");
					}
				}
			}
		}
		MultivaluedMap<String, String> headerParams = request.getHeaders();
		StringBuilder hdrParams = new StringBuilder();
		if (headerParams != null && !headerParams.isEmpty()) {
			for (Map.Entry<String, List<String>> keyValuePair : headerParams.entrySet()) {
				List<String> values = keyValuePair.getValue();
				if (values != null && !values.isEmpty()) {
					for (String value : values) {
						hdrParams.append(keyValuePair.getKey()).append(": ").append(value).append("\n");
					}
				}
			}
		}
		
		String content = "";
		/*
		InputStream in = request.getEntityStream();
		if(in != null) {
			Scanner scnr = new Scanner(in,"UTF-8");
			scnr = scnr.useDelimiter("\\n");
			content = scnr.hasNext() ? scnr.next() : "";
			request.setEntityStream(in);
		}
		//*/
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(request.getEntityStream(), baos);
			byte[] bytes = baos.toByteArray();
			content = new String(bytes, "UTF-8");
			request.setEntityStream(new ByteArrayInputStream(bytes));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		logger.info("Request: " + request.getMethod()
						  + " " + uriInfo.getRequestUri().getPath()
						  + "?" + apiParams.toString()
						  + "\n" + hdrParams.toString()
						  + "\n" + content);
		String json = "{" +
						"'verb':'" + request.getMethod() + "'," +
						"'path':'" + uriInfo.getRequestUri().getPath() + "'," +
						"'query_params':'" + apiParams.toString() + "'," +
						"'header_params':'" + hdrParams.toString() + "'," +
						"'content':'" + content + "'" +
					  "}";
		String eventTitle = request.getMethod() + " " + request.getPath(true);
		EventLog el = mngrEventUtil.log(EventLog.LogType.api, eventTitle, json, null, null);
		
		//We do allow wadl to be retrieve
		if(method.equals("GET") && (path.equals("application.wadl") || path.equals("application.wadl/xsd0.xsd"))) {
			return;
		}
		if(method.equals("POST") && (path.equals("v1/users/register"))) {
			return;
		}
		if(method.equals("GET") && (path.equals("v1/countries"))) {
			return;
		}
		if(method.equals("GET") && (path.equals("v1/gateway/url"))) {
			return;
		}
		if(path.equals("v1/gateway/callback/success") || path.equals("v1/gateway/callback/error")) {
			return;
		}
		if(path.contains("v1/users/password")) {
			return;
		}
		if(method.equals("GET") && (path.contains("v1/transactions/"))) {
			return;
		}
		if(method.equals("GET") && (path.equals("v1/services"))) {
			return;
		}
		String token = request.getHeaderString("Authorization");
		if (token == null) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		if(path.equals("v1/users/login") && token.contains("Basic")) {
			return;
		}
		token = token.replaceFirst("[B|b]earer ", "");
		UserAuth userLogin = mngrUserAuth.getByToken(token);
		
		if(userLogin == null) {
			throw new InvalidTokenException();
		}
		
		context.setSecurityContext(new Authorizer(userLogin.getUser()));
		el.setUser(userLogin.getUser());
		el.setOwner(userLogin.getUser());
		//el = mngrEventLog.update(el);
		mngrEventUtil.updateLog(el);
		context.setProperty("User", userLogin.getUser());
		context.setProperty("ApiEvent", el);
		
		if((userLogin.getUser().getUserRole().equals("admin_customer") || userLogin.getUser().getUserRole().equals("user"))
				&& !method.equals("GET")) {
			EventLog elc = new EventLog();
			if(userLogin.getUser().getUserRole().equals("admin_customer")) {
				elc.setEventLogType(EventLog.LogType.customercare);
			} else {
				elc.setEventLogType(EventLog.LogType.user);
			}
			
			elc.setOwner(userLogin.getUser());
			String title = "Added ";
			if(method.equals("PUT")) {
				title = "Updated ";
			} else if(method.equals("DELETE")) {
				title = "Deleted ";
			}
			
			if(path.matches("v1\\/users(\\/\\d+)?")) {
				title += "User Profile";
			} else if(path.matches("v1\\/users\\/\\d+\\/phones(\\/\\d+)?")) {
				title += "User Phone";
			} else if(path.contains("v1/tickets")) {
				title += "Tickets";
			}
			if(!content.isEmpty()) {
				elc.setEventLogTitle(title);
				elc.setEventLogDetails(content);
				mngrEventLog.add(elc);
			}
		}
	}
}
