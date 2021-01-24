@regression
Feature: As a User I want to be able to register to the app, so I can use the food delivery service.

  @sc1 @smoke
  Scenario: User should submit the following fields in order to successfully register to FoodDelivery app

    Given user registers to food delivery app with the following fields:
      | username | password | fullName | address     | city    | state | zip   | phone     |
      | John334  | Test123  | John Doe | 123 main st | Chicago | IL    | 60625 | 112131321 |
    Then verify that status code is 200
    Then verify that response message is "User registration successful" in "status"
    Then verify that user information successfully saved in DB

  @sc2
  Scenario: registration with existing username

    Given user registers to food delivery app with an existing username:
      | username | password | fullName | address     | city    | state | zip   | phone     |
      | John334  | Test123  | John Doe | 123 main st | Chicago | IL    | 60625 | 112131321 |
    Then verify that status code is 400
    Then verify that response message is "Username unavailable. Please choose another one" in "errorMessage"
    Then verify that user information successfully saved in DB
    Then remove record with username from food_delivery_db table
      | John334 |

  @sc3
  Scenario: registration with empty username
    Given user registers to food delivery app with empty username:
      | username | password | fullName  | address      | city    | state | zip   | phone     |
      |          | Test123  | John Doe1 | 1123 main st | Chicago | IL    | 60625 | 112131322 |
    Then verify that status code is 400
    Then verify that response message is "Username cannot be null or empty" in "errorMessage"
    Then verify that user information is not saved in DB

  @sc4
  Scenario: registration with null username
    Given user registers to food delivery app with null username:
      | username | password | fullName  | address     | city    | state | zip   | phone     |
      | null     | Test123  | John Doe2 | 123 main st | Chicago | IL    | 60625 | 112131323 |
    Then verify that status code is 400
    Then verify that response message is "Username cannot be null or empty" in "errorMessage"
    Then verify that user information is not saved in DB

  @sc5
  Scenario: registration with empty fullname
    Given user registers to food delivery app with empty fullname:
      | username | password | fullName | address     | city    | state | zip   | phone     |
      | John06   | Test123  |          | 123 main st | Chicago | IL    | 60625 | 112131324 |
    Then verify that status code is 400
    Then verify that response message is "Fullname cannot be null or empty" in "errorMessage"
    Then verify that user information is not saved in DB

  Scenario: registration with null fullname
    Given user registers to food delivery app with null fullname:
      | username | password | fullName | address     | city    | state | zip   | phone     |
      | John07   | Test123  | null     | 123 main st | Chicago | IL    | 60625 | 112131325 |
    Then verify that status code is 400
    Then verify that response message is "Fullname cannot be null or empty" in "errorMessage"
    Then verify that user information is not saved in DB

  Scenario: registration with empty password
    Given user registers to food delivery app without password:
      | username | password | fullName | address     | city    | state | zip   | phone     |
      | John08   |          | John Doe | 123 main st | Chicago | IL    | 60625 | 112131326 |
    Then verify that status code is 500
    Then verify that response message is "Password cannot be null or empty" in "message"
    Then verify that user information is not saved in DB

  @sc8
  Scenario: registration with null password
    Given user registers to food delivery app with null password:
      | username      | password | fullName | address     | city    | state | zip   | phone     |
      | John09sddsdc1 | null     | John Doe | 123 main st | Chicago | IL    | 60625 | 112131327 |
    Then verify that status code is 500
    Then verify that response message is "rawPassword cannot be null" in "message"
    Then verify that user information is not saved in DB
