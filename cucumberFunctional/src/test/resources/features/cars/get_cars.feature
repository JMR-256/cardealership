Feature: Retrieving cars from the database

  Scenario: Retrieving all cars
    Given The following cars exists in the database
      | brand | model | price | year | mileage | colour |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
      | BMW   | X6    | 100000| 2023 | 3000    | Magenta    |
    When the client GETs the endpoint 'cars/admin'
    Then the client receives status code of 200
    And the client receives response JSON containing
    | brand | model | price | year | mileage | colour |
    | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
    | BMW   | X6    | 100000| 2023 | 3000    | Magenta    |