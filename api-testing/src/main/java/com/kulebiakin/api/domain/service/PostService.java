package com.kulebiakin.api.domain.service;

import com.kulebiakin.api.core.ApiConfig;
import com.kulebiakin.api.domain.model.Post;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostService {

    public Response getAllPosts() {
        return given()
            .baseUri(ApiConfig.BASE_URL)
            .contentType(ContentType.JSON)
            .when()
            .get(ApiConfig.POSTS_ENDPOINT);
    }

    public Response getPostById(Object id) {
        return given()
            .baseUri(ApiConfig.BASE_URL)
            .contentType(ContentType.JSON)
            .when()
            .get(ApiConfig.POSTS_ENDPOINT + "/" + id);
    }

    public Response createPost(Post post) {
        return given()
            .baseUri(ApiConfig.BASE_URL)
            .contentType(ContentType.JSON)
            .body(post)
            .when()
            .post(ApiConfig.POSTS_ENDPOINT);
    }

    public Response updatePost(int id, Post post) {
        return given()
            .baseUri(ApiConfig.BASE_URL)
            .contentType(ContentType.JSON)
            .body(post)
            .when()
            .put(ApiConfig.POSTS_ENDPOINT + "/" + id);
    }

    public Response deletePost(int id) {
        return given()
            .baseUri(ApiConfig.BASE_URL)
            .contentType(ContentType.JSON)
            .when()
            .delete(ApiConfig.POSTS_ENDPOINT + "/" + id);
    }
}
