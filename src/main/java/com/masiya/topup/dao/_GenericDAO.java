package com.masiya.topup.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.masiya.topup.mapper._IMapper;

public class _GenericDAO<T, PK, M extends _IMapper<T, PK>> extends _BaseDAO {
	private Class<M> mprType;

	public _GenericDAO(Class<M> type) {
		super();
		this.mprType = type;
	}

	public List<T> selectAll(String search) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			M mpr = (M) session.getMapper(mprType);
			search = (search!=null? search+"%": search);
			return mpr.getAll(search);
		} finally {
			session.close();
		}
	}
	
	public T selectById(PK id) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			M mpr = (M) session.getMapper(mprType);
			return mpr.getById(id);
		} finally {
			session.close();
		}
	}
	
	public T add(T elem) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			M mpr = (M) session.getMapper(mprType);
			mpr.add(elem);
			session.commit();
		} finally {
			session.close();
		}
		return elem;
	}
	
	public T update(T elem) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			M mpr = (M) session.getMapper(mprType);
			mpr.update(elem);
			session.commit();
		} finally {
			session.close();
		}
		return elem;
	}
	
	public boolean delete(PK id) {
		SqlSession session = getSqlSessionFactory().openSession();
		boolean bRes = false;
		try {
			M mpr = (M) session.getMapper(mprType);
			mpr.delete(id);
			session.commit();
			bRes = true;
		} finally {
			session.close();
		}
		return bRes;
	}
	
}
