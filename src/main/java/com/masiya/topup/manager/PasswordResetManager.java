package com.masiya.topup.manager;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.masiya.topup.dao.PasswordResetDAO;
import com.masiya.topup.model.PasswordReset;

public class PasswordResetManager implements IManager<PasswordReset, Integer> {
	final static Logger logger = LogManager.getLogger(PasswordResetManager.class);
	
	@Inject
	private PasswordResetDAO daoPasswordReset;

	public PasswordResetManager() {
	}

	@Override
	public PasswordReset add(PasswordReset user) {
		daoPasswordReset.add(user);
		return user;
	}

	@Override
	public PasswordReset update(PasswordReset user) {
		return daoPasswordReset.update(user);
	}

	@Override
	public boolean delete(Integer id) {
		return daoPasswordReset.delete(id);
	}

	public PasswordReset selectByHash(String hashKey) {
		logger.debug("selectByHash: " + hashKey);
		return daoPasswordReset.selectByHash(hashKey);
	}

	@Override
	public List<PasswordReset> selectAll(String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PasswordReset selectById(Integer id) {
		return daoPasswordReset.selectById(id);
	}

}
