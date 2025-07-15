# API Automation Framework for Bookstore FastAPI

## Overview

This project is an API automation framework for the Bookstore FastAPI. It uses Java, RestAssured, Cucumber, and TestNG to test the User API (Create, Read, Update, Delete).

---

## Testing Strategy (Quick Summary)

- **Goal:** Make sure the API works for all main actions (create, read, update, delete) and handles errors correctly.
- **How:** Use easy-to-read Cucumber files, reusable Java code, and a local mock API for reliable, repeatable tests.

### Step-by-Step Testing Approach

1. **Write clear test scenarios**
   - Use Cucumber feature files (plain English) for each API action.
   - Cover both positive (valid) and negative (invalid/error) cases.
2. **Keep test data separate**
   - Store user data in a JSON file (`user_test_data.json`).
   - Reference this data in feature files by key, not by value.
3. **Use request chaining**
   - Save data from one test (like a user ID) and use it in later tests.
4. **No hardcoded URLs or secrets**
   - All environment details (base URL, credentials) are in `config.properties`.
5. **Reusable and maintainable code**
   - Java step definitions are modular and map directly to Cucumber steps.
   - Easy to add new tests or update data.
6. **Advanced validation**
   - Check not just if the API works, but also status codes, headers, and error messages.
7. **Automated reporting**
   - Use Allure and TestNG for detailed test reports.
8. **CI/CD integration**
   - Tests run automatically on every code push using GitHub Actions.

**Problems we solved:**
- Public APIs were unreliable, so we use a local mock API.
- Test data and config are always kept outside the code.
- Tests are easy to read, update, and extend.

---

## Features

- Automated tests for all User API operations (CRUD)
- Positive and negative test scenarios
- Request chaining (using data from one test in another)
- Easy configuration
- Detailed test reports (TestNG, Allure)
- CI/CD with GitHub Actions
- Local mock API for reliable testing

## Prerequisites

- Java 11 or higher
- Maven
- Node.js and npm (for the mock API)

## Step-by-Step Setup

### 1. Install Dependencies
- Make sure Java, Maven, and Node.js are installed on your machine.
- Install JSON Server globally (for the mock API):
  ```bash
  npm install -g json-server
  ```

### 2. Start the Mock API
- Go to the `mock-api` directory:
  ```bash
  cd mock-api
  ```
- Start the mock API server:
  ```bash
  json-server db.json --port 3000
  ```
- The API will be available at `http://localhost:3000`.

### 3. Update Configuration
- Open `src/test/resources/config.properties`.
- Set the base URL:
  ```
  baseUrl=http://localhost:3000
  ```

### 4. Run the Tests
- Go back to the main project directory.
- Run all tests with Maven:
  ```bash
  mvn clean test
  ```

### 5. View the Test Report
- To see a detailed Allure report:
  ```bash
  allure serve target/allure-results
  ```
- This will open the report in your browser.

### 6. Generate Detailed Execution Report
- Run the comprehensive test execution script:
  ```bash
  run-tests-with-detailed-report.bat
  ```
- This will generate multiple report formats:
  - **Allure HTML Report**: Interactive dashboard with detailed test results
  - **TestNG XML Reports**: Machine-readable test results
  - **Detailed Text Reports**: Human-readable execution summaries
  - **Cucumber HTML Reports**: Feature-based test results

## Detailed Execution Reporting

The test suite now includes comprehensive reporting capabilities that generate detailed execution reports highlighting pass/fail/skip results:

### Report Types Generated:

1. **Allure Reports** (`target/allure-report/`)
   - Interactive HTML dashboard
   - Test execution timeline
   - Detailed test steps and attachments
   - Pass/Fail/Skip statistics
   - Error details and stack traces

2. **TestNG Reports** (`target/surefire-reports/`)
   - XML format for CI/CD integration
   - Emailable HTML reports
   - Detailed test method results

3. **Detailed Text Reports** (`target/detailed-reports/`)
   - Human-readable execution summaries
   - Test-by-test breakdown
   - Success rate calculations
   - Execution timestamps

4. **Cucumber Reports** (`target/cucumber-reports.html`)
   - Feature-based test results
   - Step-by-step execution details

### Report Features:

- **Real-time Progress**: Shows test execution progress
- **Detailed Statistics**: Pass/Fail/Skip counts with percentages
- **Error Analysis**: Complete stack traces for failed tests
- **API Response Logging**: Request/response details for API tests
- **Test Data Tracking**: Shows test data used in each scenario
- **Execution Timeline**: Start/end times for each test
- **Success Rate Calculation**: Overall test suite performance metrics

## CI/CD Pipeline
- Every time you push code, GitHub Actions will:
  1. Set up Java, Maven, and Node.js
  2. Start the mock API
  3. Run all tests (unit and integration)
  4. Generate and save test and coverage reports
- You can find the workflow file at `.github/workflows/ci.yml`.
- Test results and coverage reports are saved as build artifacts in GitHub Actions.

## Sample Test Report

(You can add a screenshot or sample output here if you want)
