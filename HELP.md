# Mortality Rates Service

## Part 1: Backend

An insurance company needs a service and an application for maintaining mortality tables. A simplified mortality table allows knowing, for a given year, in a given country, the number of deaths per 1000 inhabitants according to gender.

The country code is an acronym according to the ISO 3166 standard. Mortality rates have two decimal places and are limited between 0.00 and 1000.00.

The REST service should allow:
- Querying a mortality table for a given year.
- Updating/creating a mortality rate record for a given year and country.
- When registering an entry in the mortality table, it should be automatically complemented with two additional columns corresponding to the male and female population of each country. For example, in 2017, there were 4,523,172 males and 5,145,768 females in Portugal. This information is obtained from an external service (e.g., INE) that may vary in each deployment of the application. For testing purposes, it should be possible to use a mock service.
- Uploading a CSV file with mortality data for a given year, which should completely overwrite the mortality table for that year.

A POSTMAN (or similar) collection should also be provided for using the API, which can be executed automatically and includes tests.

The solution should be based on Java, Spring, JPA/Hibernate.

## Part 2: Frontend

A web application is also required where the mortality table for a given year is presented in read-only mode. The year can be changed in a dropdown, and the table should reflect the correct values for that year. There should be an "edit" button in front of each table entry that allows editing that entry. When clicking this button, the entry should switch to edit mode, and two new buttons "confirm" and "cancel" should appear.

You can use third-party libraries to help with the structure and styling of the app, such as patternfly-react, material-ui, etc.