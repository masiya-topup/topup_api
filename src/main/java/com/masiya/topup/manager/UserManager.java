package com.masiya.topup.manager;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.masiya.topup.dao.UserDAO;
import com.masiya.topup.model.User;

public class UserManager implements IManager<User, Integer> {
	final static Logger logger = LogManager.getLogger(UserManager.class);
	
	@Inject
	private UserDAO daoUser;

	public UserManager() {
	}

	@Override
	public User add(User user) {
		daoUser.add(user);
		return user;
	}

	@Override
	public User update(User user) {
		return daoUser.update(user);
	}

	@Override
	public boolean delete(Integer id) {
		return daoUser.delete(id);
	}

	@Override
	public User selectById(Integer id) {
		logger.debug("selectById: " + id.toString());
		return daoUser.selectById(id);
	}

	@Override
	public List<User> selectAll(String search) {
		return daoUser.selectAll(search);
	}
	
	public List<User> selectAllByRole(String role) {
		return daoUser.selectAllByRole(role);
	}

	public List<User> selectAllByRoleCondition(String role, String search) {
		return daoUser.selectAllByRoleCondition(role, search);
	}
	
	public User selectByEMail(String email) {
		return daoUser.selectByEMail(email);
	}
	
	public User selectByLogin(String login) {
		return daoUser.selectByLogin(login);
	}
}
