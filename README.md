# DBGroupProject

TODO: 
 1. In DataBaseUtils class implement following methods: 
 - connectToDatabase()
 - executeQuery()
 - executeUpdate()
 - executeInsert()
 - executeQueryToBean()
 2. In BeanHelper implement  following methods: 
 - getBeanPropertyNames()
 3. In beans package implement 3 following Bean classes:
 Override equals(), hashCode() and compareTo() methods so that we can compare to objects with each other based on the instance variable values rather than memory pointers.
 - CartItems
 - Food
 - Orders
 4. Create a feature file and implement it for the following scenarios:
 - Remove all records from cart_item table
 - Remove all records from food table
 - Remove all records from orders table
 - Add new data into the food tables and verify that new data has been inserted
 - Add new data into the cart_items tables and verify that new data has been inserted
 - Add new data into the orders tables and verify that new data has been inserted
  (make sure to insert order_placed_at field between that was placed after "20120-01-09" and "2020-01-18")
 - Update price in food table based on id
 - Update order status in orders table based on id
 - Remove food record in food table based on id
 - Remove orders record in orders table that was placed after "2020-01-12"
 
 Warning: We should refrain from exposing any sensitive passwords in the repo.
 So do not put password in properties file. Use VM arguments for passing password on the fly.
 
 
 VM options: -DdbPassword=yourpassword
 in code you can use System.getProperty("dbPassword");