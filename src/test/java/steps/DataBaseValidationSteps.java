package steps;

import beans.CartItems;
import beans.FoodDB;
import beans.Orders;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import utils.beanutils.BeanHelper;
import utils.db.DataBaseUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataBaseValidationSteps {

    //TODO: IMPLEMENT HERE

    //this is needed for deleting related table with primary and foreign key.
    private static final String DISABLE_FOREIGNKEY_CHECKS_QUERY = "SET FOREIGN_KEY_CHECKS = 0;";
    private static final String ENABLE_FOREIGNKEY_CHECKS_QUERY = "SET FOREIGN_KEY_CHECKS = 1;";

    private String QUERY;
    private List<Map<String, Object>> databaseResult;

    @Given("^Establish a database connection$")
    public void establish_a_database_connection() throws Throwable {
        DataBaseUtils.connectToDatabase();
    }

    @Given("^remove all records from a \"([^\"]*)\" table$")
    public void removeAllRecordsFromATable(String table) throws Throwable {

        DataBaseUtils.executeQuery(DISABLE_FOREIGNKEY_CHECKS_QUERY);
        QUERY = "TRUNCATE table " + table + ";";
        DataBaseUtils.executeQuery(QUERY);
        DataBaseUtils.executeQuery(ENABLE_FOREIGNKEY_CHECKS_QUERY);
        databaseResult = DataBaseUtils.executeQuery("select * from " + table + ";");
        assertTrue("FAILED: Table is not empty", databaseResult.isEmpty());
    }


    @Given("^Add new data in the cart_items tables$")
    public void addNewDataInTheCart_itemsTables(List<CartItems> cartItems) throws SQLException {

        DataBaseUtils.executeQuery(DISABLE_FOREIGNKEY_CHECKS_QUERY);
        QUERY = "INSERT INTO cart_item VALUES(?, ?, ?, ?);";

        for (CartItems cart_item : cartItems) {
            DataBaseUtils.executeInsert(QUERY, cart_item, BeanHelper.getBeanPropertyNames(CartItems.class));
        }
        DataBaseUtils.executeQuery(ENABLE_FOREIGNKEY_CHECKS_QUERY);
    }

    @Then("^verify that the cart item table has the following data$")
    public void verifyTheCartTable(List<CartItems> items) throws SQLException {
        QUERY = "select * from cart_item order by id;";
        databaseResult = DataBaseUtils.executeQuery(QUERY);

        List<CartItems> cartItemsFromDB = DataBaseUtils.executeQueryToBean(CartItems.class, QUERY);

        List<CartItems> cartItems = new ArrayList<>(items);
        Collections.sort(cartItems);
        Collections.sort(cartItemsFromDB);

        assertEquals("Failed: Mismatch in lists size", cartItems.size(), cartItemsFromDB.size());
        assertEquals("Failed: Mismatch in data", cartItems, cartItemsFromDB);
    }

    @Given("^Add new data in the food tables$")
    public void addNewDataInTheFoodTables(List<FoodDB> foods) throws SQLException {
        QUERY = "INSERT INTO food VALUES(?, ?, ?, ?, ?, ?);";

        for (FoodDB food : foods) {
            DataBaseUtils.executeInsert(QUERY, food, BeanHelper.getBeanPropertyNames(FoodDB.class));
        }
    }

    @Then("^verify that food table has the following food data$")
    public void verifyTheFoodTable(List<FoodDB> foods) throws SQLException {
        QUERY = "select * from food order by id;";
        databaseResult = DataBaseUtils.executeQuery(QUERY);

        List<FoodDB> actual = DataBaseUtils.executeQueryToBean(FoodDB.class, QUERY);

        List<FoodDB> expected = new ArrayList<>(foods);
        Collections.sort(actual);
        Collections.sort(expected);

        assertEquals("Failed: Mismatch in lists size", expected.size(), actual.size());
        assertEquals("Failed: Mismatch in data", expected, actual);
    }

    @Given("^Add new data in the orders tables$")
    public void addNewDataInTheOrdersTables(List<Orders> orders) throws SQLException {
        DataBaseUtils.executeQuery(DISABLE_FOREIGNKEY_CHECKS_QUERY);
        QUERY = "INSERT INTO orders VALUES(?, ?, ?, ?, ?);";

        for (Orders order : orders) {
            DataBaseUtils.executeInsert(QUERY, order, BeanHelper.getBeanPropertyNames(Orders.class));
        }
        DataBaseUtils.executeQuery(ENABLE_FOREIGNKEY_CHECKS_QUERY);
    }

    @Then("^verify that orders table has the following orders$")
    public void verifyThatOrdersTableHasTheFollowingOrders(List<Orders> orders) throws SQLException {
        QUERY = "select * from orders order by id;";
        databaseResult = DataBaseUtils.executeQuery(QUERY);

        List<Orders> actual = DataBaseUtils.executeQueryToBean(Orders.class, QUERY);

        List<Orders> expected = new ArrayList<>(orders);
        Collections.sort(actual);
        Collections.sort(expected);

        assertEquals("Failed: Mismatch in lists size", expected.size(), actual.size());
        assertEquals("Failed: Mismatch in data", expected, actual);
    }

    @Given("^update price to \"([^\"]*)\" in food table for food id \"([^\"]*)\"$")
    public void updatePriceToInFoodTableForFoodId(Double price, int id) throws Throwable {
        QUERY = "UPDATE food SET price = ? WHERE id = ? ;";
        DataBaseUtils.executeUpdate(QUERY, price, id);

        QUERY = "select * from food where id = " + id + ";";

        List<FoodDB> actual = DataBaseUtils.executeQueryToBean(FoodDB.class, QUERY);
        for (FoodDB food : actual) {
            if (food.getId() == id) {
                Assert.assertEquals("Price did not match", price, food.getPrice());
                break;
            }
        }
    }

    @Given("^update order status to \"([^\"]*)\" in food table of order id \"([^\"]*)\"$")
    public void updateOrderStatusToInFoodTableOfOrderId(int status, int id) throws Throwable {
        QUERY = "UPDATE orders SET order_status = ? WHERE id = ? ;";

        DataBaseUtils.executeUpdate(QUERY, status, id);

        QUERY = "select * from orders where id = " + id + ";";

        List<Orders> actual = DataBaseUtils.executeQueryToBean(Orders.class, QUERY);
        for (Orders orders : actual) {
            if (orders.getId() == id) {
                Assert.assertEquals("Order status did not match", status, orders.getOrder_status().intValue());
                break;
            }
        }

    }

    @Given("^remove food record with food id \"([^\"]*)\"$")
    public void removeFoodRecordWithFoodId(int id) throws Throwable {
        DataBaseUtils.executeQuery(DISABLE_FOREIGNKEY_CHECKS_QUERY);
        QUERY = "DELETE FROM food WHERE id=?;";
        DataBaseUtils.executeUpdate(QUERY, id);
        DataBaseUtils.executeQuery(ENABLE_FOREIGNKEY_CHECKS_QUERY);

        QUERY = "select * from food where id = " + id + ";";
        List<FoodDB> actual = DataBaseUtils.executeQueryToBean(FoodDB.class, QUERY);
        assertTrue(actual.isEmpty());
    }

    @Given("^remove order's records that was placed after \"([^\"]*)\"$")
    public void removeOrderSRecordsThatWasPlacedAfter(String time) throws Throwable {
        QUERY = "select * from orders where order_placed_at > '" + time + "';";
        List<Orders> orders = DataBaseUtils.executeQueryToBean(Orders.class, QUERY);
        Assert.assertFalse("Failed: Cannot perform delete operation on empty orders record", orders.isEmpty());
        QUERY = "DELETE FROM orders WHERE id=?;";

        for (Orders order : orders) {
            DataBaseUtils.executeUpdate(QUERY, order.getId());
        }
    }

}
