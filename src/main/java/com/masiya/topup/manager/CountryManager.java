package com.masiya.topup.manager;

import java.util.List;

import javax.inject.Inject;

import com.masiya.topup.dao.CountryDAO;
import com.masiya.topup.model.Country;

public class CountryManager implements IManager<Country, Integer> {
	@Inject
	private CountryDAO daoCountry;

	public CountryManager() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Country add(Country elem) {
		return null;
	}

	@Override
	public Country update(Country elem) {
		return null;
	}

	@Override
	public boolean delete(Integer id) {
		return false;
	}

	@Override
	public Country selectById(Integer id) {
		return daoCountry.selectById(id);
	}

	@Override
	public List<Country> selectAll(String search) {
		return daoCountry.selectAll(search);
	}
}
