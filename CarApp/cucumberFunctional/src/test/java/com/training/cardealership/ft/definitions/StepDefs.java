package com.training.cardealership.ft.definitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class StepDefs {

    RequestSpecification request;
    Response response;
    @When("^the client calls /private/status$")
    public void the_client_issues_GET_version() throws Throwable {
        response = get("/private/status");
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        Assertions.assertEquals(statusCode, response.getStatusCode());
    }

    @And("^the client receives a response of (.*)$")
    public void the_client_receives_server_version_body(String clientResponse) throws Throwable {
        Assertions.assertEquals(clientResponse, response.getBody().asString());
    }

    @Given("I want to add the following car")
    public void i_want_to_add_the_following_car(List<Map<String, Object>> table) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(table);
        request = given().contentType(ContentType.JSON).body(json);
    }

    @When("I POST to the {string} endpoint")
    public void i_post_to_the_endpoint(String endpoint) {
        response = request.post(endpoint);
    }


    @And("the JSON should contain the key {string} with value {string}")
    public void theJSONShouldContainTheKeyXWithValueY(String expectedKey, String expectedValue) {
        Map<String, String> responseData = response.body().as(new TypeRef<Map<String, String>>() {});
        Assertions.assertTrue(responseData.containsKey(expectedKey));
        Assertions.assertEquals(expectedValue, responseData.get(expectedKey));
    }

    @Given("I want to add the following car with JSON {string}")
    public void iWantToAddTheFollowingCarWithJSONBrand(String carJSON) {
        request = given().contentType(ContentType.JSON).body(carJSON);
    }
}