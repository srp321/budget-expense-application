# budget-expense-application
Budget Expense Application is part of assessment for MFW.
The implementation is based on following functional requirements mentioned:
- A client of the API should be able to post budget categories that include the name of the budget category as well as the amount budgeted for the category.
- A client of the API should be able to get all budget categories.
- A client of the API should be able to post one or more expense transactions. An expense should be associated with a valid budget category.
- A client of the API should be able to get a list of all expenses that were posted.
- A client of the API should be able to get a summary of all expenses aggregated by budget category. In addition the response should include what the budget is for each category as well as the percentage of the budget used for each category.

### Technology Selection
Spring boot is used as the framework for development of application because of the following reasons:
- Provides a lot of out-of-the-box functionality (embedded servers, dependency injection, configuration management)
- Ample availability of easily integrable long-running frameworks and libraries
- Scalable for high-performance volume data with distributed architecture support
- Eases of multi-level testing (unit tests, integration tests, performance tests)

### Prerequisites for Development and Local Testing
- JDK 1.11
- Apache Maven
- Docker

### Steps to build the Service
- Download or clone project from GitHub (https://github.com/srp321/budget-expense-application)
- Build the application (including running the tests) by executing below command
   ```
        mvn clean install
   ```

### Steps to execute the Service
- Run the application by executing command
   ```
        java -jar target/budget-assignment-1.0.0.jar
   ```

### Sources for API Documentation
- The documentation for the APIs as a swagger-ui html page APIs available at
  http://localhost:8080/swagger-ui.html
- The documentation for the APIs in OpenAPI json format APIs available at
  http://localhost:8080/v3/api-docs

### Steps to run application
- docker build --tag=budget-expense-application:latest
- docker run budget-expense-application:latest

### Additional Notes
- Few assumptions have been made regarding the way multiple Budget Category and Category Expenses and saved where invalid ones are not saved, but success is returned for valid records.
- Currently, an in-memory database (H2DB) has been used for the development of the application.
- For a persistent database such as PostgreSQL, the values in application-testing.properties needs to be updated and application should be started with that profile settings.
