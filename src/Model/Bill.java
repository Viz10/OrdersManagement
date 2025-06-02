package Model;

/**
 * Represents an immutable Bill with details: unique identifier, the associated name,
 * product name, the quantity of the product, and the total price.
 *
 * @param id the unique identifier of the bill
 * @param name the name associated with the bill
 * @param productName the name of the product in the bill
 * @param quantity the quantity of the product; must be positive
 * @param totalPrice the total price for the bill; must be positive
 */
public record Bill(
        int id,
        String name,
        String productName,
        int quantity,
        double totalPrice
) {

    public Bill {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (totalPrice <= 0) {
            throw new IllegalArgumentException("Total price cannot be negative");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
    }
}
