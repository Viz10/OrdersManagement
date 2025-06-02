package BusinessLogic;

import BusinessLogic.validators.Validation;
import DataAccess.OrderDAO;
import DataAccess.ProductDAO;
import Model.Orders;
import java.util.*;

/**
 * The OrderBLL class provides the business logic for processing orders.
 * It extends {@link BusinessLogic.validators.Validation} to inherit validation functionality for {@link Model.Orders},
 * and it interacts with {@link DataAccess.OrderDAO} and {@link DataAccess.ProductDAO} for database operations.
 */
public class OrderBLL extends Validation<Orders> {

    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;

    /**
     * Constructs a new OrderBLL instance and initializes the data access objects for orders and products.
     */
    public OrderBLL() {
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
    }

    /**
     * Retrieves a table-form representation of orders.
     *
     * @return a {@link Map.Entry} where the key is a two-dimensional object array representing the bill records,
     *         and the value is an {@link ArrayList} of column names.
     */
    public Map.Entry<Object[][], ArrayList<String>> orderTable() {
        return orderDAO.tableHelper();
    }

    /**
     * Adds an order by processing the requested product quantity and updating the product inventory.
     *
     * @param id_client         the ID of the client placing the order.
     * @param id_product        the ID of the product to be ordered.
     * @param price_product     the price per unit of the product.
     * @param quantity_product  the total quantity available of the product.
     * @param quantity_demanded the quantity of the product requested in this order.
     * @return a {@link Map.Entry} where the key is the remaining product quantity after processing the order,
     *         and the value is an {@link ArrayList} containing order details: order ID, client ID, product ID,
     *         ordered quantity, and the computed total price.
     * @throws InputMismatchException if the available quantity is insufficient or if the insertion fails.
     */
    public Map.Entry<Integer, ArrayList<Object>> addOrder(int id_client, int id_product, double price_product, int quantity_product, int quantity_demanded) {
        int res;
        ArrayList<Object> orderData = new ArrayList<>();

        try {

            if(quantity_demanded <= 0){
                throw new Exception("Quantity demanded should be greater than 0");
            }

            res = quantity_product - quantity_demanded;

            if (res < 0) {
                throw new RuntimeException("Insertion Error: Not enough products left for order!");
            }

            productDAO.updateQuantity(id_product, res);


            Orders order = new Orders(id_client, id_product, quantity_demanded, quantity_product * price_product);
            Orders response = orderDAO.insert(order);
            if (response == null) {
                throw new RuntimeException("Insertion Error: Insertion Failed!");
            }

            orderData.add(response.getId());
            orderData.add(response.getId_client());
            orderData.add(response.getId_product());
            orderData.add(response.getQuantity());
            orderData.add(response.getQuantity() * price_product);

        } catch (Exception e) {
            String errorMsg = (e.getMessage() != null) ? e.getMessage() : "Unknown database error";
            throw new InputMismatchException(errorMsg);
        }
        return new AbstractMap.SimpleEntry<>(res, orderData);
    }
    /**
     * Deletes an order from the database.
     *
     * @param id the unique identifier of the order to delete.
     * @return {@code true} if the order was successfully deleted {@code false} otherwise.
     */
    public boolean deleteOrder(int id) {
        return orderDAO.delete(id);
    }
}
