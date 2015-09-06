package com.masiya.topup.dao;

import com.masiya.topup.mapper.CountryMapper;
import com.masiya.topup.model.Country;

public class CountryDAO extends _GenericDAO<Country, Integer, CountryMapper> {

	public CountryDAO() {
		super(CountryMapper.class);
	}

}
