package stepdefs.api;

import api.client.ApiClient;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.qameta.allure.Step;

public class Hooks {

  @Before
  @Step("Reset test state before scenario")
  public void beforeScenario() {
    UserApiSteps.createdUserId = null;
  }

  @After
  @Step("Clean up created user after scenario")
  public void afterScenario() {
    if (UserApiSteps.createdUserId != null) {
      try {
        ApiClient.delete("/users/" + UserApiSteps.createdUserId);
      } catch (Exception e) {
        System.err.println("Warning: Failed to delete user with ID " + UserApiSteps.createdUserId + " during cleanup.");
      }
      UserApiSteps.createdUserId = null;
    }
  }
}
