package com.masiya.topup;

public class TopupConstants {

	public static String HOST_SMTP = "localhost";
	public static String HOST_WEB = "104.236.199.74";
	public static String HOST_API = "104.236.199.74:8080";
	public static String ADMIN_EMAIL = "admin@masiya.com";
	public static String ADMIN_NAME = "Masiya Admin";
	
	public static void initialize(String env) {
		if(env != null && env.equalsIgnoreCase("local")) {
			HOST_WEB = "local.topup";
			HOST_API = "localhost:8080";
		}
	}
}
