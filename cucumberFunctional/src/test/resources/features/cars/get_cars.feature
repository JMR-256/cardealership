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


  Scenario: Retrieving all BMWs
    Given The following cars exists in the database
      | brand | model | price | year | mileage | colour |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
      | BMW   | X6    | 100000| 2023 | 3000    | Magenta    |
      | Audi | R8    | 250000| 2022 | 100     | Black      |
    When the client GETs the endpoint 'cars/admin' with query 'brand=BMW'
    Then the client receives status code of 200
    And the client receives response JSON containing
      | brand | model | price | year | mileage | colour |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
      | BMW   | X6    | 100000| 2023 | 3000    | Magenta    |

  Scenario: Retrieving by model
    Given The following cars exists in the database
      | brand | model | price | year | mileage | colour |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
      | BMW   | X6    | 100000| 2023 | 3000    | Magenta    |
      | Audi | R8    | 250000| 2022 | 100     | Black      |
    When the client GETs the endpoint 'cars/admin' with query 'model=x5'
    Then the client receives status code of 200
    And the client receives response JSON containing
      | brand | model | price | year | mileage | colour |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |

  Scenario: Retrieving by price
    Given The following cars exists in the database
      | brand | model | price | year | mileage | colour |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
      | BMW   | X6    | 100000| 2023 | 3000    | Magenta    |
      | Audi | R8    | 250000| 2022 | 100     | Black      |
    When the client GETs the endpoint 'cars/admin' with query 'price=100000'
    Then the client receives status code of 200
    And the client receives response JSON containing
      | brand | model | price | year | mileage | colour |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
      | BMW   | X6    | 100000| 2023 | 3000    | Magenta    |

  Scenario: Retrieving by year
    Given The following cars exists in the database
      | brand | model | price | year | mileage | colour |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
      | BMW   | X6    | 100000| 2023 | 3000    | Magenta    |
      | Audi | R8    | 250000| 2022 | 100     | Black      |
    When the client GETs the endpoint 'cars/admin' with query 'year=2022'
    Then the client receives status code of 200
    And the client receives response JSON containing
      | brand | model | price | year | mileage | colour |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
      | Audi | R8    | 250000| 2022 | 100     | Black      |

  Scenario: Retrieving by mileage
    Given The following cars exists in the database
      | brand | model | price | year | mileage | colour |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
      | BMW   | X6    | 100000| 2023 | 3000    | Magenta    |
      | Audi | R8    | 250000| 2022 | 100     | Black      |
    When the client GETs the endpoint 'cars/admin' with query 'mileage=300'
    Then the client receives status code of 200
    And the client receives response JSON containing
      | brand | model | price | year | mileage | colour |
      | Audi | R8    | 250000| 2022 | 100     | Black      |

  Scenario: Retrieving by colour
    Given The following cars exists in the database
      | brand | model | price | year | mileage | colour |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
      | BMW   | X6    | 100000| 2023 | 3000    | Magenta    |
      | Audi | R8    | 250000| 2022 | 100     | Black      |
    When the client GETs the endpoint 'cars/admin' with query 'colour=Magenta'
    Then the client receives status code of 200
    And the client receives response JSON containing
      | brand | model | price | year | mileage | colour |
      | BMW   | X6    | 100000| 2023 | 3000    | Magenta |


