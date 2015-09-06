package com.masiya.topup.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.masiya.topup.mapper.PasswordResetMapper;
import com.masiya.topup.model.PasswordReset;


public class PasswordResetDAO extends _GenericDAO<PasswordReset, Integer, PasswordResetMapper> {

	public PasswordResetDAO() {
		super(PasswordResetMapper.class);
	}

	public PasswordReset selectByHash(String hashKey) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			PasswordResetMapper mpr = session.getMapper(PasswordResetMapper.class);
			return mpr.getByHash(hashKey);
		} finally {
			session.close();
		}
	}
	
}
