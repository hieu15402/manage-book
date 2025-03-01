# Book Management API

This is a simple Book Management API built using Spring Boot 3.4.1 and Java 17, with PostgreSQL as the database.

## Features

- CRUD operations for managing books and authors
- Book schema includes:
  - id (Primary Key)
  - title (String)
  - author_id (FK)
  - published_date (Date)
  - isbn (String, unique)
  - price (Decimal)
- Author schema (optional):
  - id (Primary Key)
  - name (String)
  - nationality (String)
  
## Endpoints

### 1. Create a Book

- **URL:** `/api/books`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "title": "Effective Java",
    "authorId": 1,
    "publishedDate": "2022-12-03",
    "isbn": "9780134685121",
    "price": 40.00
  }
- **Response:**
  ```json
   {
    "code": 201,
    "message": "Book created successfully",
    "result": {
        "id": 6,
        "title": "Effective Java",
        "authorId": 1,
        "publishedDate": "2022-12-03",
        "isbn": "9780134685121",
        "price": 40.00
    }
  }
  
### 2. Get a Book by ID

- **URL:** `/api/books/{id}`
- **Method:** `GET`
- **Response:**
  ```json
   {
    "code": 200,
    "message": "Book fetched successfully",
    "result": {
        "id": 6,
        "title": "Effective Java",
        "author": {
            "id": 1,
            "name": "Nguyen Minh Hieu",
            "nationality": "Viet Nam"
        },
        "publishedDate": "2022-12-03",
        "isbn": "9780134685121",
        "price": 40.00
    }
  }
### 3. Get Books with filtering and pagination

- **URL:** `/api/books`
- **Method:** `GET`
- **Query Parameters:**
  - page: Page number (default: 1)
  - size: Page size (default: 10)
  - authorId: Filter by author ID
  - publishedDate: Filter by publication date
  - title: Filter by title (contain keyword)
  - minPrice: Filter by minimum price
  - maxPrice: Filter by maximum price
- **Response:**
  ```json
   {
    "code": 200,
    "message": "Book fetched successfully",
    "result": {
        "currentPage": 1,
        "totalPages": 1,
        "pageSize": 10,
        "totalElements": 5,
        "data":[
            {
                "id": 1,
                "title": "Advanced Java Programming",
                "authorId": 1,
                "publishedDate": "2025-01-11",
                "isbn": "9780743271246",
                "price": 20.11
            },
            {
                "id": 2,
                "title": "Mastering Spring Framework",
                "authorId": 1,
                "publishedDate": "2024-10-15",
                "isbn": "9780134685991",
                "price": 35.99
            },
            {
                "id": 3,
                "title": "Java Design Patterns",
                "authorId": 2,
                "publishedDate": "2023-07-22",
                "isbn": "9780135166307",
                "price": 45.50
            },
            {
                "id": 4,
                "title": "Spring Boot Essentials",
                "authorId": 2,
                "publishedDate": "2024-05-18",
                "isbn": "9780136576129",
                "price": 27.30
            },
            {
                "id": 6,
                "title": "Effective Java",
                "authorId": 1,
                "publishedDate": "2022-12-03",
                "isbn": "9780134685121",
                "price": 40.00
            }
        ]
    }
  }
### 4. Update a book

- **URL:** `/api/books/{id}`
- **Method:** `PUT`
- **Request Body:**
  ```json
  {
    "title": "Effective Java",
    "authorId": 1, 
    "publishedDate": "2022-12-03", 
    "price": 42.00 
  }
- **Response:**
  ```json
  {
      "code": 200,
      "message": "Book updated successfully",
      "result": {
          "id": 6,
          "title": "Effective Java",
          "authorId": 1,
          "publishedDate": "2022-12-03",
          "isbn": "9780134685121",
          "price": 42.00
      }
  }
### 5. Delete a book

- **URL:** `/api/books/{id}`
- **Method:** `DELETE`
- **Response:**
  ```json
  {
    "code": 200,
    "message": "Book deleted successfully"
  }
## Features
- The application includes proper error handling, and you will receive meaningful error messages if something goes wrong. Some common error codes are:
  - 400 Bad Request: Invalid input
  - 404 Not Found: Book or author not found.
  - 409 Conflict: ISBN already exists.
## Technologies Used
- Spring Boot 3.4.1
- Java 17
- PostgreSQL
- JPA (Hibernate)
## Conclusion
- This is a simple Book Management System that demonstrates how to build a RESTful API with Spring Boot, validate inputs, handle errors, and interact with a PostgreSQL database.
