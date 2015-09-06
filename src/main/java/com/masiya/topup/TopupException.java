package com.masiya.topup;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.masiya.topup.model.AppException;

@Provider
public class TopupException implements ExceptionMapper<Throwable> {
	final static Logger logger = LogManager.getLogger(TopupException.class);
	@Override
	public Response toResponse(Throwable ex) {
		AppException appExc = null;
		if(ex instanceof WebApplicationException) {
			WebApplicationException exW = (WebApplicationException) ex;
			appExc = new AppException(exW);
		} else {
			appExc = new AppException(ex);
		}
		logger.error(ex);
		
		return Response.status(appExc.getStatus())
				.entity(appExc)
				.type(MediaType.APPLICATION_JSON).
				build();
	}
}