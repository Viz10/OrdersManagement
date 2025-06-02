package BusinessLogic.validators;

import Model.Product;

/**
 * Validates a {@link Model.Product} to ensure that its price and quantity are not negative.
 *
 * @see Validator
 */
public class ProductPriceQuantityVal implements Validator<Product> {

    /**
     * Validates the provided {@link Model.Product}.
     *
     * @param product the product to validate.
     * @throws IllegalArgumentException if the product's price or quantity is negative.
     */
    @Override
    public void validate(Product product) {
        if (product.getProductPrice() < 0 || product.getQuantity() < 0) {
            throw new IllegalArgumentException("Cannot insert negative numbers");
        }
    }
}
