package com.masiya.topup.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.masiya.topup.mapper.UserPhoneMapper;
import com.masiya.topup.model.User;
import com.masiya.topup.model.UserPhone;

public class UserPhoneDAO extends _GenericDAO<UserPhone, Integer, UserPhoneMapper> {

	public UserPhoneDAO() {
		super(UserPhoneMapper.class);
	}
	
	public List<UserPhone> selectAllByUser(User user) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			UserPhoneMapper mpr = session.getMapper(UserPhoneMapper.class);
			return mpr.getAllByUser(user);
		} finally {
			session.close();
		}
	}

	public UserPhone selectByNumber(String phoneNumber) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			UserPhoneMapper mpr = session.getMapper(UserPhoneMapper.class);
			return mpr.getByNumber(phoneNumber);
		} finally {
			session.close();
		}
	}

	public UserPhone selectByPhone(String phoneNo, String phoneType) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			UserPhoneMapper mpr = session.getMapper(UserPhoneMapper.class);
			return mpr.getByPhone(phoneNo, phoneType);
		} finally {
			session.close();
		}
	}
	
}
