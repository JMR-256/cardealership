Feature: Adding a car to the database
  Scenario: POST a car using the /cars/admin endpoint
    Given I want to add the following car
    | brand | model | price | year | mileage | colour |
    | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
    | BMW   | X6    | 100000| 2023 | 0       | Magenta    |
    When I POST to the '/cars/admin/' endpoint
    Then the client receives status code of 201
    And the JSON should contain the key 'description' with value 'Database updated'

  Scenario: POST a car using the /car/admin endpoint with malformed attribute
    Given I want to add the following car with JSON '[{brand: "BMW","model": "X5","year": 2022, "price": 80000, "mileage": 10000, "colour": "space grey"}]'
    When I POST to the '/cars/admin' endpoint
    Then the client receives status code of 400
    And the JSON should contain the key 'description' with value 'Incorrect car data provided'

  Scenario: POST a car using the /cars/admin endpoint with malformed field
    Given I want to add the following car with JSON '[{"brand": BMW,"model": "X5","year": 2022,"price": 80000,"mileage": 10000,"colour": "space grey"}]'
    When I POST to the '/cars/admin' endpoint
    Then the client receives status code of 400
    And the JSON should contain the key 'description' with value 'Incorrect car data provided'


  Scenario: POST a car using the /cars/admin endpoint with missing attribute
    Given I want to add the following car
      | [blank]| model | price | year | mileage | colour |
      | BMW   | X5    | 80000 | 2022 | 10000   | Space Grey |
    When I POST to the '/cars/admin' endpoint
    Then the client receives status code of 400
    And the JSON should contain the key 'description' with value 'Incorrect car data provided'


  Scenario: POST a car using the /cars/admin endpoint with missing field
    Given  I want to add the following car
      | brand | model | price | year | mileage | colour |
      |[blank]| X5    | 80000 | 2022 | 10000   | Space Grey |
    When I POST to the '/cars/admin' endpoint
    Then the client receives status code of 400
    And the JSON should contain the key 'description' with value 'Incorrect car data provided'

  Scenario: POST a car using the /cars/admin endpoint with a year length of 3
    Given  I want to add the following car
      | brand | model | price | year | mileage | colour |
      |  BMW  | X5    | 80000 | 202 | 10000   | Space Grey |
    When I POST to the '/cars/admin' endpoint
    Then the client receives status code of 400
    And the JSON should contain the key 'description' with value 'Incorrect car data provided'

  Scenario: POST a car using the /cars/admin endpoint with a year length of 5
    Given  I want to add the following car
      | brand | model | price | year | mileage | colour |
      |  BMW  | X5    | 80000 | 20222| 1000    | Space Grey |
    When I POST to the '/cars/admin' endpoint
    Then the client receives status code of 400
    And the JSON should contain the key 'description' with value 'Incorrect car data provided'


