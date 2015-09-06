package com.masiya.topup.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.masiya.topup.TopupResponseCollection;
import com.masiya.topup.manager.TransactionManager;
import com.masiya.topup.manager.UserManager;
import com.masiya.topup.model.Transaction;
import com.masiya.topup.model.User;

@Path("/v1/transactions")
public class APITransactionV1 {
	
	@Inject
	TransactionManager mngrTransaction;
	
	@Inject
	UserManager mngrUser;

	public APITransactionV1() {
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Transaction addTransaction(Transaction transaction) {
		return mngrTransaction.add(transaction);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Transaction updateTransaction(@PathParam("id") Integer id, Transaction transaction) {
		transaction.setTransactionId(id);
		return mngrTransaction.update(transaction);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTransaction(@PathParam("id") Integer id) {
		boolean bRes = mngrTransaction.delete(id);
		Response resp = Response.status(Response.Status.BAD_REQUEST).build();
		if(bRes) {
			resp = Response.status(Response.Status.NO_CONTENT).build();
		}
		return resp;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin_all", "admin_finance"})
	public TopupResponseCollection<Transaction> getTransactions(@QueryParam("search") String search, @QueryParam("startDate") String from, @QueryParam("endDate") String to) throws ParseException {
		Date startDate = null, endDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		
		if(from != null) {
			startDate = sdf.parse(from);
		}
		if(to != null) {
			endDate = sdf.parse(to);
		}
		
		List<Transaction> lst = mngrTransaction.selectSearchDate(search, startDate, endDate);
		TopupResponseCollection<Transaction> trc = new TopupResponseCollection<Transaction>(lst);
		return trc;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Transaction getTransactionById(@PathParam("id") Integer id) {
		return mngrTransaction.selectById(id);
	}
}
