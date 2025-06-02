package BusinessLogic;

import BusinessLogic.validators.ProductNameValidator;
import BusinessLogic.validators.ProductPriceQuantityVal;
import BusinessLogic.validators.Validation;
import BusinessLogic.validators.Validator;
import DataAccess.ProductDAO;
import Model.Product;

import java.util.*;

/**
 * The ProductBLL class manages the business logic related to products.
 *
 * <p>This class extends {@link BusinessLogic.validators.Validation} to use generic validation functionality for
 * {@link Model.Product} objects.</p>
 */
public class ProductBLL extends Validation<Product> {

    private final List<Validator<Product>> validators;
    private final ProductDAO productDAO;

    /**
     * Constructs a new instance of ProductBLL.
     *
     * <p>This constructor initializes the list of validators for {@link Model.Product} objects and sets up the</p>
     */
    public ProductBLL() {
        validators = new ArrayList<>();
        validators.add(new ProductNameValidator());
        validators.add(new ProductPriceQuantityVal());

        productDAO = new ProductDAO();
    }

    /**
     * Retrieves a tabular representation of the products' data.
     *
     * @return a {@link Map.Entry} where the key is a two-dimensional Object array containing product records,
     *         and the value is an {@link ArrayList} of column names.
     */
    public Map.Entry<Object[][], ArrayList<String>> productTable() {
        return productDAO.tableHelper();
    }

    /**
     * Deletes a product from the database.
     *
     * @param id the unique identifier of the product to delete.
     * @return {@code true} if the deletion was successful {@code false} otherwise.
     */
    public boolean deleteProduct(int id) {
        return productDAO.delete(id);
    }

    /**
     * Adds a new product to the system.
     *
     * @param fields an array of {@link String} values representing the product details
     * @return an {@link ArrayList} of objects representing the properties of the inserted product to be inserted in the GUI table.
     * @throws InputMismatchException if validation fails or a database error occurs.
     */
    public ArrayList<Object> addProduct(String[] fields) {
        ArrayList<Object> values = new ArrayList<>();
        Product product;
        try {
            validateDouble(fields[1]);
            validateInteger(fields[2]);
            Product p = new Product(fields[0], Double.parseDouble(fields[1]), Integer.parseInt(fields[2]));
            isValid(validators, p); /// use polymorphism for validation
            if (productDAO.ProductExists(p)) {
                throw new RuntimeException("Insertion Error: Product already exists!");
            }
            product = productDAO.insert(p);
            if (product == null) {
                throw new RuntimeException("Insertion Error: Could not add product.");
            }
        } catch (Exception e) {
            String errorMsg = (e.getMessage() != null) ? e.getMessage() : "Unknown database error";
            throw new InputMismatchException(errorMsg);
        }

        productDAO.retrieveProperties(product, values, new ArrayList<>());
        return values;
    }

    /**
     * Updates the details of an existing product.
     *
     * @param id  the unique identifier of the product to update.
     * @param fields an array of {@link String} values representing the updated product details
     * @throws InputMismatchException if validation fails or if the update operation encounters an error.
     */
    public void updateProduct(int id, String[] fields) {
        try {

            validateDouble(fields[1]);
            validateInteger(fields[2]);
            Product p = new Product(id, fields[0], Double.parseDouble(fields[1]), Integer.parseInt(fields[2]));
            isValid(validators, p);

            Product current = productDAO.findById(id);
            if (!Objects.equals(current.getProductName(), fields[0]) && productDAO.ProductExists(p)) {
                throw new RuntimeException("Cannot update product name. Other product already exists!");
            }
            Product check = productDAO.update(p);
            if (check == null) {
                throw new RuntimeException("Update Error");
            }
        } catch (Exception e) {
            String errorMsg = (e.getMessage() != null) ? e.getMessage() : "Unknown database error";
            throw new InputMismatchException(errorMsg);
        }
    }
}
