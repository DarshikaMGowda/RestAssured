import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BaseTest {

    protected static ExtentReports extent;
    protected static ExtentTest test;
   
    private static final String EXTENT_REPORT_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "extent-test-output" + File.separator;
    String loginUrl = "/sbox/profile/auth/login";
    String logoutUrl = "/sbox/profile/auth/logout";
  
    
    // Initialize Extent Reports
    @BeforeSuite
    public void setUp() {    	
    	
        try {
           
            // Set up ExtentSparkReporter
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter( EXTENT_REPORT_FOLDER_PATH);
            sparkReporter.config().setDocumentTitle("Test Report");
            sparkReporter.config().setReportName("Automation Test Results");
            sparkReporter.config().setEncoding("UTF-8");

            // Initialize ExtentReports
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // Add System Info
            extent.setSystemInfo("Tester", "Your Name");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Browser", "Chrome");

            System.out.println("Extent Reports initialized at: " +  EXTENT_REPORT_FOLDER_PATH);
        } catch (Exception e) {
            System.out.println("Error during Extent Reports initialization: " + e.getMessage());
        }
    }

    
    // Create a new test entry for each test method
    @BeforeMethod
    public void startTest(Method method) {
        test = extent.createTest(method.getName());
    }

    
    // Capture test results
    @AfterMethod
    public void captureResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("Test Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed");
        } else {
            test.skip("Test Skipped: " + result.getThrowable());
        }
    }

    
    // Close Extent Reports
    @AfterSuite
    public void tearDown() {
        if (extent != null) {
            extent.flush();
            System.out.println("Extent Report generated successfully.");
        }
    }
    
    
    
    @AfterTest
    public static void flushReports() {	
    	if (Objects.nonNull(extent)) {
			extent.flush();
		}
		try {
			File reportFile = new File(EXTENT_REPORT_FOLDER_PATH);
                // Use the Desktop class to open the report in the default browser
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(reportFile.toURI()); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
           
    
    
    public HashMap<String, String> tcData = null;
	@BeforeTest
	public void parseJsonFile() {
		
		JSONParser parser = new JSONParser();
		String tcClassName = this.getClass().getSimpleName();		
		try {
		 //   String content = new String(Files.readAllBytes(Paths.get("/Users/darshika/eclipse-workspace/RestAssuredDemo/src/test/resources/testdata/Rest_API.json")), StandardCharsets.UTF_8);
		// String content =  new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/src/test/resources/testdata/" + tcClassName + ".json")), StandardCharsets.UTF_8);
		 String content = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("testdata/" + tcClassName + ".json").getPath())), StandardCharsets.UTF_8);
		 Object obj = parser.parse(content);
//		    JSONObject jsonObject = (JSONObject) obj;
		    tcData = (JSONObject) parser.parse(content);
		    System.out.println(tcData);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	 protected String baseURI = "https://41dpdmvw27.execute-api.us-east-2.amazonaws.com";


    
    @BeforeClass
    public void setup() {
        // Set Base URI
        RestAssured.baseURI = baseURI;
        
    } 

    // Method to prepare the body for creating a basket
    public Response createBasketRequestBody(String productid, String quantity, String recipient) {
        
    	Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("client-type", "Android");
        headers.put("device-id", "2D23ED39-81ED-4012-BA80-2EFAC35421E3");
        headers.put("user-agent", "Android;@DeviceManufacturer;@ModelNumber;@AndroidVersion;@ScreenDensity");
        headers.put("x-api-key", "8badv8bl1k3sB38D9B3l0rutKAR8c09B30lkq0sbox");
        headers.put("Authorization", "Bearer GkJWybdoEKX9bQJpzBHgFCvk1oocKqgU");
    	
    	JSONObject requestBody = new JSONObject();
        requestBody.put("productid", productid);
        requestBody.put("quantity", quantity);
        requestBody.put("recipient", recipient);
        
        Response response = RestAssured.given().log().all().body(requestBody).headers(headers).when().post("/sbox/order/baskets/125119/basket");
        return response;
    }

    
    // Method to prepare the body for updating the tip amount
    public Response updateTipRequestBody(String amount, String basketId ) {
    	
    	Map<String, String> headers = new HashMap<>();
    	headers.put("Authorization", "Bearer GkJWybdoEKX9bQJpzBHgFCvk1oocKqgU");
        headers.put("Content-Type", "application/json");
        headers.put("device-id", "2D23ED39-81ED-4012-BA80-2EFAC35421E3");
        headers.put("client-type", "Android");
        headers.put("x-Fowarded-for", "192.168.29.4");
        headers.put("x-api-key", "8badv8bl1k3sB38D9B3l0rutKAR8c09B30lkq0sbox");
    	
        JSONObject requestBody = new JSONObject();
        requestBody.put("amount", amount);
        
        Response response = RestAssured.given().log().all().body(requestBody).headers(headers).when().put("/sbox/order/baskets/" + basketId + "/tip");
        return response;
    }
    
    
    // method for login request
    
    public Response loginRequestBody(String email, String password) {
    	
    	Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("client-type", "Android");
        headers.put("device-id", "2D23ED39-81ED-4012-BA80-2EFAC35421E3");
        headers.put("user-agent", "Android;@DeviceManufacturer;@ModelNumber;@AndroidVersion;@ScreenDensity");
        headers.put("x-api-key", "8badv8bl1k3sB38D9B3l0rutKAR8c09B30lkq0sbox");
        
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        requestBody.put("password", password);
        
        Response response = RestAssured.given().log().all().body(requestBody).headers(headers).when().post(loginUrl);
        return response;
    }
    
 // method for logout request
    
 public Response logutRequestBody(String access_token, String oloToken) {
    	
    	Map<String, String> headers = new HashMap<>();
    	
    	 headers.put("Content-Type", "application/json");      
    	 headers.put("client-type", "Android");              
    	 headers.put("device-id", "2D23ED39-81ED-4012-BA80-2EFAC35421E3"); 
    	 headers.put("user-agent", "Android;@DeviceManufacturer;@ModelNumber;@AndroidVersion;@ScreenDensity"); 
    	 headers.put("x-api-key", "8badv8bl1k3sB38D9B3l0rutKAR8c09B30lkq0sbox");
    	 headers.put("Connection", "Keep-alive");
    	
        
        JSONObject requestBody = new JSONObject();
        requestBody.put("access_token", access_token);
        requestBody.put("olo_access_token", oloToken);
        
        Response response = RestAssured.given().log().all().body(requestBody).headers(headers).when().post(logoutUrl);
        return response;
    }
    
    
}
