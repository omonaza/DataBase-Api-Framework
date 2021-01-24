package utils.db;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import utils.config.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBaseUtils {
    private static Connection connection;
    private static Statement statement;
    private static QueryRunner queryRunner;
    private static ResultSet resultSet;


    /***
     * CREATE A METHOD THAT OPENS A CONNECTION TO THE DATABASE
     * @throws Exception
     */
    public static void connectToDatabase() throws Exception {

//        TODO: IMPLEMENT HERE...
        try {

            connection = DriverManager.getConnection(Config.getPropertiesValue("url"), Config.getPropertiesValue("user"), "Devx2020$");
            System.out.println("Establishing Database Connection to: " + Config.getPropertiesValue("url"));
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, resultSet.CONCUR_UPDATABLE);
            queryRunner = new QueryRunner();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Connection to database refused: " + Config.getPropertiesValue("url"));
        }
    }

    /***
     * Implement a method that executes the query, and saves/returns the records in List<Map<String, Object>>.
     * @param query
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> executeQuery(String query) throws SQLException {
        //        TODO: IMPLEMENT HERE...


        List<Map<String, Object>> list = new ArrayList<>();
        if (statement.execute(query)) {
            resultSet = statement.executeQuery(query);
            ResultSetMetaData rsMdata = resultSet.getMetaData();

            int colCount = rsMdata.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> rowMap = new HashMap<>();

                for (int col = 1; col <= colCount; col++) {
                    rowMap.put(rsMdata.getColumnName(col), resultSet.getObject(col));
                }
                list.add(rowMap);
            }
        }
        return list;
    }

    /***
     * Implement a method that performs update sql functions.
     * @param query
     * @param params
     * @return
     * @throws SQLException
     */
    public static int executeUpdate(String query, Object... params) throws SQLException {

        //        TODO: IMPLEMENT HERE...

        PreparedStatement stmt = connection.prepareStatement(query);
         for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        return stmt.executeUpdate();

    }

    /***
     * Implement a method that will insert new records as a Bean
     * @param query
     * @param bean
     * @param properties
     * @param <T>
     * @return
     * @throws SQLException
     */
    public static <T> boolean executeInsert(String query, T bean, String[] properties) throws SQLException {

                //        TODO: IMPLEMENT HERE...


        PreparedStatement preparedStatement = connection.prepareStatement(query);
        queryRunner.fillStatementWithBean(preparedStatement, bean, properties);
        return preparedStatement.execute();
    }

    /***
     * Implement a method that reads the records from database and save it to List<Bean> objects.
     * @param object
     * @param query
     * @param <T>
     * @return
     * @throws SQLException
     */
    public static <T> List<T> executeQueryToBean(Class<T> object, String query) throws SQLException {

                //        TODO: IMPLEMENT HERE...


        resultSet = statement.executeQuery(query);
        return new BeanProcessor().toBeanList(resultSet, object);
    }


}
