@Regression
Feature: Food DB Tests

  Scenario: Add new data into the food tables
    Given remove all records from a "food" table
    When Add new data in the food tables
      | id | description | food_type | image_url                               | name        | price |
      | 1  | appetizers  | 2         | https://www.fooddelivery.com/appetizers | hummus      | 12.99 |
      | 2  | salads      | 3         | https://www.fooddelivery.com/salads     | greek salad | 21.00 |
      | 3  | soups       | 5         | https://www.fooddelivery.com/soups      | tomato soup | 5.50  |
    Then verify that food table has the following food data
      | id | description | food_type | image_url                               | name        | price |
      | 1  | appetizers  | 2         | https://www.fooddelivery.com/appetizers | hummus      | 12.99 |
      | 2  | salads      | 3         | https://www.fooddelivery.com/salads     | greek salad | 21.00 |
      | 3  | soups       | 5         | https://www.fooddelivery.com/soups      | tomato soup | 5.50  |

@db_gh
  Scenario: Update price in food table
    Given remove all records from a "food" table
    And Add new data in the food tables
      | id | description | food_type | image_url                               | name        | price |
      | 1  | appetizers  | 2         | https://www.fooddelivery.com/appetizers | hummus      | 12.99 |
      | 2  | salads      | 3         | https://www.fooddelivery.com/salads     | greek salad | 21.00 |
      | 3  | soups       | 5         | https://www.fooddelivery.com/soups      | tomato soup | 5.50  |
    And verify that food table has the following food data
      | id | description | food_type | image_url                               | name        | price |
      | 1  | appetizers  | 2         | https://www.fooddelivery.com/appetizers | hummus      | 12.99 |
      | 2  | salads      | 3         | https://www.fooddelivery.com/salads     | greek salad | 21.00 |
      | 3  | soups       | 5         | https://www.fooddelivery.com/soups      | tomato soup | 5.50  |
    When update price to "28.98" in food table for food id "1"
    Then verify that food table has the following food data
      | id | description | food_type | image_url                               | name        | price |
      | 1  | appetizers  | 2         | https://www.fooddelivery.com/appetizers | hummus      | 28.98 |
      | 2  | salads      | 3         | https://www.fooddelivery.com/salads     | greek salad | 21.00 |
      | 3  | soups       | 5         | https://www.fooddelivery.com/soups      | tomato soup | 5.50  |


  Scenario: Remove food record in food table
    Given remove all records from a "food" table
    And Add new data in the food tables
      | id | description | food_type | image_url                               | name        | price |
      | 1  | appetizers  | 2         | https://www.fooddelivery.com/appetizers | hummus      | 12.99 |
      | 2  | salads      | 3         | https://www.fooddelivery.com/salads     | greek salad | 21.00 |
      | 3  | soups       | 5         | https://www.fooddelivery.com/soups      | tomato soup | 5.50  |
    And verify that food table has the following food data
      | id | description | food_type | image_url                               | name        | price |
      | 1  | appetizers  | 2         | https://www.fooddelivery.com/appetizers | hummus      | 12.99 |
      | 2  | salads      | 3         | https://www.fooddelivery.com/salads     | greek salad | 21.00 |
      | 3  | soups       | 5         | https://www.fooddelivery.com/soups      | tomato soup | 5.50  |
    When remove food record with food id "2"
    Then verify that food table has the following food data
      | id | description | food_type | image_url                               | name        | price |
      | 1  | appetizers  | 2         | https://www.fooddelivery.com/appetizers | hummus      | 12.99 |
      | 3  | soups       | 5         | https://www.fooddelivery.com/soups      | tomato soup | 5.50  |