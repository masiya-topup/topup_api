package com.masiya.regtest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.masiya.topup.model.User;
import com.masiya.topup.model.UserAuth;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Topup_API_UserOld {
	static UserAuth userAuth = null;
	
	@Before
	public void setUp() {
		//RestAssured.basePath = "http://localhost:8080";
	}

	@Test
	public void testUser_1Login() {
		Response resp = given()
			.header("Authorization", "Basic YWRtaW46amF5YW50")
			.log().method()
			.log().path()
			.log().headers()
		.when()
			.post("/topup/api/v1/users/login")
		.then()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body(
				"userAuthToken", notNullValue(),
				"userLoginStatus", equalTo(1)
				)
			.log().ifError()
			.log().ifValidationFails()
		.extract()
			.response();
		userAuth = resp.as(UserAuth.class);
		System.out.println("AuthToken: " + userAuth.getUserAuthToken());
	}

	@Test
	public void testUser_Details() {
		Response resp = given()
			.header("Authorization", "Bearer " + userAuth.getUserAuthToken())
			.log().method()
			.log().path()
			.log().headers()
		.when()
			.get("/topup/api/v1/users/1")
		.then()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body(
				"userId", notNullValue(),
				"userId", equalTo(1)
				)
			.log().ifError()
			.log().ifValidationFails()
		.extract()
			.response();
		User usr = resp.as(User.class);
	}

	@Test
	public void testUser_ZLogout() {
		Response resp = given()
			.header("Authorization", "Bearer " + userAuth.getUserAuthToken())
			.log().method()
			.log().path()
			.log().headers()
		.when()
			.post("/topup/api/v1/users/logout")
		.then()
			.statusCode(200)
			.contentType(ContentType.TEXT)
			.log().all()
		.extract()
			.response();
		Boolean status = Boolean.valueOf(resp.body().asString());
		
	}
}
