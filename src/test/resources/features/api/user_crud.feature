Feature: User API

  Scenario: Create a user
    Given I use user test data "validUser"
    When I send a POST request to "/users"
    Then Response code should be 201
    And Response header "Content-Type" should be "application/json"

  Scenario: Get user by ID
    When I send a GET request to get the created user
    Then Response code should be 200
    And Response should contain "Vikash"
    And Response header "Content-Type" should be "application/json"

  Scenario: Update user
    When I send a PUT request to "/users/{id}" with updated name "Vikash Updated" and email "vikash.updated@test.com"
    Then Response code should be 200
    And Response header "Content-Type" should be "application/json"

  Scenario: Delete user
    When I send a DELETE request to "/users/{id}" for the created user
    Then Response code should be 200
    And Response header "Content-Type" should be "application/json"

  Scenario: Get user with invalid ID
    When I send a GET request to "/users/9999"
    Then Response code should be 404
    And Response should have error message "User not found"
    And Response header "Content-Type" should be "application/json"

  Scenario: Create user with missing name
    Given I use user test data "missingNameUser"
    When I send a POST request to "/users"
    Then Response code should be 400
    And Response should have error message "Name is required"
    And Response header "Content-Type" should be "application/json"

  Scenario: Create user with invalid email
    Given I use user test data "invalidEmailUser"
    When I send a POST request to "/users"
    Then Response code should be 400
    And Response should have error message "Invalid email format"
    And Response header "Content-Type" should be "application/json"

  # --- Additional Positive Scenarios ---
  Scenario: Get all users
    When I send a GET request to "/users"
    Then Response code should be 200

  Scenario: Create user with special characters in name
    Given I use user test data "specialCharUser"
    When I send a POST request to "/users"
    Then Response code should be 201

  Scenario: Create user with maximum allowed name length
    Given I use user test data "maxNameUser"
    When I send a POST request to "/users"
    Then Response code should be 201

  Scenario: Create user with maximum allowed email length
    Given I use user test data "maxEmailUser"
    When I send a POST request to "/users"
    Then Response code should be 201

  Scenario: Update user with valid new email
    When I send a PUT request to "/users/{id}" with updated name "Vikash", and email "vikash.new@test.com"
    Then Response code should be 200

  Scenario: Delete user and verify deletion
    When I send a DELETE request to "/users/{id}" for the created user
    Then Response code should be 200
    When I send a GET request to "/users/{id}"
    Then Response code should be 404

  Scenario: Create user with mixed-case email
    Given I use user test data "mixedCaseEmailUser"
    When I send a POST request to "/users"
    Then Response code should be 201

  # --- Additional Negative Scenarios ---
  Scenario: Create user with missing email
    Given I use user test data "missingEmailUser"
    When I send a POST request to "/users"
    Then Response code should be 400

  Scenario: Create user with name exceeding max length
    Given I use user test data "longNameUser"
    When I send a POST request to "/users"
    Then Response code should be 400

  Scenario: Create user with email exceeding max length
    Given I use user test data "longEmailUser"
    When I send a POST request to "/users"
    Then Response code should be 400

  Scenario: Update user with invalid ID
    When I send a PUT request to "/users/9999" with updated name "Invalid Update", and email "invalid.update@test.com"
    Then Response code should be 404

  Scenario: Update user with invalid email
    When I send a PUT request to "/users/{id}" with updated name "Invalid Email", and email "notanemail"
    Then Response code should be 400

  Scenario: Delete user with invalid ID
    When I send a DELETE request to "/users/9999" for the created user
    Then Response code should be 404

  Scenario: Get all users when none exist
    When I send a GET request to "/users"
    Then Response code should be 200
    And Response should not contain "Vikash"

  Scenario: Create user with duplicate email
    Given I use user test data "duplicateEmailUser"
    When I send a POST request to "/users"
    Then Response code should be 409
    And Response should have error message "User already exists"
    And Response header "Content-Type" should be "application/json"
