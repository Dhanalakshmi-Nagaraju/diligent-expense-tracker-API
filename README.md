# Expense Tracker API

A RESTful Expense Tracker API built using **Java**, **Spring Boot**, and **Maven**. This application allows users to manage personal expenses by adding, viewing, filtering, calculating totals, and deleting expenses. Data is stored in-memory using a `ConcurrentHashMap`, as required by the assignment.

---

## Features

- Add a new expense
- View all expenses
- Filter expenses by category
- Calculate total expenses
- Calculate total expenses by category
- Delete an expense
- Input validation using Bean Validation
- Global exception handling
- Generic API response wrapper
- Swagger/OpenAPI documentation
- Unit tests using JUnit 5, Mockito, and MockMvc

---

## Tech Stack

- Java 17
- Spring Boot 3.5.x
- Maven
- Lombok
- Spring Validation
- Swagger / OpenAPI
- JUnit 5
- Mockito
- MockMvc

---

## Project Structure

```
expense-tracker
│
├── src
│   ├── main
│   │   ├── controller
│   │   ├── dto
│   │   ├── exception
│   │   ├── model
│   │   ├── repository
│   │   ├── service
│   │   └── config
│   │
│   └── test
│       ├── controller
│       └── service
│
├── README.md
├── AI_NOTES.md
└── pom.xml
```

---

## API Endpoints

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/expenses` | Add a new expense |
| GET | `/expenses` | Get all expenses |
| GET | `/expenses?category=FOOD` | Filter expenses by category |
| GET | `/expenses/total` | Get total expenses |
| GET | `/expenses/total?category=FOOD` | Get total by category |
| DELETE | `/expenses/{id}` | Delete an expense |

---

## Installation

Clone the repository

```bash
git clone https://github.com/<your-username>/expense-tracker.git
```

Move into the project

```bash
cd expense-tracker
```

---

## Install Dependencies

```bash
mvn clean install
```

---

## Run the Application

```bash
mvn spring-boot:run
```

The application starts on

```
http://localhost:8080
```

---

## Swagger Documentation

Open

```
http://localhost:8080/swagger-ui/index.html
```

---

## Run Tests

```bash
mvn test
```

---

## Sample Request

### Add Expense

**POST** `/expenses`

```json
{
  "title": "Coffee",
  "amount": 120,
  "category": "FOOD",
  "date": "2026-08-02"
}
```

---

## Sample Response

```json
{
  "success": true,
  "message": "Expense created successfully",
  "data": {
    "id": "3c3d5cb6-d0f5-45b7-a2dd-cfd93dbba650",
    "title": "Coffee",
    "amount": 120,
    "category": "FOOD",
    "date": "2026-08-02"
  }
}
```

---

## Validation

The API validates incoming requests using Bean Validation.

Examples:

- Title cannot be blank
- Amount must be greater than zero
- Category is required
- Date is required

---

## Testing

The project contains:

- Service layer unit tests
- Controller layer tests using MockMvc
- Validation tests

---

## Author

**Dhanalakshmi N**