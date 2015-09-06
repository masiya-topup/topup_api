package com.masiya.topup.manager;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.masiya.topup.dao.CompanyDAO;
import com.masiya.topup.dao.CountryDAO;
import com.masiya.topup.model.Company;
import com.masiya.topup.model.Country;

public class CompanyManager implements IManager<Company, Integer> {
	
	@Inject
	private CompanyDAO daoCompany;
	
	@Inject
	private CountryDAO daoCountry;

	public CompanyManager() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Company add(Company company) {
		Country country = daoCountry.selectById(company.getCountry().getCountryId());
		company.setCountry(country);
		
		company.setCompanyDate(new Date());
		daoCompany.add(company);
		return company;
	}

	@Override
	public Company update(Company company) {
		Country country = daoCountry.selectById(company.getCountry().getCountryId());
		company.setCountry(country);
		
		return daoCompany.update(company);
	}

	@Override
	public boolean delete(Integer id) {
		return daoCompany.delete(id);
	}
	
	@Override
	public List<Company> selectAll(String search) {
		return daoCompany.selectAll(search);
	}
	
	@Override
	public Company selectById(Integer id) {
		return daoCompany.selectById(id);
	}
}
