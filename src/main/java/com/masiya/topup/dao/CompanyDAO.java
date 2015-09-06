package com.masiya.topup.dao;

import com.masiya.topup.mapper.CompanyMapper;
import com.masiya.topup.model.Company;

public class CompanyDAO extends _GenericDAO<Company, Integer, CompanyMapper> {

	public CompanyDAO() {
		super(CompanyMapper.class);
	}

}
