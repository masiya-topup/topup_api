package com.masiya.topup.manager;

import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;

import com.masiya.topup.dao.UserAuthDAO;
import com.masiya.topup.model.EventLog;
import com.masiya.topup.model.EventLog.LogType;
import com.masiya.topup.model.User;
import com.masiya.topup.model.UserAuth;
import com.masiya.topup.util.EventUtil;

public class UserAuthManager {
	@Inject
	UserAuthDAO daoUserAuth;
	@Inject
	EventUtil mngrEventUtil;
	
	
	private UserAuth doLogin(String login, String pwd) {
		UserAuth ua = daoUserAuth.selectByLoginPwd(login, pwd);
		if(ua == null) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		UserAuth uaNew = new UserAuth(ua.getUser());
		uaNew.setUserAuthLastDate(ua.getUserAuthDate());
		return daoUserAuth.add(uaNew);
	}
	
	public UserAuth login(String authString) {
		authString = authString.replaceFirst("[B|b]asic ", "");
		byte[] decodedBytes = DatatypeConverter.parseBase64Binary(authString);
		if(decodedBytes == null || decodedBytes.length == 0) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		String[] arr = new String(decodedBytes).split(":", 2);
		UserAuth ua = doLogin(arr[0], arr[1]);
		if(ua.getUser().getUserRole().equals("user")) {
			EventLog el = mngrEventUtil.log(LogType.user, "Login", (new Date()).toString(), ua.getUser(), ua.getUser());
		} else if(ua.getUser().getUserRole().equals("admin_customer")) {
			EventLog el = mngrEventUtil.log(LogType.customercare, "Login", (new Date()).toString(), ua.getUser(), ua.getUser());
		}
		return ua;
	}
	
	public boolean logout(String authString) {
		authString = authString.replaceFirst("[B|b]earer ", "");
		
		UserAuth ua = daoUserAuth.selectByToken(authString);
		if(ua == null) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		ua.setUserAuthStatus((byte) 0);
		daoUserAuth.update(ua);
		
		if(ua.getUser().getUserRole().equals("user")) {
			EventLog el = mngrEventUtil.log(LogType.user, "Logout", (new Date()).toString(), ua.getUser(), ua.getUser());
		} else if(ua.getUser().getUserRole().equals("admin_customer")) {
			EventLog el = mngrEventUtil.log(LogType.customercare, "Logout", (new Date()).toString(), ua.getUser(), ua.getUser());
		}
		return true;
	}
	
	public UserAuth getByToken(String token) {
		UserAuth userLogin = daoUserAuth.selectByToken(token);
		return userLogin;
	}

	public UserAuth register(User user) {
		UserAuth ua = doLogin(user.getUserLogin(), user.getUserPassword());
		
		if(ua.getUser().getUserRole().equals("user")) {
			EventLog el = mngrEventUtil.log(LogType.user, "Register", (new Date()).toString(), ua.getUser(), ua.getUser());
		} else if(ua.getUser().getUserRole().equals("admin_customer")) {
			EventLog el = mngrEventUtil.log(LogType.customercare, "Register", (new Date()).toString(), ua.getUser(), ua.getUser());
		}
		
		return ua;
	}
}
