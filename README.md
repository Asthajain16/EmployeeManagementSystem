# Employee Management System

Spring Boot REST API for managing employees with full CRUD support.

# Features
- Add, view, update, and delete employees
- RESTful API design using Spring Boot
- Integrated input validation with Jakarta Bean Validation
- OpenAPI documentation using springdoc-openapi and Swagger UI
- Integration-tested with MockMvc and H2
  
# Instructions to setup and run project

    ## Run all integration and unit tests
        ./mvnw test
        
    ## Build and run
        ./mvnw spring-boot:run
    
    API: http://localhost:8080/api/employees
    
    Swagger UI: http://localhost:8080/swagger-ui/index.html
    <img width="1649" height="887" alt="image" src="https://github.com/user-attachments/assets/7cb0e443-18f6-419f-8326-d512cc1b5054" />

    Base URL: `http://localhost:8080/api/employees`

    | Method | Endpoint             | Description               |
    |--------|----------------------|---------------------------|
    | GET    | `/`                  | Fetch all employees       |
    | GET    | `/{id}`              | Get employee by ID        |
    | POST   | `/`                  | Add a new employee        |
    | PUT    | `/{id}`              | Update existing employee  |
    | DELETE | `/{id}`              | Delete employee by ID     |
      
    All endpoints are validated. Invalid inputs return appropriate error messages.


# Technologies
- Java 17
- Spring Boot
- JPA (Hibernate)
- PostgreSQL
- Swagger (OpenAPI)
