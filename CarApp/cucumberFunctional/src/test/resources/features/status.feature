Feature: the status returns OK
  Scenario: client makes call to GET /private/status
    When the client calls /private/status
    Then the client receives status code of 200
    And the client receives a response of OK