package com.training.cardealership.ft.definitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class StepDefs {

    private static final Map<String, String> ENDPOINTS = Map.of("POST_CAR", "cars/admin");

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
    public void i_want_to_add_the_following_car(List<Map<String, String>> table) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(table);
        request = given().contentType(ContentType.JSON).body(json);
    }

    @Given("The following cars exists in the database")
    public void theFollowingCarHasBeenPostedToTheCarsAdminEndpoint(List<Map<String, String>> table) throws JsonProcessingException {
        i_want_to_add_the_following_car(table);
        request.post(ENDPOINTS.get("POST_CAR"));
    }

    @When("I POST to the {string} endpoint")
    public void i_post_to_the_endpoint(String endpoint) {
        response = request.post(endpoint);
    }

    @When("the client GETs the endpoint {string}")
    public void clientGetEndpoint(String endpoint) {
        response = given().get(endpoint);
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

    @DataTableType(replaceWithEmptyString = "[blank]")
    public String stringType(String cell) {
        return cell;
    }

    @And("the client receives response JSON containing")
    public void checkResponseJson(List<Map<String, String>> expectedResult) {
        Set<Map<String, String>> parsedResponse = response.as(new TypeRef<Set<Map<String, String>>>(){});
        Assertions.assertEquals(new HashSet<>(expectedResult), parsedResponse);
    }
}