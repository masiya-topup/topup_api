package com.masiya.topup.dao;

import org.apache.ibatis.session.SqlSessionFactory;


public abstract class _BaseDAO {
	protected SqlSessionFactory sqlSessionFactory;

	public _BaseDAO() {
		sqlSessionFactory = DBConnectionFactory.getSqlSessionFactory();
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

}
