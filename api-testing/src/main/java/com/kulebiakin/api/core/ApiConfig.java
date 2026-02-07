package com.kulebiakin.api.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiConfig {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    public static final String POSTS_ENDPOINT = "/posts";
    public static final String USERS_ENDPOINT = "/users";

    public static final String CONTENT_TYPE_JSON = "application/json";
}
