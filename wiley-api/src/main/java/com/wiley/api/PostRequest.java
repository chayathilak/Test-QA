package com.wiley.api;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class PostRequest {

	@Test(description="Set delay as 10 and get the response with the status code 200",priority = 1)
	public void postDelay() {
		//make the api call to set delay as 10
		Response response = given()
				.baseUri("https://httpbin.org/delay/10")
				.when()
				.post();
		
		//get the status code
		int statusCode = response.statusCode();
		System.out.println(response.asString());
		
		//check whether the status code is 200(ok)
		assertEquals(statusCode, 200);
	}

}
