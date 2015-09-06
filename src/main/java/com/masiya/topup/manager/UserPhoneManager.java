package com.masiya.topup.manager;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.masiya.topup.dao.UserDAO;
import com.masiya.topup.dao.UserPhoneDAO;
import com.masiya.topup.model.User;
import com.masiya.topup.model.UserPhone;

public class UserPhoneManager implements IManager<UserPhone, Integer> {
	final static Logger logger = LogManager.getLogger(UserPhoneManager.class);
	
	@Inject
	UserDAO daoUser;
	
	@Inject
	UserPhoneDAO daoUserPhone;
	
	@Override
	public UserPhone add(UserPhone userPhone) {
		UserPhone up = daoUserPhone.selectByNumber(userPhone.getUserPhoneNo());
		if(up != null) {
			return up;
		}
		if(userPhone.getUser() != null) {
			User usr = daoUser.selectById(userPhone.getUser().getUserId());
			userPhone.setUser(usr);
		}
		
		daoUserPhone.add(userPhone);
		return userPhone;
	}

	@Override
	public UserPhone update(UserPhone userPhone) {
		if(userPhone.getUser() != null) {
			User usr = daoUser.selectById(userPhone.getUser().getUserId());
			userPhone.setUser(usr);
		}
		
		return daoUserPhone.update(userPhone);
	}

	@Override
	public boolean delete(Integer id) {
		return daoUserPhone.delete(id);
	}
	
	@Override
	public List<UserPhone> selectAll(String search) {
		return daoUserPhone.selectAll(search);
	}
	
	public List<UserPhone> selectAllByUser(User user) {
		return daoUserPhone.selectAllByUser(user);
	}
	
	@Override
	public UserPhone selectById(Integer id) {
		return daoUserPhone.selectById(id);
	}
	
	public UserPhone selectByNumber(String phoneNo) {
		return daoUserPhone.selectByNumber(phoneNo);
	}

	public UserPhone selectByPhone(String phoneNo, String phoneType) {
		return daoUserPhone.selectByPhone(phoneNo, phoneType);
	}
}
