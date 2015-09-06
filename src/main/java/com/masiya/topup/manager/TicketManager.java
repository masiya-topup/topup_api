package com.masiya.topup.manager;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.masiya.topup.dao.TicketDAO;
import com.masiya.topup.dao.UserDAO;
import com.masiya.topup.model.Ticket;
import com.masiya.topup.model.User;

public class TicketManager extends BaseManager<Ticket, Integer> {
	
	@Inject
	private TicketDAO daoTicket;
	
	@Inject
	private UserDAO daoUser;

	public TicketManager() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Ticket add(Ticket ticket) {
		User usr = daoUser.selectById(ticket.getUser().getUserId());
		ticket.setUser(usr);
		
		ticket.setTicketOpenDate(new Date());
		daoTicket.add(ticket);
		return ticket;
	}

	@Override
	public Ticket update(Ticket ticket) {
		User usr = daoUser.selectById(ticket.getUser().getUserId());
		ticket.setUser(usr);
		if(ticket.getTicketStatus().contains("resolved")) {
			ticket.setTicketCloseDate(new Date());
		}
		
		return daoTicket.update(ticket);
	}

	@Override
	public boolean delete(Integer id) {
		return daoTicket.delete(id);
	}
	
	@Override
	public List<Ticket> selectAll(String search) {
		return daoTicket.selectAll(search);
	}
	
	@Override
	public Ticket selectById(Integer id) {
		return daoTicket.selectById(id);
	}
}
