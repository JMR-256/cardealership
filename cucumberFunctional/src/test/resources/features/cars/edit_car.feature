Feature: Editing a car in the database

  Scenario: Editing a single car
    Given The following cars exists in the database
      | brand | model | price | year | mileage | colour |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
    When The client PUTs the endpoint '/cars/admin' with the following data
      | brand | model | price | year | mileage | colour |
      | BMW   | X5    | 10000 | 2022 | 120000   | Space Grey |
    Then the client receives status code of 200
    And the JSON should contain the key 'description' with value 'Car updated'