package com.masiya.topup;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.masiya.topup.dao.CategoryDAO;
import com.masiya.topup.dao.CompanyDAO;
import com.masiya.topup.dao.CountryDAO;
import com.masiya.topup.dao.EventLogDAO;
import com.masiya.topup.dao.PasswordResetDAO;
import com.masiya.topup.dao.ServiceDAO;
import com.masiya.topup.dao.TicketDAO;
import com.masiya.topup.dao.TransactionDAO;
import com.masiya.topup.dao.UserAuthDAO;
import com.masiya.topup.dao.UserDAO;
import com.masiya.topup.dao.UserPhoneDAO;
import com.masiya.topup.manager.CategoryManager;
import com.masiya.topup.manager.CompanyManager;
import com.masiya.topup.manager.CountryManager;
import com.masiya.topup.manager.EventLogManager;
import com.masiya.topup.manager.GatewayManager;
import com.masiya.topup.manager.PasswordResetManager;
import com.masiya.topup.manager.ServiceManager;
import com.masiya.topup.manager.TicketManager;
import com.masiya.topup.manager.TransactionManager;
import com.masiya.topup.manager.UserAuthManager;
import com.masiya.topup.manager.UserManager;
import com.masiya.topup.manager.UserPhoneManager;
import com.masiya.topup.util.EventUtil;

public class TopupApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(UserManager.class).to(UserManager.class);
		bind(UserAuthManager.class).to(UserAuthManager.class);
		bind(UserPhoneManager.class).to(UserPhoneManager.class);
		bind(CompanyManager.class).to(CompanyManager.class);
		bind(CategoryManager.class).to(CategoryManager.class);
		bind(CountryManager.class).to(CountryManager.class);
		bind(ServiceManager.class).to(ServiceManager.class);
		bind(TransactionManager.class).to(TransactionManager.class);
		bind(GatewayManager.class).to(GatewayManager.class);
		bind(TicketManager.class).to(TicketManager.class);
		bind(EventLogManager.class).to(EventLogManager.class);
		bind(PasswordResetManager.class).to(PasswordResetManager.class);
		bind(EventUtil.class).to(EventUtil.class);
		
		bind(UserDAO.class).to(UserDAO.class);
		bind(UserAuthDAO.class).to(UserAuthDAO.class);
		bind(UserPhoneDAO.class).to(UserPhoneDAO.class);
		bind(CompanyDAO.class).to(CompanyDAO.class);
		bind(CategoryDAO.class).to(CategoryDAO.class);
		bind(CountryDAO.class).to(CountryDAO.class);
		bind(ServiceDAO.class).to(ServiceDAO.class);
		bind(TransactionDAO.class).to(TransactionDAO.class);
		bind(TicketDAO.class).to(TicketDAO.class);
		bind(EventLogDAO.class).to(EventLogDAO.class);
		bind(PasswordResetDAO.class).to(PasswordResetDAO.class);
	}
}
