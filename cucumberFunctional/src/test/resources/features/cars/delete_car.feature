Feature: Delete a car

  Scenario:
    Given The following cars exists in the database
      | brand | model | price | year | mileage | colour     |
      | BMW   | X5    | 80000 | 2021 | 10000   | Space Grey |
    When The client performs a DELETE request to the endpoint 'cars/admin/BMW/X5'
    Then the client receives status code of 204
    Then a GET request to 'cars/admin' with query 'brand=bmw&model=x5'
    And the client receives response JSON containing
      | brand | model | price | year | mileage | colour |
