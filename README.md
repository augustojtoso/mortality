# Mortality Rates API

## Description

The Mortality Rates API is a Spring Boot application that provides endpoints for managing and retrieving mortality rates by year and country. The API supports operations such as uploading CSV files with mortality rates and querying mortality rates for specific years and countries.

## Features

- Upload mortality rates via CSV files
- Retrieve mortality rates by year
- Update mortality rates for specific countries
- Validation of input data

## Technologies

- Java
- Spring Boot
- Maven
- Cucumber (for testing)
- Swagger (for API documentation)

## Usage

### Postman Collection

To test the API endpoints, you can use the provided Postman collection: [`OpenAPI definition.postman_collection.json`](src/main/resources/OpenAPI%20definition.postman_collection.json).
#### Importing the Postman Collection

1. Open Postman.
2. Click on the **Import** button in the top left corner.
3. Select the **Upload Files** tab.
4. Click on **Choose Files** and select the `OpenAPI definition.postman_collection.json` file.
5. Click **Open** to import the collection.

### API Endpoints


- **GET /MortalityRates/years/{yearOfRate}/countries**: Retrieve mortality rates for a specific year.
- **POST /MortalityRates/years/{yearOfRate}/countries**: Upload a CSV file with mortality rates for a specific year.
- **POST /MortalityRates/years/{yearOfRate}/countries/{countryCode}**: Update mortality rates for a specific country.

### Example Requests

#### Retrieve Mortality Rates

```sh
curl -X GET "http://localhost:8080/MortalityRates/years/2016/countries"