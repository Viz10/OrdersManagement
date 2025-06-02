package Model;

/**
 * Represents a product available in the system.
 *
 * <p>The class stores information about a product,
 * including its unique identifier, name, price, and available quantity.
 * It includes constructors to create a product either with or without an assigned ID.
 * </p>
 */
public class Product {

    private int id = 0;
    private String productName;
    private double productPrice;
    private int quantity;

    public Product() {}

    public Product(String productName, double productPrice, int quantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public Product(int id, String productName, double productPrice, int quantity) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }
    public double getProductPrice() {
        return productPrice;
    }
    public int getQuantity() {
        return quantity;
    }
    public int getId() {
        return id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", quantity=" + quantity +
                '}';
    }
}
