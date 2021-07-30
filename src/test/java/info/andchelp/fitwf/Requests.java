package info.andchelp.fitwf;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class Requests {


    public static ValidatableResponse request(Method method, String uri, int status, Object body) {
        return given()
                .body(body)
                .contentType(ContentType.JSON)
                .when().request(method, uri)
                .then().statusCode(status)
                .log().body();
    }

    public static ValidatableResponse post(String uri, Object body) {
        return post(uri, HttpStatus.SC_OK, body);
    }

    public static ValidatableResponse post(String uri, int status, Object body) {
        return request(Method.POST, uri, status, body);
    }
}
