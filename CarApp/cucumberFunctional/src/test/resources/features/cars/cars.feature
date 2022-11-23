Feature: Adding a car to the database
  Scenario: POST a car using the /cars/admin endpoint
    Given I want to add the following car
    | brand | model | price | year | mileage | colour
    | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey
    | BMW   | X6    | 100000| 2023 | 0       | Magenta
    When I POST to the '/cars/admin/' endpoint
    Then the client receives status code of 201
    And the JSON should contain the key 'description' with value 'Database updated'