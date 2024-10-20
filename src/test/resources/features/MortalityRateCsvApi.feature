Feature: Mortality Rates CSV API

  Scenario: Post empty file
    When I send empty file for the year 2016
    Then I get a "INTERNAL_SERVER_ERROR" status response

  Scenario Outline: Post csv mortality rates Bad Request
    When I send "<file>.csv" for the year <year>
    Then I get a "BAD_REQUEST" status response
    And error message is "<message>"
    Examples:
      | year  | file          | message                                    |
      | 20162 | mortality2019 | Year must be exactly 4 digits long         |
      | 2016  | bad1          | Invalid file format                        |
      | 2016  | bad2          | xx is an invalid country code for ISO 3166 |
      | 2016  | bad3          | Rate must have 2 decimal places            |
      | 2016  | bad4          | Rate must be less than 1000                |

  Scenario: Post csv mortality Rate empty year
    When I send "mortality2019.csv" for the year 2019
    Then I get a "CREATED" status response
    And the response is:
      | year | country | rateMasc | rateFem |
      | 2019 | pt      | 200      | 10      |
      | 2019 | us      | 1.23     | 4.56    |
      | 2019 | de      | 3.21     | 4.5     |
      | 2019 | es      | 3.15     | 4.56    |
      | 2019 | fr      | 3.14     | 3.99    |
      | 2019 | it      | 3.15     | 4.34    |

  Scenario: Post csv mortality Rate existing year
    Given I have the registered mortality rates:
      | year | country | rateMasc | rateFem |
      | 2016 | PT      | 4.5      | 3.21    |
      | 2016 | US      | 4.56     | 3.15    |
      | 2020 | US      | 4.56     | 3.15    |
    When I send "mortality2019.csv" for the year 2016
    Then I get a "CREATED" status response
    And the response is:
      | year | country | rateMasc | rateFem |
      | 2016 | pt      | 200      | 10      |
      | 2016 | us      | 1.23     | 4.56    |
      | 2016 | de      | 3.21     | 4.5     |
      | 2016 | es      | 3.15     | 4.56    |
      | 2016 | fr      | 3.14     | 3.99    |
      | 2016 | it      | 3.15     | 4.34    |
    When I request the mortality rates for the year 2020
    And the response is:
      | year | country | rateMasc | rateFem |
      | 2020 | US      | 4.56     | 3.15    |
