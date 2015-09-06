package com.masiya.topup.dao;

import com.masiya.topup.mapper.ServiceMapper;
import com.masiya.topup.model.Service;

public class ServiceDAO extends _GenericDAO<Service, Integer, ServiceMapper> {

	public ServiceDAO() {
		super(ServiceMapper.class);
	}

}
