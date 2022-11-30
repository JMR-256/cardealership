Feature: Retrieving cars from the database

  Scenario: Retrieving all cars sorted by brand
    Given The following cars exists in the database
      | brand | model | price | year | mileage | colour |
      | Ford  | Focus | 26000 | 2022 | 100     | Red    |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
      | BMW   | X6    | 100000| 2023 | 3000    | Magenta    |
      | Audi  | R8    | 250000| 2022 | 100     | Black      |
    When the client GETs the endpoint 'cars/admin'
    Then the client receives status code of 200
    And the client receives response JSON containing
    | brand | model | price | year | mileage | colour |
    | Audi  | R8    | 250000| 2022 | 100     | Black      |
    | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
    | BMW   | X6    | 100000| 2023 | 3000    | Magenta    |
    | Ford  | Focus | 26000 | 2022 | 100     | Red    |


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
      | Audi | R8    | 250000| 2022 | 100     | Black      |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |

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


  Scenario Outline: Sending a request with <issue>
    When the client GETs the endpoint 'cars/admin' with query <query>
    Then the client receives status code of 400
    And the JSON should contain the key 'description' with value 'Incorrect query parameter provided'

    Examples:
    | issue                         | query                              |
    | an invalid query key          | 'invalid=true'                     |
    | a empty query                 | 'brand='                           |
    | an empty number query         | 'price='                           |
    | number field contains chars   | 'mileage=test&year=test&price=test'|
    | field contains special chars  | 'brand=/\fdsa'                     |
    | field contains whitespace     | 'brand=AMG Mercedes'               |
    | year length is less than 4    | 'year=200'                         |
    | year length is greater than 4 | 'year=324432'                      |


