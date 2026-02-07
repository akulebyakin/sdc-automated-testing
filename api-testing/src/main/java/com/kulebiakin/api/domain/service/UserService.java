package com.kulebiakin.api.domain.service;

import com.kulebiakin.api.core.ApiConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserService {

    public Response getAllUsers() {
        return given()
            .baseUri(ApiConfig.BASE_URL)
            .contentType(ContentType.JSON)
            .when()
            .get(ApiConfig.USERS_ENDPOINT);
    }

    public Response getUserById(Object id) {
        return given()
            .baseUri(ApiConfig.BASE_URL)
            .contentType(ContentType.JSON)
            .when()
            .get(ApiConfig.USERS_ENDPOINT + "/" + id);
    }

    public Response getUserPosts(int userId) {
        return given()
            .baseUri(ApiConfig.BASE_URL)
            .contentType(ContentType.JSON)
            .when()
            .get(ApiConfig.USERS_ENDPOINT + "/" + userId + "/posts");
    }
}
