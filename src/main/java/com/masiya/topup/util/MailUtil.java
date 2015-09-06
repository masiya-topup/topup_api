package com.masiya.topup.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.masiya.topup.TopupConstants;
import com.masiya.topup.model.User;


public class MailUtil {
	final static Logger logger = LogManager.getLogger(MailUtil.class);

	public static boolean send(String to, String subject, String content, String contentType) {
		boolean res = true;
		logger.info("MailUtil::send() "+to + "; " +subject +"; "+content);
		try {
			Properties props = System.getProperties();
			props.setProperty("mail.smtp.host", TopupConstants.HOST_SMTP);
			Session session = Session.getDefaultInstance(props);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(TopupConstants.ADMIN_EMAIL, TopupConstants.ADMIN_NAME));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to, "Mr. User"));
			msg.setSubject(subject);
			msg.setText(content);
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to, "Mr. User"));
			logger.info("MailUtil: " + msg.getAllRecipients()[0].toString() + "; " + msg.getSubject() + "; " + msg.getContentType() + "; " + msg.getContent().toString(), msg);
			Transport.send(msg);
		} catch (AddressException e) {
			logger.error("Mail not sent", e);
			res = false;
		} catch (MessagingException e) {
			logger.error("Mail not sent", e);
			res = false;
		} catch (UnsupportedEncodingException e) {
			logger.error("Mail not sent", e);
			res = false;
		} catch(Exception e) {
			logger.error("Mail not sent", e);
			res = false;
		}
		return res;
	}
	
	public static boolean send(User usr, String subject, String cntText, String cntHtml) {
		boolean res = true;
		logger.info("MailUtil::send() " + usr.getUserEmail() + "; " +subject +"; "+cntText);
		try {
			Properties props = System.getProperties();
			props.setProperty("mail.smtp.host", TopupConstants.HOST_SMTP);
			Session session = Session.getDefaultInstance(props);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(TopupConstants.ADMIN_EMAIL, TopupConstants.ADMIN_NAME));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(usr.getUserEmail(), usr.getUserFirstName() + " " + usr.getUserLastName()));
			msg.setSubject(subject);
			
			if(cntHtml != null) {
				msg.setContent(cntHtml, "text/html; charset=utf-8");
			} else if(cntText != null) {
				msg.setText(cntText);
			}
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(usr.getUserEmail(), usr.getUserFirstName() + " " + usr.getUserLastName()));
			logger.info("MailUtil: " + msg.getAllRecipients()[0].toString() + "; " + msg.getSubject() + "; " + msg.getContentType() + "; " + msg.getContent().toString(), msg);
			Transport.send(msg);
		} catch (AddressException e) {
			logger.error("Mail not sent", e);
			res = false;
		} catch (MessagingException e) {
			logger.error("Mail not sent", e);
			res = false;
		} catch (UnsupportedEncodingException e) {
			logger.error("Mail not sent", e);
			res = false;
		} catch(Exception e) {
			logger.error("Mail not sent", e);
			res = false;
		}
		return res;
	}
}
