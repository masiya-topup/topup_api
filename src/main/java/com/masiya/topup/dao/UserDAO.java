package com.masiya.topup.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.masiya.topup.mapper.UserMapper;
import com.masiya.topup.model.User;

public class UserDAO extends _GenericDAO<User, Integer, UserMapper> {

	public UserDAO() {
		super(UserMapper.class);
	}

	public List<User> selectAllByRole(String role) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			UserMapper mpr = session.getMapper(UserMapper.class);
			return mpr.getAllByRole(role);
		} finally {
			session.close();
		}
	}
	
	public List<User> selectAllByRoleCondition(String role, String search) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			UserMapper mpr = session.getMapper(UserMapper.class);
			search = (search!=null? search+"%": search);
			return mpr.getAllByRoleCondition(role, search);
		} finally {
			session.close();
		}
	}
	
	public User selectByEMail(String email) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			UserMapper mpr = session.getMapper(UserMapper.class);
			return mpr.getByEMail(email);
		} finally {
			session.close();
		}
	}
	
	public User selectByLogin(String login) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			UserMapper mpr = session.getMapper(UserMapper.class);
			return mpr.getByLogin(login);
		} finally {
			session.close();
		}
	}
	
}
