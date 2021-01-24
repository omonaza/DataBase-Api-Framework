@Regression
Feature: Cart Item DB Tests

  @ggg
  Scenario: Add new data into the cart_items tables
    Given remove all records from a "cart_item" table
    When Add new data in the cart_items tables
      | id | quantity | total_price | food_id |
      | 1  | 12       | 29.99       | 1       |
      | 2  | 2        | 219.09      | 1       |
      | 3  | 43       | 100.00      | 2       |
      | 4  | 13       | 76.32       | 1       |
      | 5  | 4        | 34.55       | 3       |
    Then verify that the cart item table has the following data
      | id | quantity | total_price | food_id |
      | 1  | 12       | 29.99       | 1       |
      | 2  | 2        | 219.09      | 1       |
      | 3  | 43       | 100.00      | 2       |
      | 4  | 13       | 76.32       | 1       |
      | 5  | 4        | 34.55       | 3       |











