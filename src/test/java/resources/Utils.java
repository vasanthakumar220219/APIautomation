package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils {
	
	public static RequestSpecification req;
	public static ResponseSpecification res;
	public static Response response;
	
	public RequestSpecification requestSpecification() throws IOException
	{
		if(req==null)
			{
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		req = new RequestSpecBuilder().setBaseUri(getGlobalValue("BaseURI"))
				.addQueryParam("key", "qaclick123")
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log))
				.setContentType(ContentType.JSON)
				.build();
		return req;
			}
		return req;
	} 
	
	public  ResponseSpecification responsespecification()
	{
		res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		return res;
		
	}
	
	public static String getGlobalValue(String value) throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\MySpace\\WorkRelated\\WorkSpaces\\AutomationWorkSpace\\API_FRAMEWORK\\src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		return prop.getProperty(value);
	}
	
	public static String getJsonPath(Response response,String key)
	{
		String resp = response.asString();
		JsonPath json = new JsonPath(resp);
		return json.get(key).toString();
	}
	
	public Response getResource(RequestSpecification req,String resource,String method)
	{
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
		if(method.equalsIgnoreCase("POST"))
			response = req.when().post(resourceAPI.getResource());
			else if(method.equalsIgnoreCase("GET"))
				response = req.when().get(resourceAPI.getResource());
			else if(method.equalsIgnoreCase("DELETE"))
				response = req.when().delete(resourceAPI.getResource());
		
		return response;
	}

}
