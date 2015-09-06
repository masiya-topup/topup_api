package com.masiya.topup.manager;

import java.util.List;

import javax.inject.Inject;

import com.masiya.topup.dao.EventLogDAO;
import com.masiya.topup.dao.UserDAO;
import com.masiya.topup.model.EventLog;
import com.masiya.topup.model.User;

public class EventLogManager implements IManager<EventLog, Integer> {
	@Inject
	private UserDAO daoUser;
	
	@Inject
	private EventLogDAO daoEventLog;
	
	public EventLogManager() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public EventLog add(EventLog el) {
		if(el.getUser() != null) {
			User usr = daoUser.selectById(el.getUser().getUserId());
			el.setUser(usr);
		}
		if(el.getOwner() != null) {
			User ownr = daoUser.selectById(el.getOwner().getUserId());
			el.setOwner(ownr);
		}
		
		daoEventLog.add(el);
		return el;
	}

	@Override
	public EventLog update(EventLog el) {
		
		return daoEventLog.update(el);
	}

	@Override
	public boolean delete(Integer id) {
		return daoEventLog.delete(id);
	}
	
	@Override
	public List<EventLog> selectAll(String search) {
		return daoEventLog.selectAll(search);
	}

	@Override
	public EventLog selectById(Integer id) {
		return daoEventLog.selectById(id);
	}

	public List<EventLog> selectAllByType(String type, User usr) {
		return daoEventLog.selectAllByType(type, usr);
	}

	public List<EventLog> selectAllByTypeCondition(String type, User usr, String search) {
		return daoEventLog.selectAllByTypeCondition(type, usr, search);
	}
}
