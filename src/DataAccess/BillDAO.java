package DataAccess;

import Model.Bill;

import java.sql.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import Connection.ConnectionFactory;

/**
 * The  class is responsible for performing database operations related to
 * {@link Model.Bill} objects. It provides methods to insert bills and to retrieve bill data.
 */
public class BillDAO extends AbstractDAO<Bill> {

    private String findAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("id, name, product_name, quantity, total_price");
        sb.append(" FROM ");
        sb.append("Bill");
        return sb.toString();
    }
    private String insertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append("Bill");
        sb.append("( name, product_name, quantity, total_price)");
        sb.append(" VALUES");
        sb.append("(?, ?, ?, ?)");
        return sb.toString();
    }

    /**
     * Inserts a new Bill into the database.
     *
     * @param name        the name associated with the bill
     * @param productName the product's name recorded in the bill
     * @param quantity    the quantity of the product in the bill
     * @param totalPrice  the total price for the bill
     * @return the generated ID of the inserted Bill or -1 if the insertion failed
     */
    public int insertBill(String name, String productName, int quantity, double totalPrice) {
        String query = insertQuery();

        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        int generatedId = -1;

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, productName);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, totalPrice);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }
            else {
                throw new SQLException("Failed to insert bill");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during bill insertion: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.close(generatedKeys);
            ConnectionFactory.close(preparedStatement);
        }

        return generatedId;
    }

    /**
     * Retrieves a tabular representation of Bills from the database.
     *
     * @return a Pair where the key is a 2D Object array containing bill records, and the value is
     * the column names.
     */
    @Override
    public Map.Entry<Object[][], ArrayList<String>> tableHelper() {

        ArrayList<String> columnNames = new ArrayList<>();

        /// predefined data
        columnNames.add("ID");
        columnNames.add("Name");
        columnNames.add("Product Name");
        columnNames.add("Quantity");
        columnNames.add("Total Price");

        Connection connection;
        Statement statement = null;
        ResultSet resultSet = null;

        String query = findAllQuery(); /// select *

        List<Object[]> rows = new ArrayList<>();

        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                Object[] row = new Object[5];

                row[0] = resultSet.getInt("id");
                row[1] = resultSet.getString("name");
                row[2] = resultSet.getString("product_name");
                row[3] = resultSet.getInt("quantity");
                row[4] = resultSet.getBigDecimal("total_price");
                rows.add(row);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Database error in tableHelper: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
        }

        Object[][] result = rows.toArray(new Object[rows.size()][5]);

        return new AbstractMap.SimpleEntry<>(result, columnNames);
    }
}
