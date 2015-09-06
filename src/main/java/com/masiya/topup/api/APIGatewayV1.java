package com.masiya.topup.api;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;

import com.masiya.topup.manager.GatewayManager;
import com.masiya.topup.manager.UserManager;
import com.masiya.topup.manager.UserPhoneManager;
import com.masiya.topup.model.User;
import com.masiya.topup.model.UserPhone;

@Path("/v1/gateway")
public class APIGatewayV1 {
	
	@Inject
	GatewayManager mngrGateway;
	
	@Inject
	UserManager mngrUser;
	
	@Inject
	UserPhoneManager mngrUserPhone;

	public APIGatewayV1() {
	}

	@GET
	@Path("/url")
	@Produces(MediaType.APPLICATION_JSON)
	public String getGatewayUrl(@QueryParam("amount") Double amount, @QueryParam("phoneId") Integer phoneId, @QueryParam("phoneNo") String phoneNo, @QueryParam("phoneType") String phoneType, @QueryParam("email") String userEMail, @QueryParam("service") Integer serviceId) throws ClientProtocolException, IOException  {
		User user = mngrUser.selectByEMail(userEMail);
		if(phoneId != null && phoneNo == null) {
			UserPhone ph = mngrUserPhone.selectById(phoneId);
			phoneNo = ph.getUserPhoneNo();
			phoneType = ph.getUserPhoneType();
		}
		
		return mngrGateway.getPaymentGateway(amount, phoneNo, phoneType, userEMail, serviceId, user);
	}
	
	@POST
	@Path("/callback/success")
	public String postSuccessCallback(@FormParam("paymentid") String paymentId, @FormParam("result") String result, @FormParam("auth") String auth,
									@FormParam("ref") String ref, @FormParam("postdate") String date, @FormParam("transid") String transId,
									@FormParam("trackid") Integer trackId, @FormParam("udf1") String phoneNo, @FormParam("udf2") String phoneType,
									@FormParam("udf3") String email, @FormParam("udf4") Integer serviceId, @FormParam("udf5") Integer userId)  {
		return mngrGateway.processTransaction(paymentId, result, auth, ref, date, transId, trackId, phoneNo, phoneType, email, serviceId, userId);
	}
	
	@POST
	@Path("/callback/error")
	public String postErrorCallback(@FormParam("trackid") Integer trackId)  {
		return mngrGateway.failedTransaction(trackId);
	}
	
	@GET
	@Path("/callback/error")
	public String getErrorCallback(@QueryParam("trackid") Integer trackId)  {
		return mngrGateway.failedTransaction(trackId);
	}
}
