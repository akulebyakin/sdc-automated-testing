# API Testing Module

REST API testing module using Rest Assured framework against [JSONPlaceholder](https://jsonplaceholder.typicode.com/)
API.

## Project Structure

```
api-testing/
├── src/main/java/com/kulebiakin/api/
│   ├── core/
│   │   └── ApiConfig.java          # API configuration (base URL, endpoints)
│   └── domain/
│       ├── model/
│       │   ├── Post.java           # Post entity
│       │   └── User.java           # User entity with nested Address, Company, Geo
│       └── service/
│           ├── PostService.java    # Posts API client
│           └── UserService.java    # Users API client
└── src/test/java/com/kulebiakin/api/tests/
    ├── PostApiTest.java            # Posts endpoint tests
    └── UserApiTest.java            # Users endpoint tests
```

## Technologies

- **Rest Assured** - REST API testing library
- **JUnit 5** - Testing framework
- **AssertJ** - Fluent assertions with SoftAssertions
- **Lombok** - Reduces boilerplate code
- **Jackson** - JSON serialization/deserialization

## API Endpoints Tested

### Posts (`/posts`)

- `GET /posts` - Get all posts
- `GET /posts/{id}` - Get post by ID
- `POST /posts` - Create new post
- `PUT /posts/{id}` - Update post
- `DELETE /posts/{id}` - Delete post

### Users (`/users`)

- `GET /users` - Get all users
- `GET /users/{id}` - Get user by ID
- `GET /users/{id}/posts` - Get posts by user

## Running Tests

```bash
# Run all tests
mvn test -pl api-testing

# Run specific test class
mvn test -pl api-testing -Dtest=PostApiTest

# Run specific test method
mvn test -pl api-testing -Dtest=PostApiTest#getAllPostsShouldReturnAllPosts
```

## Test Categories

### Positive Tests

- Verify successful responses (200, 201)
- Validate response structure and content
- Check nested objects (Address, Company, Geo)
- Verify response headers and content type

### Negative Tests

- Non-existent resource (404)
- Invalid ID formats
- Empty request bodies
- Response time validation
