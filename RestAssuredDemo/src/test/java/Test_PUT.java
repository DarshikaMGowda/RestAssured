import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Test_PUT {
	
	@Test
	public void Test_PUT() {
	
	
	 RestAssured.baseURI = "https://41dpdmvw27.execute-api.us-east-2.amazonaws.com";

     //Post request for Create basket
     
     JSONObject requestBody = new JSONObject();
     
     requestBody.put("productid", "16288739");  
     requestBody.put("quantity", "1"); 
     requestBody.put("recipient", "Varsha"); 
    

     
     Response response =  given()
         .header("Content-Type", "application/json")      
         .header("client-type", "Android")               
         .header("device-id", "2D23ED39-81ED-4012-BA80-2EFAC35421E3") 
         .header("user-agent", "Android;@DeviceManufacturer;@ModelNumber;@AndroidVersion;@ScreenDensity") 
         .header("x-api-key", "8badv8bl1k3sB38D9B3l0rutKAR8c09B30lkq0sbox")
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
    
    
   // JSONObject user = new JSONObject();
   
   // user.put("amount", "1");
   
    requestBody1.put("amount", 1);

    // Send PUT request
    given()
        .header("Content-Type", "application/json")  
        .header("client-type", "Android")
        .header("device-id", "2D23ED39-81ED-4012-BA80-2EFAC35421E3")
        .header("Authorization", "Bearer GkJWybdoEKX9bQJpzBHgFCvk1oocKqgU")
        .header("x-api-key", "8badv8bl1k3sB38D9B3l0rutKAR8c09B30lkq0sbox")
        .body(requestBody1.toJSONString())               
    .when()
        .put("/sbox/order/baskets/"+basketId+"/tip")      
    .then()
        .statusCode(200)                            
        .body("status", equalTo("Success"))         
        .body("message", equalTo("Tip amount set to basket successfully"))
        .body("statusCode", equalTo(2000))
        .log().all(); 
    
    
}
		
	}

