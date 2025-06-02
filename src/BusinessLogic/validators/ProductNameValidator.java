package BusinessLogic.validators;

import Model.Product;

/**
 * Validates a {@link Model.Product} to ensure that its product name is both present and valid.
 *
 * @see Validator
 */
public class ProductNameValidator implements Validator<Product> {

    /**
     * Validates the product name of the provided {@link Model.Product}.
     *
     * @param t the product to validate.
     * @throws IllegalArgumentException if the product name is null, empty, or contains non-letter characters.
     */
    @Override
    public void validate(Product t) {
        if (t.getProductName() == null || t.getProductName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (!t.getProductName().matches("[a-zA-Z]*")) {
            throw new IllegalArgumentException("Product name must contain only letters");
        }
    }
}
