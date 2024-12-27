import org.testng.annotations.Test;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Test_GET {

	@Test
	public void Test_GET() {
		
	//GET request for fetching favourite orders
		
		
		Response response = given()
				.header("Content-Type", "application/json")
	            .header("client-type", "Android")
	            .header("device-id", "2D23ED39-81ED-4012-BA80-2EFAC35421E3")
	            .header("Authorization", "Bearer GkJWybdoEKX9bQJpzBHgFCvk1oocKqgU")
    .when()
        .get("https://41dpdmvw27.execute-api.us-east-2.amazonaws.com/sbox/profile/user/favourite_orders");
        
       
    response.then()
    .statusCode(200) 
    .body("status", notNullValue())
    .body("status", equalTo("Success"))
    .body("message", equalTo("User favourite orders retrieved successfully"))
    .body("statusCode", equalTo(2000))
    .body("data.size()", greaterThan(0))
    .body("data[0].vendorname", equalTo("Tropical Smoothie Cafe - Brink Lab 2")) 
    .body("data[0].products[0].name", equalTo("DETOX ISLAND GREEN® "))
    .body("data[1].vendorname", equalTo("Tropical Smoothie Cafe - Brink Lab 3")) 
    .body("data[1].products[0].name", equalTo("MANGO BERRY COSMO"))
    //.body("data[0].products[0].name", notNullValue())
    .log().all(); 
    
    
    System.out.println("Response Body: " + response.body().asString());
    System.out.println("Status Code: " + response.getStatusCode());
    
	}
}
