package com.masiya.topup.manager;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.masiya.topup.dao.CategoryDAO;
import com.masiya.topup.dao.CompanyDAO;
import com.masiya.topup.dao.CountryDAO;
import com.masiya.topup.model.Category;
import com.masiya.topup.model.Company;
import com.masiya.topup.model.Country;

public class CategoryManager implements IManager<Category, Integer> {
	
	@Inject
	private CategoryDAO daoCategory;

	@Inject
	private CompanyDAO daoCompany;
	
	@Inject
	private CountryDAO daoCountry;

	public CategoryManager() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Category add(Category category) {
		Country country = daoCountry.selectById(category.getCountry().getCountryId());
		category.setCountry(country);
		
		Company company = daoCompany.selectById(category.getCompany().getCompanyId());
		category.setCompany(company);
		
		category.setCategoryDate(new Date());
		daoCategory.add(category);
		return category;
	}

	@Override
	public Category update(Category category) {
		Country country = daoCountry.selectById(category.getCountry().getCountryId());
		category.setCountry(country);
		
		Company company = daoCompany.selectById(category.getCompany().getCompanyId());
		category.setCompany(company);
		
		return daoCategory.update(category);
	}

	@Override
	public boolean delete(Integer id) {
		return daoCategory.delete(id);
	}
	
	@Override
	public List<Category> selectAll(String search) {
		return daoCategory.selectAll(search);
	}
	
	@Override
	public Category selectById(Integer id) {
		return daoCategory.selectById(id);
	}
}
