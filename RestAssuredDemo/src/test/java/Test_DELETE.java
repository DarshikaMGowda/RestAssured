import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class Test_DELETE {

	@Test
	public void Test_DELETE() {
	
		RestAssured.baseURI = "https://41dpdmvw27.execute-api.us-east-2.amazonaws.com";

		// POST request for setting the location as Fav
		
        Response response =  given()
            .header("Content-Type", "application/json")      
            .header("client-type", "Android")               
            .header("device-id", "2D23ED39-81ED-4012-BA80-2EFAC35421E3") 
            .header("user-agent", "Android;@DeviceManufacturer;@ModelNumber;@AndroidVersion;@ScreenDensity") 
            .header("x-api-key", "8badv8bl1k3sB38D9B3l0rutKAR8c09B30lkq0sbox")
            .header("Connection", "Keep-alive")
            .header("Authorization","Bearer GkJWybdoEKX9bQJpzBHgFCvk1oocKqgU")             
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
				.header("Content-Type", "application/json")  
		        .header("client-type", "Android")
		        .header("device-id", "2D23ED39-81ED-4012-BA80-2EFAC35421E3")
		        .header("Authorization", "Bearer GkJWybdoEKX9bQJpzBHgFCvk1oocKqgU")
		        .header("x-api-key", "8badv8bl1k3sB38D9B3l0rutKAR8c09B30lkq0sbox")
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
