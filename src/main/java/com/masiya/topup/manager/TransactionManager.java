package com.masiya.topup.manager;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.masiya.topup.dao.CategoryDAO;
import com.masiya.topup.dao.CompanyDAO;
import com.masiya.topup.dao.ServiceDAO;
import com.masiya.topup.dao.TransactionDAO;
import com.masiya.topup.dao.UserDAO;
import com.masiya.topup.dao.UserPhoneDAO;
import com.masiya.topup.model.Service;
import com.masiya.topup.model.Transaction;
import com.masiya.topup.model.User;
import com.masiya.topup.model.UserPhone;

public class TransactionManager implements IManager<Transaction, Integer> {
	
	@Inject
	private CategoryDAO daoCategory;

	@Inject
	private CompanyDAO daoCompany;
	
	@Inject
	private ServiceDAO daoService;
	
	@Inject
	private UserDAO daoUser;
	
	@Inject
	private UserPhoneDAO daoUserPhone;

	@Inject
	private TransactionDAO daoTransaction;

	public TransactionManager() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Transaction add(Transaction tx) {
		Service service = daoService.selectById(tx.getService().getServiceId());
		tx.setService(service);
		tx.setCompany(service.getCompany());
		tx.setCategory(service.getCategory());
		
		if(tx.getUser() != null) {
			User usr = daoUser.selectById(tx.getUser().getUserId());
			tx.setUser(usr);
		}
		
		UserPhone ph = daoUserPhone.selectByNumber(tx.getPhone().getUserPhoneNo());
		if(ph == null) {
			ph = daoUserPhone.add(tx.getPhone());
		}
		tx.setPhone(ph);
		
		tx.setTransactionDate(new Date());
		daoTransaction.add(tx);
		return tx;
	}

	@Override
	public Transaction update(Transaction tx) {
		Service service = daoService.selectById(tx.getService().getServiceId());
		tx.setService(service);
		tx.setCompany(service.getCompany());
		tx.setCategory(service.getCategory());
		
		if(tx.getUser() != null) {
			User usr = daoUser.selectById(tx.getUser().getUserId());
			tx.setUser(usr);
		}
		
		UserPhone ph = daoUserPhone.selectByNumber(tx.getPhone().getUserPhoneNo());
		if(ph == null) {
			ph = daoUserPhone.selectById(tx.getPhone().getUserPhoneId());
		}
		tx.setPhone(ph);
		
		return daoTransaction.update(tx);
	}

	@Override
	public boolean delete(Integer id) {
		return daoTransaction.delete(id);
	}
	
	@Override
	public List<Transaction> selectAll(String search) {
		return daoTransaction.selectAll(search);
	}

	public List<Transaction> selectSearchDate(String search, Date startDate, Date endDate) {
		return daoTransaction.selectSearchDate(search, startDate, endDate);
	}
	
	@Override
	public Transaction selectById(Integer id) {
		return daoTransaction.selectById(id);
	}

}
