# BootBlog Backend

A secure and scalable Blog Platform REST API built using Spring Boot. The application provides authentication and authorization using JWT, role-based access control, blog post management, category management, comments, user profiles, pagination, validation, and API documentation.

## Features

### Authentication & Authorization

* User Registration
* User Login
* JWT Authentication
* Role-Based Authorization (USER, ADMIN)

### Categories

* Create Category
* Get All Categories
* Get Category By Id
* Update Category
* Delete Category

### Posts

* Create Post
* Get All Posts
* Get Post By Id
* Get Posts By Category
* Search Posts
* Update Post (Owner Only)
* Delete Post (Owner/Admin)

### Comments

* Create Comment
* Get Comment By Id
* Get Comments By Post
* Update Comment (Owner Only)
* Delete Comment (Owner/Admin)
* Get Comments By User
* Get My Comments

### User Profile

* Get My Profile
* Update My Profile

### Additional Features

* DTO Validation
* Global Exception Handling
* Pagination
* Swagger/OpenAPI Documentation
* Environment Variable Based Configuration

## Tech Stack

* Java 21
* Spring Boot
* Spring Security
* JWT (JSON Web Tokens)
* Spring Data JPA
* Hibernate
* MySQL
* ModelMapper
* Maven
* Swagger/OpenAPI

## API Documentation

Swagger UI:

http://localhost:8080/swagger-ui/index.html

## Environment Variables

Configure the following environment variables:

```properties
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password

JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000
```

## Running the Project

1. Clone the repository

```bash
git clone https://github.com/siddardha-007/bootblog-backend.git
```

2. Navigate to the project

```bash
cd bootblog-backend
```

3. Configure environment variables

4. Run the application

```bash
mvn spring-boot:run
```

## Author

Siddardha Bangaru

B.Tech Computer Science Engineering
GITAM University
