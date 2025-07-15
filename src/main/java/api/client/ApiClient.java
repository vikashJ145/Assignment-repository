
package api.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import api.config.ConfigManager;

public class ApiClient {
    static {
        RestAssured.baseURI = ConfigManager.get("baseUrl");
    }

    public static Response get(String endpoint) {
        return RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .when()
                .get(endpoint)
                .then().log().all().extract().response();
    }

    public static Response post(String endpoint, Object body) {
        return RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then().log().all().extract().response();
    }

    public static Response put(String endpoint, Object body) {
        return RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put(endpoint)
                .then().log().all().extract().response();
    }

    public static Response delete(String endpoint) {
        return RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .when()
                .delete(endpoint)
                .then().log().all().extract().response();
    }
}
