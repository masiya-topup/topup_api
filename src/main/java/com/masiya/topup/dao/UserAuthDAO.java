package com.masiya.topup.dao;

import org.apache.ibatis.session.SqlSession;

import com.masiya.topup.mapper.UserAuthMapper;
import com.masiya.topup.model.UserAuth;

public class UserAuthDAO extends _GenericDAO<UserAuth, Integer, UserAuthMapper> {

	public UserAuthDAO() {
		super(UserAuthMapper.class);
	}
	
	public UserAuth selectByLoginPwd(String login, String password) {
		SqlSession session = getSqlSessionFactory().openSession();
		UserAuth ua = null;
		try {
			UserAuthMapper mpr = session.getMapper(UserAuthMapper.class);
			ua = mpr.getByLoginPwd(login, password);
		} finally {
			session.close();
		}
		return ua;
	}
	
	public UserAuth selectByToken(String token) {
		SqlSession session = getSqlSessionFactory().openSession();
		UserAuth ua = null;
		try {
			UserAuthMapper mpr = session.getMapper(UserAuthMapper.class);
			ua = mpr.getByToken(token);
		} finally {
			session.close();
		}
		return ua;
	}

}
