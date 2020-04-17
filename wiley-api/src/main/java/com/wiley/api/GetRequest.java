package com.wiley.api;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.json.*;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class GetRequest {


	@Test(description="Validate Number of suggestions in search response JSON",priority = 1)
	public void testSuggestions() {	
		//make the API call
		Response response = RestAssured.given()
				.baseUri("https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J")
				.param("term", "java")
				.when()
				.get();

		System.out.println(response.asString());
		//create JSON object using response JSON string
		JSONObject resultJsonObject = new JSONObject(response.asString());
		
		//Get the suggestions array from JSON object
		JSONArray suggestionsArray = resultJsonObject.getJSONArray("suggestions");
		System.out.println("number of suggestions:- " + suggestionsArray.length());
		
		//assert the number of suggestions in suggestions array
		assertEquals(suggestionsArray.length(), 4);
	}


	@Test(description="Validate the attribute 'term' starts with Java",priority = 2)
	public void testSuggestionsTermStartsWithJava() {	
		//make the API call
		Response response = RestAssured.given()
				.baseUri("https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J")
				.param("term", "java")
				.when()
				.get();

		System.out.println(response.asString());

		String termWord = "{\"term\":\"<span class=\\\"search-highlight\\\">java</span>";

		//check whether the term word is included in response string and get the matching count of term word
		int count = StringUtils.countMatches(response.asString(), termWord);
		System.out.println(count);
		
		//assert the number of terms which start from <span class=\"search-highlight\">java</span>
		assertEquals(4, count);
	}



	@Test(description="Validate Number of Products in search response JSON",priority = 3)
	public void testProducts() {	
		//make the API call
		Response response = RestAssured.given()
				.baseUri("https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J")
				.param("term", "java")
				.when()
				.get();

		System.out.println(response.asString());
		
		//create JSON object using response JSON string
		JSONObject resultJsonObject = new JSONObject(response.asString());	
		
		//Get the products array from JSON object
		JSONArray productsArray = resultJsonObject.getJSONArray("products");
		System.out.println("number of products:- " + productsArray.length());
		
		//assert the number of products in products array
		assertEquals(productsArray.length(), 4);
	}


	@Test(description="Validate the attribute 'name' in product (contains java)",priority = 4)
	public void testProductNameContainsJava() {	
		//make the API call
		Response response = RestAssured.given()
				.baseUri("https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J")
				.param("term", "java")
				.when()
				.get();

		System.out.println(response.asString());
		//create JSON object using response JSON string
		JSONObject resultJsonObject = new JSONObject(response.asString());	
		
		//Get the products array from JSON object
		JSONArray productsArray = resultJsonObject.getJSONArray("products");

		boolean isPassed =  true;
		String word = "<span class=\\\"search-highlight\\\">java</span>";

		//check whether the name contains given word
		for(int i =0; i<productsArray.length(); i++) {

			//get the first product from product array
			JSONObject productJsonObject = productsArray.getJSONObject(i);
			
			//get the value of name
			String product = productJsonObject.getString("name");
			System.out.println(product);

			//check the product name with given word
			if(!product.contains(word)) {
				isPassed = false;
			}
		}
		assertTrue(isPassed);;
	}



	@Test(description="Validate Number of pages in search response JSON",priority = 5)
	public void testPages() {
		//make the API call
		Response response = RestAssured.given()
				.baseUri("https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J")
				.param("term", "java")
				.when()
				.get();

		System.out.println(response.asString());
		
		//create JSON object using response JSON string
		JSONObject resultJsonObject = new JSONObject(response.asString());	
		
		//get the page array from JSON object
		JSONArray pagesArray = resultJsonObject.getJSONArray("pages");
		System.out.println("number of pages:- " + pagesArray.length());
		
		//assert the number of pages in page array
		assertEquals(pagesArray.length(), 4);
	}


	@Test(description="Validate the attribute 'title' in page(contains the word Wiley)",priority = 6)
	public void testPageTitleContainsWiley() {	
		//make the API call
		Response response = RestAssured.given()
				.baseUri("https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J")
				.param("term", "java")
				.when()
				.get();

		System.out.println(response.asString());

		//create JSON object using response JSON string
		JSONObject resultJsonObject = new JSONObject(response.asString());		
		
		//get the page array from JSON object
		JSONArray pagesArray = resultJsonObject.getJSONArray("pages");

		boolean isPassed =  true;
		String word = "Wiley";

		//check whether the title contains given word
		for(int i =0; i<pagesArray.length(); i++) {
			//get the first page JSON object from page array
			JSONObject pageJsonObject = pagesArray.getJSONObject(i);

			//get the value of title
			String title = pageJsonObject.getString("title");

			System.out.println(title);

			//check the title with given word
			if(!title.contains(word)) {
				isPassed = false;
			}
		}

		assertTrue(isPassed);;
	}

	@Test(description="Check whether an image URL contains wodth 300",priority = 7)
	public void testImagesWidth() {	
		//make the API call
		Response response = RestAssured.given()
				.baseUri("https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J")
				.param("term", "java")
				.when()
				.get();

		System.out.println(response.asString());
		//create JSON object using response JSON string
		JSONObject resultJsonObject = new JSONObject(response.asString());		
		
		//get the products array from JSON object
		JSONArray productsArray = resultJsonObject.getJSONArray("products");
		
		boolean isWidthOk = false;
		
		//get the first product from product json array
		JSONObject productObject = productsArray.getJSONObject(0);
		
		//get the image array of first product
		JSONArray imageArray =  productObject.getJSONArray("images");
		
		//get the first image JSON object from image array
		JSONObject imageObject = imageArray.getJSONObject(0);
		
		//get the value of Image UDL
		String url = imageObject.getString("url");
		
		System.out.println(url);
		
		//check whether the url conatins 300
		if(url != null && url.contains("300")) {
			isWidthOk = true;
		}
		assertTrue(isWidthOk);
	}
	
	@Test(description="Check the image width using image information",priority = 8)
	public void testImagesWidthUsingImageInformation() {	
		//make the API call
		Response response = RestAssured.given()
				.baseUri("https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J")
				.param("term", "java")
				.when()
				.get();

		System.out.println(response.asString());
		//create JSON object using response JSON string
		JSONObject resultJsonObject = new JSONObject(response.asString());		
		
		//get the products array from JSON object
		JSONArray productsArray = resultJsonObject.getJSONArray("products");
		
		boolean isWidthOk = false;
		
		//get the first product from product json array
		JSONObject productObject = productsArray.getJSONObject(0);
		
		//get the image array of first product
		JSONArray imageArray =  productObject.getJSONArray("images");
		
		//get the first image JSON object from image array
		JSONObject imageObject = imageArray.getJSONObject(0);

		//get the value of Image UDL
		String url = imageObject.getString("url");

		try {
			URL imageUrl = new URL(url);
			URLConnection conn = imageUrl.openConnection();	
			InputStream in = conn.getInputStream();
			BufferedImage image = ImageIO.read(in);
			
			int width = image.getWidth();
			System.out.println("image width:" + width);
			
			if(width == 300) {
				isWidthOk = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(isWidthOk);
	}
}
