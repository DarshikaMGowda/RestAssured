package Test;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Test_POST extends BaseTest{

	@Test
	public void Test_POST() {		
		

//   Post Request for Login     
		Response response = loginRequestBody(tcData.get("email"),tcData.get("password"));

		response.prettyPrint();
        response.then().statusCode(200);
        String message = response.jsonPath().getString("message");
        assertEquals(message,tcData.get("loginmessage"));

        
        
        String access_token = response.jsonPath().getString("data.access_token.token");
        
        String oloToken = response.jsonPath().getString("data.oloToken");
       
        
 // Post Request for Logout    

        Response logoutresponse = logutRequestBody(access_token,oloToken);
        logoutresponse.then().statusCode(200);
        logoutresponse.prettyPrint();
        String logoutmessage = logoutresponse.jsonPath().getString("message");
        assertEquals(logoutmessage, tcData.get("logoutmessage"));
        
        
		}
    }
