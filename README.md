Disclaimer: As this is a public repository we want to inform you that this is an academy project for learning purposes and not to be taken as a real life project.
# projectCalculations Tool

Its an application for project calculation where you can create projects, subprojects and tasks as well as allocate resources and calculate estimates. 


## Authors

- [Kenneth](https://github.com/KvasirSG)
- [Sebastian](https://github.com/Duofour)


### Software Prerequisites

To run this application, the following software and versions are required:
- **Java JDK 17** (or later)

Ensure you have all dependencies installed and configured on your system.

### Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/KvasirSG/projektKalkulering.git
   ```
2. Configure your `enviroment variables`with your MySQL and Azure settings:
   ```properties
   PROD_DB_PWD: DB password
   PROD_DB_URL: DB URL, example: jdbc:mysql://localhost:3306/projectApp
   PROD_DB_USER: DB username
   ```
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
5. Access the application in your browser at:
   [http://localhost:8080](http://localhost:8080)

### Deployed Application

The live application is deployed at:
[Azure Deployment Link](f24c1projkal-b7g7bhebbwe2a8g7.northeurope-01.azurewebsites.net)


