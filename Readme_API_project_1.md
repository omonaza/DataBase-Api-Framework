# FOOD DELIVERY API 
## _Register_ functionality 

**TODO:**
`Test all functionalities based on the requirements below.
 Manually test each requirement in Postman, validate API Responses and records in Database(food_delivery_db1 schema)
`
---

> User Story:
> 
> As a User I want to be able to register to the app, so I can use the food delivery service.

>Functionalities:
>
-------
####Functionality 1. 

User should submit the following fields in order to successfully register to FoodDelivery app:
 - username
 - password
 - fullname
 - email
 - address
 - city
 - state
 - zip
 - phone
 
 Once user has been successfully registered, all submitted information should be saved in food_delivery_db schema in DB and 
 following tables: custom_user, user_profile. Write a query to fetch username,password,fullname,address,city,state,zip,phone from custom_user and user_profile tables.
 
 -----------
 
 See following example to test manually:
 
 Allowed HTTPs request to register a user:
  
  **`POST`**
 
 API Endpoint: 
 ```json
  http://3.131.35.165:8082/user/registration
 ```
 Request body:
 ```json
{
	"username":"John",
	"password": "Test123",
	"fullName": "John Doe",
	"address": "123 main st",
	"city": "Chicago",
	"state": "IL",
	"zip": "60625",
	"phone": "112131321"
}
```
##### Scenario 1:
```gherkin
Given user registers to food delivery app with the following fields:
|username|password| fullName|  address  |  city |state| zip |phone    |
|John    |Test123 |John Doe |123 main st|Chicago| IL  |60625|112131321|
Then verify that status code is 200
Then verify that response message is "User registration successful."
Then verify that user information successfully saved in DB
```
Example response body: 
```json
{
    "status": "User registration successful",
    "errorMessage": null,
    "userInfo": {
        "password": "$2a$10$fnkQLejKQiJrXtioCXAD/OffRvLb6EHPyQ1Yfgo5dUtfFVvf6h9HG",
        "username": "John",
        "accountNonExpired": true,
        "accountNonLocked": true,
        "credentialsNonExpired": true,
        "enabled": true,
        "authorities": [
            {
                "authority": "USER"
            }
        ],
        "userProfile": {
            "id": 49,
            "email": null,
            "fullName": "John Doe",
            "address": "123 main st",
            "city": "Chicago",
            "state": "IL",
            "zip": "60625",
            "phone": "112131321"
        }
    }
}
```
-------
####Functionality 2. 

User tries to register with an existing username.
And user should get corresponding error message "Username unavailable. Please choose another one.".
And User data should not be stored in DB

##### Scenario 1:
```gherkin
Given user registers to food delivery app with an existing username:
Then verify that status code is 400
Then verify that response message is "Username unavailable. Please choose another one."
Then verify that user information successfully saved in DB
```
Example response body: 
```json
{
    "status": "Bad Request Body",
    "errorMessage": "Username unavailable. Please choose another one",
    "userInfo": null
}
```
------
####Functionality 3. 
If user tries to register with empty or null username.
Then following error message should occur: "Username cannot be null or empty."
And User data should not be stored in DB

##### Scenario 1:
```gherkin
Given user registers to food delivery app with empty username
Then verify that status code is 400
Then verify that response message is "Username cannot be null or empty."
Then verify that user information is not saved in DB
```

##### Scenario 2:
```gherkin
Given user registers to food delivery app with null username
Then verify that status code is 400
Then verify that response message is "Username cannot be null or empty."
Then verify that user information is not saved in DB
```

Example response body: 
```json
{
    "status": "Bad Request Body",
    "errorMessage": "Username cannot be null or empty",
    "userInfo": null
}
```
------
####Functionality 4. 
If user tries to register with empty or null fullname.
Then following error message should occur: "Fullname cannot be null or empty."
And User data should not be stored in DB

##### Scenario 1:
```gherkin
Given user registers to food delivery app with empty fullname
Then verify that status code is 400
Then verify that response message is "Fullname cannot be null or empty."
Then verify that user information is not saved in DB
```

##### Scenario 2:
```gherkin
Given user registers to food delivery app with null fullname
Then verify that status code is 400
Then verify that response message is "Fullname cannot be null or empty."
Then verify that user information is not saved in DB
```

Example response body: 
```json
{
    "status": "Bad Request Body",
    "errorMessage": "Fullname cannot be null or empty",
    "userInfo": null
}
```
-------

####Functionality 5. 

If user tries to register with empty or null password.
Then following error message should occur: "Password cannot be null or empty".
And User data should not be stored in DB

##### Scenario 1:
```gherkin
Given user registers to food delivery app without password
Then verify that status code is 500
Then verify that response message is "Password cannot be null or empty."
Then verify that user information is not saved in DB

```
##### Scenario 2:
```gherkin
Given user registers to food delivery app empty password
Then verify that status code is 500
Then verify that response message is "Password cannot be null or empty."
Then verify that user information is not saved in DB

```
Example response body: 
```json
{
    "timestamp": "2020-09-03T18:51:40.371+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "rawPassword cannot be null",
    "path": "/user/registration"
}
```
