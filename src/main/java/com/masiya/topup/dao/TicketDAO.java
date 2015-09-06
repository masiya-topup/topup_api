package com.masiya.topup.dao;

import com.masiya.topup.mapper.TicketMapper;
import com.masiya.topup.model.Ticket;

public class TicketDAO extends _GenericDAO<Ticket, Integer, TicketMapper> {

	public TicketDAO() {
		super(TicketMapper.class);
	}

}
