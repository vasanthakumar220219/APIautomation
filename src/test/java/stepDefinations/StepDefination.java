package stepDefinations;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefination extends Utils{
 RequestSpecification req;
 ResponseSpecification res;
 Response response;
 String placeID=null;


@Given("Add place payload with {string} {string} {string}")
public void add_place_payload_with(String name, String language, String address) throws IOException {
    // Write code here that turns the phrase above into concrete actions
   // throw new cucumber.api.PendingException();
	 req = given().spec(requestSpecification()).body(TestDataBuild.AddPlace_request_body(name, language, address));
	// System.out.println("Completed Given" + TestDataBuild.AddPlace_request_body(name, language, address));
}

@When("user calls {string} with {string} https request")
public void user_calls_with_https_request(String resource, String method) {
    // Write code here that turns the phrase above into concrete actions
   // throw new cucumber.api.PendingException();
	response = getResource(req,resource,method);		
	//System.out.println("Completed when : "+ response.asString());
}

@Then("verify status code is {int}")
public void verify_status_code_is(Integer int1) {
    // Write code here that turns the phrase above into concrete actions
    //throw new cucumber.api.PendingException();
	assertEquals(response.getStatusCode(), 200);
	// get place id
	placeID = getJsonPath(response, "place_id");
	// System.out.println("Completed then1 $ retrive place id : "+placeID);
}

@Then("{string} in response body is {string}")
public void in_response_body_is(String key, String value) {
    // Write code here that turns the phrase above into concrete actions
    //throw new cucumber.api.PendingException();
	assertEquals(getJsonPath(response, key), value);
	// System.out.println("Completed then2");
}

@Then("verify place id created is mapped to {string} using {string}")
public void verify_place_id_created_is_mapped_to_using(String name, String resource) throws IOException {
    // Write code here that turns the phrase above into concrete actions
    //throw new cucumber.api.PendingException();
	req = given().spec(requestSpecification()).queryParam("place_id", placeID);
	response = getResource(req,resource,"GET");
	String actualname = getJsonPath(response, "name");
	assertEquals(actualname, name);
	// System.out.println("Completed then3");
}



}
