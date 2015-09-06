package com.masiya.topup.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.FormParam;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiya.topup.TopupConstants;
import com.masiya.topup.model.EventLog;
import com.masiya.topup.model.EventLog.LogType;
import com.masiya.topup.model.Service;
import com.masiya.topup.model.Transaction;
import com.masiya.topup.model.User;
import com.masiya.topup.model.UserPhone;
import com.masiya.topup.util.EventUtil;
import com.masiya.topup.util.MailUtil;

public class GatewayManager implements IManager<Transaction, Integer> {
	final static Logger logger = LogManager.getLogger(GatewayManager.class);

	@Inject
	TransactionManager mngrTransaction;
	
	@Inject
	ServiceManager mngrService;
	
	@Inject
	UserPhoneManager mngrPhone;
	
	@Inject
	EventUtil mngrEventUtil;
	

	public GatewayManager() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String getPaymentGateway(Double amount, String phoneNo, String phoneType, String email, Integer serviceId, User user) throws ClientProtocolException, IOException {
		logger.info("getPaymentGateway# Amount:"+ amount + ", PhoneNo:" + phoneNo + ", PhoneType:" + phoneType + ", EMail:" + email + ", ServiceId:" + serviceId);
		Service svc = mngrService.selectById(serviceId);
		
		Transaction tx = new Transaction();
		tx.setTransactionAmount(amount);
		tx.setService(svc);
		tx.setCompany(svc.getCompany());
		tx.setCategory(svc.getCategory());
		
		tx.setUserEmail(email);
		
		UserPhone ph = mngrPhone.selectByNumber(phoneNo);
		if(ph == null) {
			ph = new UserPhone();
			ph.setUserPhoneNo(phoneNo);
			ph.setUserPhoneType(phoneType);
//			if(user != null) {
//				ph.setUser(user);
//			}
			ph = mngrPhone.add(ph);
		}
		tx.setPhone(ph);
		
		tx.setUser(user);
		tx.setTransactionType("creditcard");
		tx.setTransactionSystem("KNET");
		tx = mngrTransaction.add(tx);
		
		HttpClient httpclient = HttpClientBuilder.create().build();
		
		HttpPost httpPost = new HttpPost("https://pg.masiya.net/paygateway/RequestPaymentInitServlet");
		httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
		
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("userid", "topup"));
		urlParameters.add(new BasicNameValuePair("password", "BiEbu5FA5neo"));
		urlParameters.add(new BasicNameValuePair("actionURL", "http://"+TopupConstants.HOST_API+"/topup/api/v1/gateway/callback/success"));
		urlParameters.add(new BasicNameValuePair("errorURL", "http://"+TopupConstants.HOST_API+"/topup/api/v1/gateway/callback/error"));
		urlParameters.add(new BasicNameValuePair("amount", amount.toString()));
		urlParameters.add(new BasicNameValuePair("trackid", tx.getTransactionId().toString()));
		urlParameters.add(new BasicNameValuePair("udf1", phoneNo));
		urlParameters.add(new BasicNameValuePair("udf2", phoneType));
		urlParameters.add(new BasicNameValuePair("udf3", email));
		urlParameters.add(new BasicNameValuePair("udf4", svc.getServiceId().toString()));
		urlParameters.add(new BasicNameValuePair("udf5", (user!=null? user.getUserId().toString(): "")));
	 
		httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
		 
		HttpResponse response = null;
		response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		BufferedReader rd = null;
		rd = new BufferedReader(new InputStreamReader(entity.getContent()));
		
		String line, content="";
		while ((line = rd.readLine()) != null) {
			content += line;
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String,String> map = new HashMap<String,String>();
		map = mapper.readValue(content, new TypeReference<HashMap<String,String>>(){});
		logger.info(map);
		tx.setTransactionPaymentId(map.get("paymentid"));
		mngrTransaction.update(tx);
		return content;
	}
	
	public String processTransaction(String paymentId, String result, String auth, String ref,
			String date, String transId, Integer trackId, String phoneNo, String phoneType, String email,
			Integer serviceId, Integer userId) {
		String returnUrl = "http://"+TopupConstants.HOST_WEB+"/callback/topup/error";
		logger.info("processTransaction# PaymentId:"+ paymentId + ", Result:" + result + ", Auth:" + auth + ", Ref:" + ref + ", Date:" + date + ", TransId:" + transId + ", TrackId:" + trackId + ", PhoneNo:" + phoneNo + ", PhoneType:" + phoneType + ", Email:" + email + ", ServiceId:" + serviceId + ", UserId:" + userId);
		Transaction tx = mngrTransaction.selectById(trackId);
		tx.setTransactionPaymentId(paymentId);
		tx.setTransactionRefId(ref);
		tx.setTransactionTrackId(transId);
		tx.setTransactionStatus("failed");
		if(result != null && result.equals("CAPTURED")) {
			tx.setTransactionStatus("success");
			returnUrl = "http://104.236.199.74/callback/topup/receipt";
		}
		
		String desc = tx.getTransactionStatus() + " | " + tx.getPhone().getUserPhoneNo() + "with " + tx.getTransactionAmount() + " KD";
		mngrEventUtil.log(LogType.user, "Transaction: Topup", desc, tx.getUser(), tx.getUser());
		VelocityContext context = new VelocityContext();
		StringWriter writer = new StringWriter();
		String  mailTemplate = "Dear $receiverName,"
				+ "\n\nThanks for topping up your phone using our service\n"
				+ "\nThe transaction parameters are:\n"
				+ "\nResult: $result"
				+ "\nDate: $date"
				+ "\nAmount: $amount KD"
				+ "\nTrack Id: $trackId"
				+ "\nPayment Id: $payementId" 
				+ "\nReference Id: $referenceId" 
				+ "\n\nRegards,"
				+ "\n\tMasiya Topup";
		context.put("receiverName", tx.getUserEmail());
		context.put("result", tx.getTransactionStatus());
		context.put("date", tx.getTransactionDate().toString());
		context.put("amount", tx.getTransactionAmount().toString());
		context.put("trackId", tx.getTransactionId());
		context.put("payementId", tx.getTransactionPaymentId());
		context.put("referenceId", tx.getTransactionRefId());
		Velocity.evaluate(context, writer, "MailTopup", mailTemplate);
		MailUtil.send(tx.getUserEmail(), "Topup "+tx.getTransactionStatus(), writer.toString(), "text/plain");
		mngrTransaction.update(tx);
		
		returnUrl = returnUrl + "/" + trackId.toString();
		
		return returnUrl;
	}

	public String failedTransaction(Integer trackId) {
		String returnUrl = "http://"+TopupConstants.HOST_WEB+"/callback/topup/error";
		logger.info("failedTransaction# TrackId:"+ trackId);
		Transaction tx = mngrTransaction.selectById(trackId);
		if(tx != null) {
			tx.setTransactionStatus("failed");
			mngrTransaction.update(tx);
			logger.info("Failed: " + tx.getTransactionPaymentId());
		}
		String desc = tx.getTransactionStatus() + " | " + tx.getPhone().getUserPhoneNo() + "with " + tx.getTransactionAmount() + " KD";
		mngrEventUtil.log(LogType.user, "Transaction: Topup", desc, tx.getUser(), tx.getUser());
		VelocityContext context = new VelocityContext();
		StringWriter writer = new StringWriter();
		String  mailTemplate = "Dear $receiverName,"
				+ "\n\nAn error is occured when recharge your phone number. Please contact administrator to fix the problem\n"
				+ "Regards,\n"
				+ "Masiya Topup";
		context.put("receiverName", tx.getUserEmail());
//		context.put("result", tx.getTransactionStatus());
//		context.put("date", tx.getTransactionDate().toString());
//		context.put("amount", tx.getTransactionAmount().toString());
//		context.put("trackId", tx.getTransactionId());
//		context.put("payementId", tx.getTransactionPaymentId());
//		context.put("referenceId", tx.getTransactionRefId());
		Velocity.evaluate(context, writer, "MailTopup", mailTemplate);
		MailUtil.send(tx.getUserEmail(), "Topup "+tx.getTransactionStatus(), writer.toString(), "text/plain");
		
		returnUrl = returnUrl + "/" + trackId.toString();
		
		return returnUrl;
	}


	@Override
	public Transaction add(Transaction elem) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Transaction update(Transaction elem) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<Transaction> selectAll(String search) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Transaction selectById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}
