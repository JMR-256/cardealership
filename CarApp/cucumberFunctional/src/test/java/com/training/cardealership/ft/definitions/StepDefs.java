package com.training.cardealership.ft.definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.get;

public class StepDefs {

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
}