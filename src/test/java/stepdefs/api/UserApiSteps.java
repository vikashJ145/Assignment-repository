package stepdefs.api;

import api.client.ApiClient;
import api.models.User;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

import static org.testng.Assert.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class UserApiSteps {

    Response response;
    User testUser;
    public static String createdUserId;
    static Map<String, Map<String, String>> userTestData;

    static {
        try {
            InputStream is = UserApiSteps.class.getClassLoader().getResourceAsStream("user_test_data.json");
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            userTestData = mapper.readValue(json, new TypeReference<Map<String, Map<String, String>>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to load user_test_data.json", e);
        }
    }

    @Given("I use user test data {string}")
    public void i_use_user_test_data(String key) {
        Map<String, String> data = userTestData.get(key);
        if (data == null)
            throw new IllegalArgumentException("No test data for key: " + key);
        testUser = new User(data.get("name"), data.get("email"));
        Allure.addAttachment("Test Data", "text/plain", "Name: " + testUser.name + "\nEmail: " + testUser.email);
    }

    @Given("no users exist")
    public void no_users_exist() {
        createdUserId = null;
    }

    @When("I send a POST request to {string}")
    public void i_send_a_post_request_to(String endpoint) {
        response = ApiClient.post(endpoint, testUser);
        if (response.statusCode() == 201 && response.jsonPath().getString("id") != null) {
            createdUserId = response.jsonPath().getString("id");
        }
        attachApiResponse("POST Request Response", response);
    }

    @When("I send a GET request to get the created user")
    public void i_send_a_get_request_to_get_the_created_user() {
        response = ApiClient.get("/users/" + createdUserId);
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        String resolved = endpoint.replace("{id}", createdUserId != null ? createdUserId : "");
        response = ApiClient.get(resolved);
    }

    @When("I send a PUT request to {string} with updated name {string} and email {string}")
    @When("I send a PUT request to {string} with updated name {string}, and email {string}")
    public void i_send_a_put_request_to_with_updated_name_and_email(String endpoint, String name, String email) {
        testUser = new User(name, email);
        String resolved = endpoint.replace("{id}", createdUserId != null ? createdUserId : "");
        response = ApiClient.put(resolved, testUser);
    }

    @When("I send a DELETE request to {string} for the created user")
    public void i_send_a_delete_request_to_for_the_created_user(String endpoint) {
        String resolved = endpoint.replace("{id}", createdUserId != null ? createdUserId : "");
        response = ApiClient.delete(resolved);
    }

    @When("I send a DELETE request to {string}")
    public void i_send_a_delete_request_to(String endpoint) {
        response = ApiClient.delete(endpoint);
    }

    @Then("Response code should be {int}")
    public void response_code_should_be(Integer statusCode) {
        assertEquals(response.statusCode(), statusCode.intValue());
    }

    @Then("Response should contain {string}")
    public void response_should_contain(String content) {
        assertTrue(response.getBody().asString().contains(content));
    }

    @Then("Response should not contain {string}")
    public void response_should_not_contain(String content) {
        assertFalse(response.getBody().asString().contains(content));
    }

    @Then("Response should have validation error")
    public void response_should_have_validation_error() {
        assertTrue(response.statusCode() == 400 || response.getBody().asString().toLowerCase().contains("error"));
    }

    @Then("Response header {string} should be {string}")
    public void response_header_should_be(String headerName, String expectedValue) {
        assertEquals(response.getHeader(headerName), expectedValue);
    }

    @Then("Response should have error message {string}")
    public void response_should_have_error_message(String errorMessage) {
        assertTrue(response.getBody().asString().contains(errorMessage),
                "Expected error message not found: " + errorMessage);
    }

    @Attachment(value = "API Response", type = "text/plain")
    public String attachApiResponse(String title, Response response) {
        StringBuilder sb = new StringBuilder();
        sb.append("Status Code: ").append(response.statusCode()).append("\n");
        sb.append("Response Headers:\n");
        response.getHeaders().forEach(
                header -> sb.append("\t").append(header.getName()).append(": ").append(header.getValue()).append("\n"));
        sb.append("Response Body:\n").append(response.getBody().asString());
        return sb.toString();
    }
}
