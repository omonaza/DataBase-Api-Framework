# FOOD DELIVERY API 
## _FOOD_ functionality 

>
>note: 
> Use food_delivery-2.0.1.jar version
>
> java -jar food_delivery-2.0.1.jar
>
**TODO:**

`  1. Write cucumber scenarios and automate it, based on the given requirements below.`

`  2. Automate each REST API calls using restassured library`

`  3. Validate REST API response messages using Hamcrest library`

`  6. Automate validation of Database in food_delivery_db schema`

`  4. For serialization/deserialization use Gson library`

`  5. Set up and Log each acction using Log4j library`

`  6. In RestApiUtils class implement following methods: requestSpecification(), addFood(), listCachedFood(), updateCachedFood(),commitCachedFood().
 `
 
 ` 7. Create and Implement Food and CachedFoodResponse pojo classes that will be used for serialization/deserialization proccesses`


========================

####NOTE: Create a step that resets all cached food. Add this step in the Background or Hooks, so it executes before each scenario.
 API Endpoint: POST REQUEST (with no request body). This API call will reset the foodcache.
```json
  http://localhost:8083/food/resetcache
```

####Functionality 1. 
####TODO: Write test cases(scenarios) and automate them. Validate each endpoint. Check logs for each endpoint as well. 

User should be able to ADD food: 

 API Endpoint: 
```json
  http://localhost:8083/food/cache/add
```
   Fields: 
 - description (not required)
 - imageUrl (required)
 - price(required)
 - name(required)
 - foodType(required)
 -----------
 If User adds food without required fields, user should see the following messages: 
 1. Invalid request - Food image url cannot be null or empty.
 2. Invalid request - Food price cannot be negative nor zero.
 3. Invalid request - Food name cannot be null or empty.
 4. Invalid request - Food type cannot be null or empty.

> Note: 
> Food Type can only be:     
        Beverages,
        Appetizers,
        MainDish.
>
If User adds food with invalid food type then user should see the error message.
 
 Allowed HTTPs request to add food:
  
  **`POST`**
 
##### Scenario 1:
Happy path: User should be able to add food with the fields provided above: 
 Request body:
 ```json
{
	"description":"Wine",
	"imageUrl": "https:foods.com",
	"price": "20.00",
	"name": "Merlot",
	"foodType": "Beverages"
}
```

Example response body: 
```json
{
    "foodCached": [
        {
            "description": "Merlot",
            "imageUrl": "https:foods.com",
            "price": 20.00,
            "name": "Merlot",
            "foodType": "Beverages"
        }
    ]
}
```

##### Scenario 2:
User should not be able to add food if image_url is null or empty.

Example response body: 
```json
{
    "errorMessage": "Invalid request - Food image url cannot be null or empty."
}
```

##### Scenario 3:
User should not be able to add food if food price is null or empty

Example response body: 
```json
{
    "errorMessage": "Invalid request - Food price cannot be negative nor zero."
}
```
##### Scenario 4:
User should not be able to add food if food name is null or empty

Example response body: 
```json
{
    "errorMessage": "Invalid request - Food name cannot be null or empty."
}
```
##### Scenario 5:
User should not be able to add food if food type is null or empty

Example response body: 
```json
{
    "errorMessage": "Invalid request - Food type cannot be null or empty."
}
```
##### Scenario 6:
User should not be able to add food if invalid food type provided:
> Food Type can only be:     
        Beverages,
        Appetizers,
        MainDish.

Example response body: 
```json
{
    "timestamp": "2020-09-10T15:04:57.408+0000",
    "status": 400,
    "error": "Bad Request",
    "message": "JSON parse error: Cannot deserialize value of type `com.devxschool.food_delivery.models.Food$FoodType` from String \"Soups\": not one of the values accepted for Enum class: [MainDish, Beverages, Appetizers]; nested exception is com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `com.devxschool.food_delivery.models.Food$FoodType` from String \"Soups\": not one of the values accepted for Enum class: [MainDish, Beverages, Appetizers]\n at [Source: (PushbackInputStream); line: 6, column: 14] (through reference chain: com.devxschool.food_delivery.models.Food[\"foodType\"])",
    "path": "/food/cache/add"
}
```

-------
####Functionality 2.
####TODO: Write test cases(scenarios) and automate them. Validate each endpoint. Check logs for each endpoint as well. 
 
User should be able to list all added foods.

 API Endpoint: 
```json
  http://localhost:8083/food/cache/list
```
 Allowed HTTPs request to list food:
  **`GET`**
 

##### Scenario 1:
User should be able to list all cached food.

Example response body: NOTE: The response will be based on what type and how much food user have added
```json
{
    "numberOfFoodsInCache": 3,
    "numberOfAppetizers": 1,
    "numberOfMainDishes": 1,
    "numberOfUnknownFood": 1,
    "foodCached": [
        {
            "description": "Merlot",
            "imageUrl": "https:foods.com",
            "price": 20.00,
            "name": "Merlot",
            "foodType": "Beverages"
        },
        {
            "description": "Turkish Baklava",
            "imageUrl": "https:foods.com",
            "price": 10.00,
            "name": "Baklava",
            "foodType": "Appetizers"
        },
        {
            "description": "Smoked Salmon",
            "imageUrl": "https:foods.com",
            "price": 30.00,
            "name": "Smoked Salmon",
            "foodType": "MainDish"
        }
    ]
}
```
------
####Functionality 3. 
####TODO: Write test cases(scenarios) and automate them.  

User should be able to update food based on the provided name and field to update
Note: Price's max limit is $125.00

 API Endpoint: 
```json
http://localhost:8083/food/cache/update?name={food name}&field={fieldName}
```
 Allowed HTTPs request to update food:
  **`PUT`**
 
##### Scenario 1:
User should be able to update food based on the provided name and field to update

Example Request body: 
```json
{
	"description":"Steak",
	"imageUrl": "https:foods.com",
	"price": "100.00",
	"name": "T-Bone steak",
	"foodType": "MainDish"
}
```
Example Response body:

```json
{
    "foodCached": [
        {
            "description": "Steak",
            "imageUrl": "https:foods.com",
            "price": 100.00,
            "name": "T-Bone steak",
            "foodType": "MainDish"
        }
    ]
}
```

##### Scenario 2: 
User should not be able to update food if price is over the limit.
Note: Price's max limit is $125.00

Example response body: 
```json
{
   
   	"description":"Steak",
   	"imageUrl": "https:foods.com",
   	"price": "125.50",
   	"name": "T-Bone steak",
   	"foodType": "MainDish"
   
}
```
Example Request Body:
```json
{
    "errorMessage": "Invalid request - Food price should be kept less than 125"
}
```

------
####Functionality 4.
####TODO: Write test cases(scenarios) and automate them. Validate each enpoint.
 Check logs for each enpoint as well. Validate data in food_delivery Database.
 
User should be able to commit changes so that it saves cache to DB _food_ table.
Some food can be excluded from being saved in DB.
 API Endpoint: 
```json
http://localhost:8083/food/commit
http://localhost:8083/food/commit?exclude={food name}

```
 Allowed HTTPs request to register a user:
  **`POST`**
 
##### Scenario 1:
User should be able to commit changes so that it saves a cache to DB _food_ table.

##### Scenario 2:
User should be able to commit changes with excluded food so that it saves a cache to DB _food_ table.

Example response body: 
```json
{
    "numberOfFoodsSaved": 3,
    "message": "Food Cache is committed to db"
}
```
-------
