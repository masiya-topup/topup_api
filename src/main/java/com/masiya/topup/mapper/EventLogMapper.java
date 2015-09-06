package com.masiya.topup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.masiya.topup.model.EventLog;
import com.masiya.topup.model.User;

public interface EventLogMapper extends _IMapper<EventLog, Integer> {

	List<EventLog> getAllByType(@Param("type") String type, @Param("user") User usr);

	List<EventLog> getAllByRoleCondition(@Param("type") String type, @Param("user") User usr, @Param("search") String search);
}
