package com.masiya.topup.util;

import javax.inject.Inject;

import com.masiya.topup.manager.EventLogManager;
import com.masiya.topup.model.EventLog;
import com.masiya.topup.model.User;

public class EventUtil {
	@Inject
	EventLogManager mngrEventLog;
	
	public EventLog log(EventLog.LogType type, String title, String desc, User user, User owner) {
		EventLog el = new EventLog();
		el.setEventLogType(type);
		el.setEventLogTitle(title);
		el.setEventLogDetails(desc);
		el.setUser(user);
		el.setOwner(owner);
		el = mngrEventLog.add(el);
		return el;
	}
	
	public EventLog updateLog(EventLog el) {
		el = mngrEventLog.update(el);
		return el;
	}
}
