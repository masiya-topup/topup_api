package com.masiya.topup.manager;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.masiya.topup.dao.CategoryDAO;
import com.masiya.topup.dao.CompanyDAO;
import com.masiya.topup.dao.ServiceDAO;
import com.masiya.topup.model.Category;
import com.masiya.topup.model.Company;
import com.masiya.topup.model.Service;

public class ServiceManager implements IManager<Service, Integer> {
	
	@Inject
	private CategoryDAO daoCategory;

	@Inject
	private CompanyDAO daoCompany;
	
	@Inject
	private ServiceDAO daoService;

	public ServiceManager() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Service add(Service service) {
		Company company = daoCompany.selectById(service.getCompany().getCompanyId());
		service.setCompany(company);
		
		Category category = daoCategory.selectById(service.getCategory().getCategoryId());
		service.setCategory(category);
		
		service.setServiceDate(new Date());
		daoService.add(service);
		return service;
	}

	@Override
	public Service update(Service service) {
		Company company = daoCompany.selectById(service.getCompany().getCompanyId());
		service.setCompany(company);
		
		Category category = daoCategory.selectById(service.getCategory().getCategoryId());
		service.setCategory(category);
		
		return daoService.update(service);
	}

	@Override
	public boolean delete(Integer id) {
		return daoService.delete(id);
	}
	
	@Override
	public List<Service> selectAll(String search) {
		return daoService.selectAll(search);
	}
	
	@Override
	public Service selectById(Integer id) {
		return daoService.selectById(id);
	}
}
