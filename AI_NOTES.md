# AI_NOTES.md

# AI Usage Notes

## AI Tools Used

I used ChatGPT as a development assistant throughout this assignment. AI was used to brainstorm the solution, discuss architectural decisions, review implementation choices, generate initial code, improve code quality, and assist with testing.

Rather than generating the complete project in one go, I used AI iteratively while making the implementation decisions myself, reviewing the generated code, testing every feature, and integrating the final solution into the project.

---

# Design & Planning

Before writing any code, I first analyzed the assignment requirements and used AI to discuss how they could be translated into REST APIs.

From the assignment requirements, we identified the following endpoints:

| Method | Endpoint | Purpose |
|---------|----------|---------|
| POST | `/expenses` | Add a new expense |
| GET | `/expenses` | Retrieve all expenses |
| GET | `/expenses?category=FOOD` | Filter expenses by category |
| GET | `/expenses/total` | Calculate total expenses |
| GET | `/expenses/total?category=FOOD` | Calculate total expenses by category |
| DELETE | `/expenses/{id}` | Delete an expense |

Before implementation, we also discussed the overall project structure and selected a layered architecture consisting of:

- Controller
- Service
- Repository
- DTO
- Model
- Exception Handling
- Configuration

During the planning phase, we also discussed and decided on the following implementation choices:

- Use in-memory storage (`ConcurrentHashMap`) instead of a database, as required by the assignment.
- Use `UUID` as the unique identifier for each expense.
- Use `BigDecimal` for monetary values.
- Represent expense categories using an enum.
- Use DTOs for request and response handling.
- Introduce a generic API response wrapper (`GenericResponse<T>`) to maintain consistent API responses.
- Add Swagger/OpenAPI documentation as the optional bonus feature.

---

# Why a Model Was Used

Although the assignment did not require a database, we decided to create an `Expense` model because it represents the application's core business object rather than a database entity.

The model:

- Represents an expense throughout the application.
- Keeps the business layer independent from the API layer.
- Separates internal business objects from request and response DTOs.
- Makes it easier to replace the in-memory repository with a database implementation in the future without changing the service or controller layers.

This follows a clean layered architecture commonly used in Spring Boot applications.

---

# Development Approach

Instead of generating the complete project at once, the application was developed incrementally.

Each feature was completed using the following workflow:

1. Brainstorm the API design and required classes.
2. Implement only that API or feature.
3. Test the endpoint manually using Postman.
4. Write unit tests using JUnit 5, Mockito, and MockMvc.
5. Ensure all tests passed successfully.
6. Commit the feature.
7. Push the feature branch to GitHub.
8. Create a Pull Request with an appropriate description.
9. Merge the Pull Request into the `main` branch.
10. Pull the latest changes from `main` before starting the next feature.

This incremental approach ensured that every feature was completed, validated, and integrated before moving on to the next requirement.

---

# Git Workflow

The project was developed using a feature branch workflow instead of implementing everything directly on the `main` branch.

For every feature, the following workflow was followed:

1. Create a new feature branch from the latest `main` branch.
2. Implement a single API or feature.
3. Test the implementation manually using Postman.
4. Write and execute unit tests.
5. Commit the completed feature.
6. Push the feature branch to GitHub.
7. Raise a Pull Request with a description of the implemented feature.
8. Merge the Pull Request into the `main` branch.
9. Pull the latest changes from the updated `main` branch before creating the next feature branch.

This workflow was consistently followed throughout the project, resulting in a clean and traceable Git history where each Pull Request represents one completed and tested feature.

Feature branches created during development include:

- `feat/add-expense`
- `feat/get-expenses`
- `feat/filter-expenses`
- `feat/total-expense`
- `feat/total-category-expense`
- `feat/delete-expense`
- `feat/swagger`
- `fix/generic-response`

---

# AI-Assisted Work

AI assisted with:

- Brainstorming the overall solution and REST API design.
- Designing the layered project architecture.
- Generating initial implementations for controllers, services, repositories, DTOs, models, and exception handling.
- Suggesting improvements to project structure.
- Generating Swagger/OpenAPI configuration.
- Generating unit tests using JUnit 5, Mockito, and MockMvc.
- Reviewing validation logic and exception handling.
- Suggesting improvements for code readability and API consistency.

---

# What I Reviewed and Modified

I reviewed and modified all AI-generated code before integrating it into the project.

This included:

- Refactoring generated code for readability and maintainability.
- Fixing compilation issues.
- Updating package names and imports to match the project structure.
- Introducing a generic response wrapper (`GenericResponse<T>`) for consistent API responses.
- Updating controller methods to use the generic response format.
- Updating unit tests after response structure changes.
- Fixing failing test cases.
- Verifying every endpoint manually using Postman.
- Running the complete Maven test suite.
- Reviewing the generated Swagger documentation to ensure every endpoint was documented correctly.

---

# AI Suggestions Not Used

Some AI suggestions were intentionally not implemented because they were outside the scope of the assignment.

Examples include:

- Using JPA/Hibernate with a relational database.
- Adding Spring Security and authentication.
- Introducing MapStruct for object mapping.
- Dockerizing the application.

These suggestions were intentionally not adopted because the assignment explicitly required in-memory storage and only one optional bonus feature. I chose to implement Swagger/OpenAPI documentation as the bonus feature.

---

# Validation Performed

Before submission, I validated the application by:

- Running the application locally.
- Testing every REST endpoint using Postman.
- Executing the complete Maven test suite.
- Verifying the generated Swagger/OpenAPI documentation.
- Reviewing API responses for consistency using the generic response wrapper.
- Reviewing the Git history to ensure each feature was implemented, tested, committed, and merged incrementally.