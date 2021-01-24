Feature: Food Delivery API part 2

  @scen1
  Scenario: add new food to FoodDelivery
    Given add new food to FoodDelivery with the following fields
      | description | imageUrl        | price | name   | foodType  |
      | Wine1       | https:foods.com | 20.00 | Merlot | Beverages |
    Then verify that status code is 200
    Then verify that food has been successfully added

  @scen2
  Scenario: add new food to FoodDelivery without image url
    Given add new food to FoodDelivery without image url
      | description | price | name   | foodType  |
      | Wine2       | 20.00 | Merlot | Beverages |
    Then verify that status code is 403
    Then verify that response message is "Invalid request - Food image url cannot be null or empty." in "errorMessage"

  @scen3
  Scenario: add new food to FoodDelivery without price
    Given add new food to FoodDelivery without price
      | description | imageUrl        | name   | foodType  |
      | Wine3       | https:foods.com | Merlot | Beverages |
    Then verify that status code is 403
    Then verify that response message is "Invalid request - Food price cannot be negative nor zero." in "errorMessage"

  @scen4
  Scenario: add new food to FoodDelivery without name
    Given add new food to FoodDelivery without name
      | description | imageUrl        | price | foodType  |
      | Wine        | https:foods.com | 20.00 | Beverages |
    Then verify that status code is 403
    Then verify that response message is "Invalid request - Food name cannot be null or empty." in "errorMessage"

  @scen5
  Scenario:add new food to FoodDelivery without food type
    Given add new food to FoodDelivery without food type
      | description | imageUrl        | name   | price |
      | Wine        | https:foods.com | Merlot | 20.00 |
    Then verify that status code is 403
    Then verify that response message is "Invalid request - Food type cannot be null or empty." in "errorMessage"

  @scen6
  Scenario:add new food to FoodDelivery with invalid food type
    Given add new food to FoodDelivery with invalid food type
      | description | imageUrl        | name   | price | foodType |
      | Wine        | https:foods.com | Merlot | 20.00 | Soups    |
    Then verify that status code is 400
    Then verify that response message is "Bad Request" in "error"

  @scen7
  Scenario:user list all food in cache
    Given user list all food in cache
    Then verify that status code is 200
    Then verify that response contains all cached foods

  @scen8
  Scenario: Update cached food
    Given reset cached food in Food Delivery API
    And add new food to FoodDelivery with the following fields
      | description | imageUrl        | price | name         | foodType |
      | Steak       | https:foods.com | 50.00 | T-Bone steak | MainDish |
    Then verify that status code is 200
    Then verify that food has been successfully added
    Given user updates "T-Bone steak"'s price to "100.00"
    Then verify that status code is 200
    Then verify that price have been updated

  @api_scen9
  Scenario: Update price over the limit $125
    Given reset cached food in Food Delivery API
    And add new food to FoodDelivery with the following fields
      | description | imageUrl        | price | name         | foodType |
      | Steak       | https:foods.com | 50.00 | T-Bone steak | MainDish |
    Then verify that status code is 200
    Then verify that food has been successfully added
    Given user updates "T-Bone steak"'s price to "125.50"
    Then verify that status code is 403
    Then verify that response message is "Invalid request - Food price should be kept less than 125" in "errorMessage"


  Scenario: Save all cached food
    Given reset cached food in Food Delivery API
    And add new food to FoodDelivery with the following fields
      | description | imageUrl        | price | name  | foodType |
      | Noodles     | https:foods.com | 10.00 | Ramen | MainDish |
    Then verify that status code is 200
    Then verify that food has been successfully added
    Given user saves all cached food
    Then verify that status code is 200
    Then verify number of saved food
    Then verify response message "Food Cache is committed to db"
    Then verify that all food information is saved in DB

  Scenario: Save cached food excluding one food
    Given reset cached food in Food Delivery API
    And add new food to FoodDelivery with the following fields
      | description | imageUrl        | price | name  | foodType |
      | Noodles     | https:foods.com | 10.00 | Ramen | MainDish |
    Then verify that status code is 200
    Then verify that food has been successfully added
    Given user saves all cached food excluding "Diet Coke"
    Then verify that status code is 200
    Then verify response message "Food Cache is committed to db"
    Then verify that all food information is saved in DB