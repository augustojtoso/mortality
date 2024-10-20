# Mortality Rates

## Description

The Mortality Rates API is a Spring Boot application that provides endpoints for managing and retrieving mortality rates by year and country. The API supports operations such as uploading CSV files with mortality rates and querying mortality rates for specific years and countries. Additionally, the application includes a frontend interface built with modern web technologies, allowing users to interact with the API through a user-friendly web interface.

## Features

- Upload mortality rates (via CSV files) for multiple countries for a given year. 
- Retrieve mortality rates by year.
- Update mortality rates for specific countries

## Technologies 

### Backend

- Java
- Spring Boot
- Maven
- Cucumber (for testing)
- Swagger (for API documentation)

### Frontend

 - React

## Usage
### Setup Instructions

#### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Maven
- Node.js and npm (for the frontend)
- Docker (optional, for containerization)

#### Backend Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/mortality-rates-api.git
    cd mortality-rates-api
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

3. Run the Spring Boot application:
    ```sh
    mvn spring-boot:run
    ```

The backend server should now be running on `http://localhost:8080`.

#### Frontend Setup

1. Navigate to the frontend directory:
    ```sh
    cd mortality-front
    ```

2. Install the dependencies:
    ```sh
    npm install
    ```

3. Start the React application:
    ```sh
    npm start
    ```

The frontend application should now be running on `http://localhost:3000`.

### Database Setup
This service uses an H2 database that is initially empty. You can populate the database using mock data from a CSV file. Follow these steps to upload the CSV file using the provided API route:

1. Ensure the backend server is running:
    ```sh
    mvn spring-boot:run
    ```

2. Use the API route to upload the CSV file (`MORTALITY2019.CSV`):
You can find the `MORTALITY2019.CSV` file [here](../path/to/MORTALITY2019.CSV).
3. 
### Postman Collection

To test the API endpoints, you can use the provided Postman collection: [`OpenAPI definition.postman_collection.json`](src/main/resources/OpenAPI%20definition.postman_collection.json).
#### Importing the Postman Collection

1. Open Postman.
2. Click on the **Import** button in the top left corner.
3. Select the **Upload Files** tab.
4. Click on **Choose Files** and select the `OpenAPI definition.postman_collection.json` file.
5. Click **Open** to import the collection.

### API Endpoints

- **GET /mortalityrates/years/{yearOfRate}/countries**: Retrieve mortality rates for a specific year.
- **POST /mortalityrates/years/{yearOfRate}/countries**: Upload a CSV file with mortality rates for a specific year.
- **POST /mortalityrates/years/{yearOfRate}/countries/{countryCode}**: Update mortality rates for a specific country.
