package com.kulebiakin.api.tests;

import com.kulebiakin.api.domain.model.Post;
import com.kulebiakin.api.domain.model.User;
import com.kulebiakin.api.domain.service.UserService;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayName("Users API Tests")
class UserApiTest {

    private final UserService userService = new UserService();

    // Positive Tests
    @Test
    @DisplayName("GET /users - should return all users with status 200")
    void getAllUsersShouldReturnAllUsers() {
        Response response = userService.getAllUsers();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getContentType()).contains("application/json");

        List<User> users = response.jsonPath().getList(".", User.class);
        assertThat(users).isNotEmpty();

        var firstUser = users.get(0);
        assertSoftly(softly -> {
            softly.assertThat(firstUser.getId()).isNotNull();
            softly.assertThat(firstUser.getName()).isNotBlank();
            softly.assertThat(firstUser.getUsername()).isNotBlank();
            softly.assertThat(firstUser.getEmail()).isNotBlank();
        });
    }

    @Test
    @DisplayName("GET /users/{id} - should return specific user by valid ID")
    void getUserByIdWithValidIdShouldReturnUser() {
        int userId = 1;
        Response response = userService.getUserById(userId);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getContentType()).contains("application/json");

        var user = response.as(User.class);
        assertSoftly(softly -> {
            softly.assertThat(user.getId()).isEqualTo(userId);
            softly.assertThat(user.getName()).isEqualTo("Leanne Graham");
            softly.assertThat(user.getUsername()).isEqualTo("Bret");
            softly.assertThat(user.getEmail()).isEqualTo("Sincere@april.biz");
        });
    }

    @Test
    @DisplayName("GET /users/{id} - should return user with complete address")
    void getUserByIdShouldReturnUserWithAddress() {
        Response response = userService.getUserById(1);

        assertThat(response.getStatusCode()).isEqualTo(200);

        var user = response.as(User.class);
        assertSoftly(softly -> {
            softly.assertThat(user.getAddress()).isNotNull();
            softly.assertThat(user.getAddress().getStreet()).isNotBlank();
            softly.assertThat(user.getAddress().getCity()).isNotBlank();
            softly.assertThat(user.getAddress().getZipcode()).isNotBlank();
            softly.assertThat(user.getAddress().getGeo()).isNotNull();
            softly.assertThat(user.getAddress().getGeo().getLat()).isNotBlank();
            softly.assertThat(user.getAddress().getGeo().getLng()).isNotBlank();
        });
    }

    @Test
    @DisplayName("GET /users/{id} - should return user with company info")
    void getUserByIdShouldReturnUserWithCompany() {
        Response response = userService.getUserById(1);

        assertThat(response.getStatusCode()).isEqualTo(200);

        var user = response.as(User.class);
        assertSoftly(softly -> {
            softly.assertThat(user.getCompany()).isNotNull();
            softly.assertThat(user.getCompany().getName()).isNotBlank();
            softly.assertThat(user.getCompany().getCatchPhrase()).isNotBlank();
            softly.assertThat(user.getCompany().getBs()).isNotBlank();
        });
    }

    @Test
    @DisplayName("GET /users/{id}/posts - should return all posts for user")
    void getUserPostsShouldReturnUserPosts() {
        int userId = 1;
        Response response = userService.getUserPosts(userId);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getContentType()).contains("application/json");

        List<Post> posts = response.jsonPath().getList(".", Post.class);
        assertThat(posts).isNotEmpty();

        assertSoftly(softly -> {
            for (Post post : posts) {
                softly.assertThat(post.getUserId()).isEqualTo(userId);
            }
        });
    }

    @Test
    @DisplayName("GET /users - all users should have valid email format")
    void getAllUsersShouldHaveValidEmails() {
        Response response = userService.getAllUsers();

        assertThat(response.getStatusCode()).isEqualTo(200);

        List<User> users = response.jsonPath().getList(".", User.class);
        assertSoftly(softly -> {
            for (User user : users) {
                softly.assertThat(user.getEmail()).contains("@");
            }
        });
    }

    // Negative Tests
    @Test
    @DisplayName("GET /users/{id} - should return 404 for non-existent user")
    void getUserByIdWithInvalidIdShouldReturnNotFound() {
        int invalidId = 99999;
        Response response = userService.getUserById(invalidId);

        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("GET /users/{id} - should handle non-numeric ID")
    void getUserByIdWithNonNumericIdShouldReturnNotFound() {
        Response response = userService.getUserById("invalid");

        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("GET /users/{id}/posts - should return empty array for user with no posts")
    void getUserPostsForInvalidUserShouldReturnEmptyArray() {
        Response response = userService.getUserPosts(99999);

        assertThat(response.getStatusCode()).isEqualTo(200);
        List<Post> posts = response.jsonPath().getList(".", Post.class);
        assertThat(posts).isEmpty();
    }

    @Test
    @DisplayName("GET /users - response time should be acceptable")
    void getAllUsersShouldRespondWithinAcceptableTime() {
        Response response = userService.getAllUsers();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getTime()).isLessThan(5000L);
    }
}
