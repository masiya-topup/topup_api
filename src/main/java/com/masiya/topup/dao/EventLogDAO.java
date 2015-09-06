package com.masiya.topup.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.masiya.topup.mapper.EventLogMapper;
import com.masiya.topup.mapper.UserMapper;
import com.masiya.topup.model.EventLog;
import com.masiya.topup.model.User;

public class EventLogDAO extends _GenericDAO<EventLog, Integer, EventLogMapper> {

	public EventLogDAO() {
		super(EventLogMapper.class);
	}

	public List<EventLog> selectAllByType(String type, User usr) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			EventLogMapper mpr = session.getMapper(EventLogMapper.class);
			return mpr.getAllByType(type, usr);
		} finally {
			session.close();
		}
	}

	public List<EventLog> selectAllByTypeCondition(String type, User usr, String search) {
		SqlSession session = getSqlSessionFactory().openSession();
		try {
			EventLogMapper mpr = session.getMapper(EventLogMapper.class);
			search = (search!=null? search+"%": search);
			return mpr.getAllByRoleCondition(type, usr, search);
		} finally {
			session.close();
		}
	}
}
