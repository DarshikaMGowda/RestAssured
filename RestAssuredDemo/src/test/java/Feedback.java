import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;

public class Feedback extends BaseTest {


	    @Test
	    public void Test_PUT() {
	        
	    	// Create Basket (POST Request)
	    	Response response = createBasketRequestBody(tcData.get("productId"),tcData.get("quantity"), tcData.get("recipient"));

	        response.then().statusCode(200);
	        String message = response.jsonPath().getString("message");
	        assertEquals(message, "Basket created successfully");

	            
	        // Extracting Basket ID
	        String basketId = response.jsonPath().getString("data.id");

	        	        
	     // Set Tip amount (PUT Request)
	        
	        Response tipRequestBody = updateTipRequestBody(tcData.get("amount"),basketId );

	        tipRequestBody.then().statusCode(200);
	        String basketMessage = tipRequestBody.jsonPath().getString("message");
	        assertEquals(basketMessage, "Tip amount set to basket successfully");
	        	        

	    }

	}
