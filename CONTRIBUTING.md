Disclaimer: As this is a public repository we want to inform you that this is an academy project for learning purposes and not to be taken as a real life project. 

## Contributing to Project Calculation Tool

Welcome to the Project Calculation Tool repository! Below are the guidelines to help new contributors get started.

### Prerequisites

To contribute, make sure you are familiar with the following:
- **Spring Boot Framework**: For understanding backend architecture.
- **MySQL**: For database interactions.
- **Thymeleaf**: For server-side HTML rendering.
- **Version Control**: Git and GitHub for version management.

Please ensure you have the necessary tools installed:
- Java JDK 17
- MySQL Server 8.0
- IDE (IntelliJ IDEA preferred)
- Git

### Getting Started

1. Fork the repository to your GitHub account.
2. Clone your forked repository:
   ```bash
   git clone https://github.com/KvasirSG/projektKalkulering.git
   ```
3. Set the upstream remote:
   ```bash
   git remote add upstream https://github.com/KvasirSG/projektKalkulering.git
   ```

### Development Workflow

1. **Branching**:
   - Always create a new branch for your changes.
   - Use descriptive names like `feature/task-manager` or `fix/database-connection`.

2. **Making Changes**:
   - Follow the coding style used in the repository.
   - Write clear, concise commit messages:
     ```bash
     git commit -m "Add task assignment feature"
     ```

3. **Testing**:
   - Run all unit and integration tests to ensure your changes don’t break the application:
     ```bash
     mvn test
     ```

4. **Pull Requests**:
   - Submit a pull request to the `develop` branch.
   - Include a clear description of the changes.

### Setting Up Local Environment

1. Configure your database:
   - Create a MySQL database:
     ```sql
     CREATE DATABASE project_db;
     ```
   - Import the provided SQL script located in the `resources/sql` folder.

2. Run the application locally:
   - Start the Spring Boot application:
     ```bash
     mvn spring-boot:run
     ```

3. Access the app:
   - Navigate to [http://localhost:8080](http://localhost:8080).

### Guidelines for Contribution

- Write clear and maintainable code.
- Comment your code to explain complex logic.
- Ensure all features are well-documented in the repository.
