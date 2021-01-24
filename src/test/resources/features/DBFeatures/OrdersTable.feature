@Regression
Feature: Orders tests

@g
  Scenario: Add new data into the orders tables
    Given remove all records from a "orders" table
    When Add new data in the orders tables
      | id | order_placed_at     | order_status | order_updated_at    | custom_user_id |
      | 1  | 2020-01-12 12:20:32 | 2            | 2020-01-12 12:40:01 | 1              |
      | 2  | 2020-01-12 10:00:01 | 1            | 2020-01-12 11:00:00 | 2              |
      | 3  | 2020-01-12 09:30:21 | 2            | 2020-01-12 10:22:00 | 1              |
    Then verify that orders table has the following orders
      | id | order_placed_at     | order_status | order_updated_at    | custom_user_id |
      | 1  | 2020-01-12 12:20:32 | 2            | 2020-01-12 12:40:01 | 1              |
      | 2  | 2020-01-12 10:00:01 | 1            | 2020-01-12 11:00:00 | 2              |
      | 3  | 2020-01-12 09:30:21 | 2            | 2020-01-12 10:22:00 | 1              |


  Scenario: Update order status in orders table
    Given remove all records from a "orders" table
    And Add new data in the orders tables
      | id | order_placed_at     | order_status | order_updated_at    | custom_user_id |
      | 1  | 2020-01-12 12:20:32 | 2            | 2020-01-12 12:40:01 | 1              |
      | 2  | 2020-01-12 10:00:01 | 1            | 2020-01-12 11:00:00 | 2              |
      | 3  | 2020-01-12 09:30:21 | 2            | 2020-01-12 10:22:00 | 1              |
    When update order status to "1" in food table of order id "1"
    Then verify that orders table has the following orders
      | id | order_placed_at     | order_status | order_updated_at    | custom_user_id |
      | 1  | 2020-01-12 12:20:32 | 1            | 2020-01-12 12:40:01 | 1              |
      | 2  | 2020-01-12 10:00:01 | 1            | 2020-01-12 11:00:00 | 2              |
      | 3  | 2020-01-12 09:30:21 | 2            | 2020-01-12 10:22:00 | 1              |


@h
  Scenario: Remove orders record in orders table
    Given remove all records from a "orders" table
    And Add new data in the orders tables
      | id | order_placed_at     | order_status | order_updated_at    | custom_user_id |
      | 1  | 2020-01-01 12:20:32  | 2            | 2020-01-12 12:40:01 | 1              |
      | 2  | 2020-06-12 10:00:01 | 1            | 2020-01-12 11:00:00 | 2              |
      | 3  | 2020-02-12 09:30:21 | 2            | 2020-01-12 10:22:00 | 1              |
    And verify that orders table has the following orders
      | id | order_placed_at     | order_status | order_updated_at    | custom_user_id |
      | 1  | 2020-01-01 12:20:32  | 2            | 2020-01-12 12:40:01 | 1              |
      | 2  | 2020-06-12 10:00:01 | 1            | 2020-01-12 11:00:00 | 2              |
      | 3  | 2020-02-12 09:30:21 | 2            | 2020-01-12 10:22:00 | 1              |
    When remove order's records that was placed after "2020-05-01"
    Then verify that n table has the following orders
      | id | order_placed_at     | order_status | order_updated_at    | custom_user_id |
      | 1  | 2020-01-01 12:20:32  | 2            | 2020-01-12 12:40:01 | 1              |
      | 3  | 2020-02-12 09:30:21 | 2            | 2020-01-12 10:22:00 | 1              |