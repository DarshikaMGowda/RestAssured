import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.internal.support.FileReader;
import io.restassured.response.Response;

public class Rest_API extends BaseTest {



	@Test
	public void Test_GET() {
		
		RestAssured.baseURI = "https://41dpdmvw27.execute-api.us-east-2.amazonaws.com";
		
		Response response = given()
				.header("Content-Type",tcData.get("Content-Type"))
	            .header("client-type", tcData.get("client-type"))
	            .header("device-id", tcData.get("device-id"))
	            .header("Authorization", tcData.get("Authorization"))
    .when()
        .get("/sbox/profile/user/favourite_orders");
		
		
		System.out.println("Response Status Code: " + response.getStatusCode());
		System.out.println("Response Headers: " + response.getHeaders());
		
    response.then()
    .statusCode(200) 
    .body("status", equalTo (tcData.get("status")))
    .body("message", equalTo("User favourite orders retrieved successfully"))
    .body("statusCode", equalTo(2000))
    .body("data.size()", greaterThan(0))
    .body("data[0].vendorname", equalTo("Tropical Smoothie Cafe - Brink Lab 2")) 
    .body("data[0].products[0].name", equalTo("DETOX ISLAND GREEN® "))
    .body("data[1].vendorname", equalTo("Tropical Smoothie Cafe - Brink Lab 3")) 
    .body("data[1].products[0].name", equalTo("MANGO BERRY COSMO"))
    .log().all(); 
    
    response.prettyPrint();
    System.out.println("Response Body: " + response.body().asString());
    System.out.println("Status Code: " + response.getStatusCode());
    
	}
	
	
	@Test
	
	public void Test_PUT() {
		
		RestAssured.baseURI = "https://41dpdmvw27.execute-api.us-east-2.amazonaws.com";

	     //Post request for Create basket
	     
	     JSONObject requestBody = new JSONObject();
	     
	     requestBody.put("productid", "16288739");  
	     requestBody.put("quantity", "1"); 
	     requestBody.put("recipient", "Varsha"); 
	    

	     
	     Response response =  given()
	         .header("Content-Type", tcData.get("Content-Type"))      
	         .header("client-type", tcData.get("client-type"))               
	         .header("device-id", tcData.get("device-id")) 
	         .header("user-agent", tcData.get("user-agent")) 
	         .header("x-api-key", tcData.get("x-api-key"))
	         .body(requestBody.toJSONString())                
	         .when()
	         .post("/sbox/order/baskets/125119/basket")
	     .then()
	         .log().all() 
	         .statusCode(200) 
	         .body("message", equalTo("Basket created successfully")) 
	         .extract().response();
	   
	     String basketId = response.jsonPath().getString("data.id");
	     
		
		//Put Request for Tip amount
		
		
		JSONObject requestBody1 = new JSONObject();
	   
	    requestBody1.put("amount", 1);

	    // Send PUT request
	    given()
	        .header("Content-Type",tcData.get("Content-Type"))  
	        .header("client-type",  tcData.get("client-type"))
	        .header("device-id", tcData.get("device-id"))
	        .header("Authorization", tcData.get("Authorization"))
	        .header("x-api-key", tcData.get("x-api-key"))
	        .body(requestBody1.toJSONString())               
	    .when()
	        .put("/sbox/order/baskets/"+basketId+"/tip")      
	    .then()
	        .statusCode(200)                            
	        .body("status", equalTo(tcData.get("status")))         
	        .body("message", equalTo("Tip amount set to basket successfully"))
	        .body("statusCode", equalTo(2000))
	        .log().all();     
	    
	}
	
	
	
	@Test
	 public void Test_POST() {
	       	
			
	        RestAssured.baseURI = "https://41dpdmvw27.execute-api.us-east-2.amazonaws.com";

	        //Post request for login
	        
	        JSONObject requestBody = new JSONObject();
	        
	        requestBody.put("email", "varshatest@gmail.com");  
	        requestBody.put("password", "I8j9Xe7KvL+QJS5E4wvDLA=="); 
	       

	        
	        Response response =  given()
	            .header("Content-Type", tcData.get("Content-Type"))      
	            .header("client-type", tcData.get("client-type"))               
	            .header("device-id", tcData.get("device-id")) 
	            .header("user-agent", tcData.get("user-agent")) 
	            .header("x-api-key", tcData.get("x-api-key"))
	            .body(requestBody.toJSONString())                
	            .when()
	            .post("/sbox/profile/auth/login")
	        .then()
	            .log().all() 
	            .statusCode(200) 
	            .body("message", equalTo("User successfully logged in")) 
	            .extract().response();
	        
	        

	        
	        String accessToken = response.jsonPath().getString("data.access_token.token");
	        
	        String oloAccessToken = response.jsonPath().getString("data.oloToken");
	       
	      //POST request for logout
	        
	        JSONObject requestBody1 = new JSONObject();
	       
	        requestBody1.put("access_token", accessToken);  
	        requestBody1.put("olo_access_token", oloAccessToken); 
	       
	        
	        Response response1 =  given()
	            .header("Content-Type", tcData.get("Content-Type"))      
	            .header("client-type", tcData.get("client-type"))               
	            .header("device-id", tcData.get("device-id")) 
	            .header("user-agent", tcData.get("user-agent")) 
	            .header("x-api-key", tcData.get("x-api-key"))
	            .header("Connection", tcData.get("Connection"))
	            .body(requestBody1.toJSONString())                
	            .when()
	            .post("/sbox/profile/auth/logout")
	        .then()
	            .log().all() 
	            .statusCode(200) 
	            .body("message", equalTo("User logged out successfully")) 
	            .extract().response();

	       
	        String requestBody2 = response1.getBody().asString();
	        System.out.println("Response Body: " + requestBody2);

			}
	
	
	
	@Test
	public void Test_DELETE() {
		
		RestAssured.baseURI = "https://41dpdmvw27.execute-api.us-east-2.amazonaws.com";

		// POST request for setting the location as Fav
        Response response =  given()
            .header("Content-Type", tcData.get("Content-Type"))      
            .header("client-type",tcData.get("client-type"))               
            .header("device-id", tcData.get("device-id")) 
            .header("user-agent", tcData.get("user-agent")) 
            .header("x-api-key", tcData.get("x-api-key"))
            .header("Connection", tcData.get("Connection"))
            .header("Authorization",tcData.get("Authorization"))             
            .when()
            .post("/sbox/profile/user/66030/favourite_locations?isdefault=false")
        .then()
            .log().all() 
            .statusCode(200) 
            .body("message", equalTo("User favourite location saved successfully"))
            .body("data.vendorname", equalTo("TSC Mama Lab")) 
            .body("data.vendorid", equalTo(66030))
            .extract().response();

       
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);

		
        // DELETE request for deleting the Fav location 
        
        String endpoint = "/sbox/profile/user/66030/favourite_locations";
		
		Response response1 = given()
				.header("Content-Type", tcData.get("Content-Type"))  
		        .header("client-type", tcData.get("client-type"))
		        .header("device-id", tcData.get("device-id"))
		        .header("Authorization", tcData.get("Authorization"))
		        .header("x-api-key", tcData.get("x-api-key"))
	        .when()
	            .delete(endpoint)
	        .then()
	            .log().all() 
	            .body("message", equalTo("User favourite location deleted successfully"))
	            .statusCode(200) 
	            .extract().response();
		
		 String responseBody1 = response1.getBody().asString();
	        System.out.println("Response Body: " + responseBody1);


	}
}
