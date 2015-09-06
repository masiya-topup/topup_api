package com.masiya.topup.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.masiya.topup.mapper.TransactionMapper;
import com.masiya.topup.model.Transaction;

public class TransactionDAO extends _GenericDAO<Transaction, Integer, TransactionMapper> {

	public TransactionDAO() {
		super(TransactionMapper.class);
	}
	
	public List<Transaction> selectSearchDate(String search, Date startDate, Date endDate) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			TransactionMapper mpr = session.getMapper(TransactionMapper.class);
			search = (search!=null? search+"%": search);
			List<Transaction> lst = mpr.getSearchDate(search, startDate, endDate);
			return lst;
		} finally {
			session.close();
		}
	}

}
