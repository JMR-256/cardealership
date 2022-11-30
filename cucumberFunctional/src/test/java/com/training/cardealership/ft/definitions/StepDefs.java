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

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;

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
        String json = convertToJson(table);
        request = given().contentType(ContentType.JSON).body(json);
    }

    @Given("The following cars exists in the database")
    public void theFollowingCarHasBeenPostedToTheCarsAdminEndpoint(List<Map<String, String>> table) throws JsonProcessingException {
        i_want_to_add_the_following_car(table);
        response = request.post(ENDPOINTS.get("POST_CAR"));
        if (response.statusCode() >= 400) {
            throw new RuntimeException();
        }
    }

    @When("I POST to the {string} endpoint")
    public void i_post_to_the_endpoint(String endpoint) {
        response = request.post(endpoint);
    }

    @When("the client GETs the endpoint {string}")
    public void clientGetEndpoint(String endpoint) {
        response = given().get(endpoint);
    }

    @When("the client GETs the endpoint {string} with query {string}")
    public void clientGetsEndpointQuery(String endpoint, String query) {
        Map<String, String> queryParams = splitQueryParams(query);
        response = given().queryParams(queryParams).get(endpoint);
    }

    @And("a GET request to {string} with query {string}")
    public void aGETRequestWithQueryBrandBmwModelXShouldRetrieveTheUpdatedCar(String endpoint, String query) {
        response = given().queryParams((splitQueryParams(query))).get(endpoint);
    }

    @When("The client PUTs the endpoint {string} with the following data")
    public void the_client_puts_the_endpoint_with_the_following_data(String endpoint, List<Map<String, String>> formBody) throws JsonProcessingException {
        String json = convertToJson(formBody.get(0));
        response = given().contentType(ContentType.JSON).body(json).put(endpoint);
    }

    @When("The client PUTs the endpoint {string} with the following JSON {string}")
    public void the_client_pu_ts_the_endpoint_with_the_following_json(String endpoint, String jsonString) {
        response = given().contentType(ContentType.JSON).body(jsonString).put(endpoint);
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


    @And("the client receives response JSON containing Unordered")
    public void checkResponseJsonUnordered(List<Map<String, String>> expectedResult) {
        Set<Map<String, String>> parsedResponse = response.as(new TypeRef<Set<Map<String, String>>>(){});
        Assertions.assertEquals(new HashSet<>(expectedResult), parsedResponse);
    }

    @And("the client receives response JSON containing")
    public void checkResponseJson(List<Map<String, String>> expectedResult) {
        List<Map<String, String>> parsedResponse = response.as(new TypeRef<List<Map<String, String>>>(){});
        Assertions.assertEquals(expectedResult, parsedResponse);
    }

    @DataTableType(replaceWithEmptyString = "[blank]")
    public String stringType(String cell) {
        return cell;
    }

    private String convertToJson(Object table) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(table);
    }

    private Map<String, String> splitQueryParams(String query) {
        String[] queryParts = query.split("&");
        return Arrays.stream(queryParts)
                .map((queryKeyValue) -> queryKeyValue.split("="))
                .collect(Collectors.toMap(splitString -> splitString[0], splitString -> splitString.length > 1 ? splitString[1] : ""));
    }


}