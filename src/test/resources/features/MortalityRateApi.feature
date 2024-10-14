Feature: Mortality Rates API

  Scenario: Get mortality rates not Found
    When I request the mortality rates for the year 2016
    Then I get a "NOT_FOUND" status response

  Scenario: Get mortality rates Bad Request
    When I request the mortality rates for the year 20162
    Then I get a "BAD_REQUEST" status response
    And error message is "Year must be exactly 4 digits long"

  Scenario Outline: post mortality rates Bad Request
    When I send new mortality rate for the year <year> country "<code>"
      | rateMasc   | rateFem |
      | <rateMasc> | 31.21   |
    Then I get a "BAD_REQUEST" status response
    And error message is "<message>"
    Examples:
      | year  | code | rateMasc | message                                        |
      | 20162 | US   | 42.5     | Year must be exactly 4 digits long             |
      | 2016  | US2  | 42.5     | Country Code must be exactly 2 characters long |
      | 2016  | S    | 42.5     | Country Code must be exactly 2 characters long |
      | 2016  | XX   | 42.5     | XX is an invalid country code for ISO 3166     |
      | 2016  | US   | 42.511   | Rate must have 2 decimal places                |
      | 2016  | US   | 1001     | Rate must be less than 1000                    |

  Rule: Pre-existing data
    Background:
      Given I have the registered mortality rates:
        | year | country | rateMasc | rateFem |
        | 2016 | PT      | 4.5      | 3.21    |
        | 2016 | US      | 4.56     | 3.15    |
        | 2020 | US      | 4.56     | 3.15    |

    Scenario: Get mortality rates by year
      When I request the mortality rates for the year 2016
      Then I get a "OK" status response
      And the response is:
        | year | country | rateMasc | rateFem |
        | 2016 | PT      | 4.5      | 3.21    |
        | 2016 | US      | 4.56     | 3.15    |

    Scenario: When I post new country Rate
      When I send new mortality rate for the year 2016 country "DE"
        | rateMasc | rateFem |
        | 42.5     | 31.21   |
      Then I get a "CREATED" status response
      And the response is:
        | year | country | rateMasc | rateFem |
        | 2016 | PT      | 4.5      | 3.21    |
        | 2016 | US      | 4.56     | 3.15    |
        | 2016 | DE      | 42.5     | 31.21   |

    Scenario: When I post update country Rate
      When I send new mortality rate for the year 2016 country "US"
        | rateMasc | rateFem |
        | 42.5     | 31.21   |
      Then I get a "CREATED" status response
      And the response is:
        | year | country | rateMasc | rateFem |
        | 2016 | PT      | 4.5      | 3.21    |
        | 2016 | US      | 42.5     | 31.21   |