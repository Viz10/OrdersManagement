package DataAccess;

import Connection.ConnectionFactory;
import Model.Product;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The class provides database operations for {@link Model.Product} objects.
 * It extends {@link AbstractDAO<Product>} to inherit common data access functionality.
 */
public class ProductDAO extends AbstractDAO<Product> {

    /**
     * Checks whether a product with the same name already exists in the database.
     *
     * @param product check for existence based on its product name
     * @return {@code true} if a product with the same name exists or {@code false} otherwise
     */
    public boolean ProductExists(Product product) {
        return findIdFromSomething("productName", product.getProductName()) != -1;
    }

    private String updateQuantityQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append("product");
        sb.append(" SET ");
        sb.append("quantity = ? ");
        sb.append(" WHERE ");
        sb.append("id = ?");
        return sb.toString();
    }

    /**
     * Updates the quantity of the specified product in the database.
     *
     * @param id           the identifier of the product to update
     * @param new_quantity the new quantity value to set in the database
     */
    public void updateQuantity(int id, int new_quantity) {

        String updateQuery = updateQuantityQuery();

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(updateQuery)) {

            ps.setInt(1, new_quantity);
            ps.setInt(2, id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update failed , no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
