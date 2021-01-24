package steps;

import beans.CachedFoodResponse;
import beans.Food;
import beans.FoodDelivery;
import com.google.gson.Gson;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import utils.db.DataBaseUtils;
import utils.restapi.RestAPIUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FoodDeliveryAPIsteps {
    private Gson gson = new Gson();
    private String jsonBody;
    private Response response;
    private List<FoodDelivery> expectedFoodDelivery;
    private List<Food> expectedFood;
    private String QUERY;
    private List<Map<String, Object>> databaseResult;


    @Given("^user registers to food delivery app with(?: the following fields| empty username| an existing username| null username| empty fullname|out password| null fullname| null password):$")
    public void user_registers_to_food_delivery_app_with_the_following_fields(List<FoodDelivery> foods) throws Throwable {
        for (FoodDelivery food : foods) {
            if (food.getUsername().equals("null")) {
                food.setUsername(null);
            } else if (food.getFullName().equals("null")) {
                food.setFullName(null);
            } else if (food.getPassword().equals("null")) {
                food.setPassword(null);
            }
            jsonBody = gson.toJson(food);
            response = RestAPIUtils.requestSpecification()
                    .body(jsonBody)
                    .post("user/registration");
        }
        expectedFoodDelivery = foods;
    }

    @Then("^verify that status code is (\\d+)$")
    public void verify_that_status_code_is(int statusCode) throws Throwable {
        response.then().assertThat().statusCode(statusCode);
    }

    @Then("^verify that response message is \"([^\"]*)\" in \"([^\"]*)\"$")
    public void verify_that_response_message_is_in(String message, String field) throws Throwable {
        response.then().assertThat().body(field, Matchers.equalTo(message));
    }


    @Then("^verify that user information successfully saved in DB$")
    public void verify_that_user_information_successfully_saved_in_DB() throws Throwable {
        for (int i = 0; i < expectedFoodDelivery.size(); i++) {
            QUERY = "select b.username, b.password, a.full_name as fullName, a.address, a.city, a.state, a.zip, a.phone from " +
                    "food_delivery_db.user_profile a join food_delivery_db.custom_user b on b.user_profile_id = a.id where b.username = '" + expectedFoodDelivery.get(i).getUsername() + "';";
            databaseResult = DataBaseUtils.executeQuery(QUERY);

            List<FoodDelivery> actual = DataBaseUtils.executeQueryToBean(FoodDelivery.class, QUERY);

            actual.get(i).setPassword(expectedFoodDelivery.get(i).getPassword());

            assertEquals("Failed: Mismatch in lists size", expectedFoodDelivery.size(), actual.size());
            assertEquals("Failed: Mismatch in data", expectedFoodDelivery.get(i), actual.get(i));
        }
    }

    @Then("^remove record with username from food_delivery_db table$")
    public void remove_record_with_username_from_food_delivery_db_table(List<String> usernames) throws Throwable {
        for (int i = 0; i < usernames.size(); i++) {
            QUERY = "DELETE FROM food_delivery_db.custom_user WHERE username ='" + usernames.get(i) + "';";
            DataBaseUtils.executeQuery(QUERY);
            QUERY = "select b.username, b.password, a.full_name as fullName, a.address, a.city, a.state, a.zip, a.phone " +
                    "from food_delivery_db.user_profile a join food_delivery_db.custom_user b\n" +
                    "on b.user_profile_id = a.id where b.username = '" + usernames.get(i) + "';";
            databaseResult = DataBaseUtils.executeQuery(QUERY);

            List<FoodDelivery> actual = DataBaseUtils.executeQueryToBean(FoodDelivery.class, QUERY);
            Assert.assertTrue(actual.isEmpty());
        }
    }

    @Then("^verify that user information is not saved in DB$")
    public void verifyThatUserInformationIsNotSavedInDB() throws SQLException {
        for (int i = 0; i < expectedFoodDelivery.size(); i++) {
            if (null == expectedFoodDelivery.get(i).getUsername()) {
                QUERY = "select b.username, b.password, a.full_name as fullName, a.address, a.city, a.state, a.zip, a.phone from " +
                        "food_delivery_db.user_profile a join food_delivery_db.custom_user b on b.user_profile_id = " +
                        "a.id where a.full_name = '" + expectedFoodDelivery.get(i).getFullName() + "' and " +
                        "a.address = '" + expectedFoodDelivery.get(i).getAddress() + "' and " +
                        "a.phone = '" + expectedFoodDelivery.get(i).getPhone() + "';";
            } else {
                QUERY = "select b.username, b.password, a.full_name as fullName, a.address, a.city, a.state, a.zip, a.phone from " +
                        "food_delivery_db.user_profile a join food_delivery_db.custom_user b on b.user_profile_id = " +
                        "a.id where b.username = '" + expectedFoodDelivery.get(i).getUsername() + "';";
            }
            databaseResult = DataBaseUtils.executeQuery(QUERY);
            Assert.assertTrue(databaseResult.isEmpty());
        }
    }

    @Given("^add new food to FoodDelivery (?:with )?(?:the following fields|without image url|without (?:price|name|food type)|invalid food type)$")
    public void add_new_food_to_FoodDelivery_with_the_following_fields(List<Food> foods) throws Throwable {
        jsonBody = gson.toJson(foods.get(0));
        response = RestAPIUtils.requestSpecification()
                .body(jsonBody)
                .post("food/cache/add");
        expectedFood = foods;

        Hooks.scenarioInfo.write("My data from test");
    }

    @Then("^verify that food has been successfully added$")
    public void verify_that_food_has_been_successfully_added() throws Throwable {
        List<Food> cachedFood = gson.fromJson(response.asString(), CachedFoodResponse.class).getFoodCached();
        Assert.assertEquals("Food do not match", expectedFood.get(0), cachedFood.get(cachedFood.size()-1));
    }

    @Given("^user list all food in cache$")
    public void user_list_all_food_in_cache() throws Throwable {
        response = RestAPIUtils.requestSpecification()
                .get("food/cache/list");
    }

    @Then("^verify that response contains all cached foods$")
    public void verify_that_response_contains_all_cached_foods() throws Throwable {
        List<Food> cachedFood = gson.fromJson(response.asString(), CachedFoodResponse.class).getFoodCached();
        response.then().assertThat().body("numberOfFoodsInCache", Matchers.equalTo(cachedFood.size()));
    }

    @Given("^reset cached food in Food Delivery API$")
    public void resetCachedFoodInFoodDeliveryAPI() {
        response = RestAPIUtils.requestSpecification()
                .and()
                .post("food/resetcache");
    }

    @Given("^user updates \"([^\"]*)\"'s price to \"([^\"]*)\"$")
    public void userUpdatesSPriceTo(String name, double priceValue) throws Throwable {
        expectedFood.get(0).setPrice(priceValue);
        jsonBody = gson.toJson(expectedFood.get(0));
        response = RestAPIUtils.requestSpecification()
                .and()
                .body(jsonBody)
                .queryParam("name",name)
                .queryParam("field","price")
                .put("food/cache/update");
    }

    @Then("^verify that price have been updated$")
    public void verifyThatPriceHaveBeenUpdated() {
        List<Food> updatedFood = gson.fromJson(response.asString(), CachedFoodResponse.class).getFoodCached();
        Assert.assertEquals("Food do not match", expectedFood, updatedFood);
    }

}
