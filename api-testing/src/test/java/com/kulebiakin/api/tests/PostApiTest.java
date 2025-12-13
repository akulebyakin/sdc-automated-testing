package com.kulebiakin.api.tests;

import com.kulebiakin.api.domain.model.Post;
import com.kulebiakin.api.domain.service.PostService;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.kulebiakin.api.core.ApiConfig.CONTENT_TYPE_JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayName("Posts API Tests")
class PostApiTest {

    private final PostService postService = new PostService();

    // Positive Tests
    @Test
    @DisplayName("GET /posts - should return all posts with status 200")
    void getAllPostsShouldReturnAllPosts() {
        Response response = postService.getAllPosts();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getContentType()).contains(CONTENT_TYPE_JSON);

        List<Post> posts = response.jsonPath().getList(".", Post.class);
        assertThat(posts).isNotEmpty();

        var firstPost = posts.get(0);
        assertSoftly(softly -> {
            softly.assertThat(firstPost.getId()).isNotNull();
            softly.assertThat(firstPost.getUserId()).isNotNull();
            softly.assertThat(firstPost.getTitle()).isNotBlank();
            softly.assertThat(firstPost.getBody()).isNotBlank();
        });
    }

    @Test
    @DisplayName("GET /posts/{id} - should return specific post by valid ID")
    void getPostByIdWithValidIdShouldReturnPost() {
        int postId = 1;
        Response response = postService.getPostById(postId);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getContentType()).contains(CONTENT_TYPE_JSON);

        var post = response.as(Post.class);
        assertSoftly(softly -> {
            softly.assertThat(post.getId()).isEqualTo(postId);
            softly.assertThat(post.getUserId()).isEqualTo(1);
            softly.assertThat(post.getTitle()).isNotBlank();
            softly.assertThat(post.getBody()).isNotBlank();
        });
    }

    @Test
    @DisplayName("POST /posts - should create new post with status 201")
    void createPostShouldReturnCreatedPost() {
        var newPost = new Post(1, "Test Title", "Test Body Content");
        Response response = postService.createPost(newPost);

        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(response.getContentType()).contains(CONTENT_TYPE_JSON);

        var createdPost = response.as(Post.class);
        assertSoftly(softly -> {
            softly.assertThat(createdPost.getId()).isNotNull();
            softly.assertThat(createdPost.getUserId()).isEqualTo(newPost.getUserId());
            softly.assertThat(createdPost.getTitle()).isEqualTo(newPost.getTitle());
            softly.assertThat(createdPost.getBody()).isEqualTo(newPost.getBody());
        });
    }

    @Test
    @DisplayName("PUT /posts/{id} - should update existing post")
    void updatePostShouldReturnUpdatedPost() {
        int postId = 1;
        var updatedPost = new Post(1, "Updated Title", "Updated Body");
        Response response = postService.updatePost(postId, updatedPost);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getContentType()).contains(CONTENT_TYPE_JSON);

        var resultPost = response.as(Post.class);
        assertSoftly(softly -> {
            softly.assertThat(resultPost.getId()).isEqualTo(postId);
            softly.assertThat(resultPost.getTitle()).isEqualTo(updatedPost.getTitle());
            softly.assertThat(resultPost.getBody()).isEqualTo(updatedPost.getBody());
        });
    }

    @Test
    @DisplayName("DELETE /posts/{id} - should delete post with status 200")
    void deletePostShouldReturnSuccess() {
        int postId = 1;
        Response response = postService.deletePost(postId);

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    // Negative Tests
    @Test
    @DisplayName("GET /posts/{id} - should return 404 for non-existent ID")
    void getPostByIdWithInvalidIdShouldReturnNotFound() {
        int invalidId = 99999;
        Response response = postService.getPostById(invalidId);

        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("GET /posts/{id} - should handle non-numeric ID gracefully")
    void getPostByIdWithNonNumericIdShouldReturnNotFound() {
        Response response = postService.getPostById("abc");

        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("POST /posts - should handle empty body")
    void createPostWithEmptyFieldsShouldStillCreate() {
        var emptyPost = new Post();
        Response response = postService.createPost(emptyPost);

        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(response.jsonPath().getInt("id")).isPositive();
    }

    @Test
    @DisplayName("GET /posts - response time should be acceptable")
    void getAllPostsShouldRespondWithinAcceptableTime() {
        Response response = postService.getAllPosts();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getTime()).isLessThan(5000L);
    }

    @Test
    @DisplayName("GET /posts/{id} - should have correct response headers")
    void getPostByIdShouldHaveCorrectHeaders() {
        Response response = postService.getPostById(1);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getHeader("Content-Type")).contains(CONTENT_TYPE_JSON);
        assertThat(response.getHeader("Cache-Control")).isNotNull();
    }
}
