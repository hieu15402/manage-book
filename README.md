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

- **URL:** `/books`
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
