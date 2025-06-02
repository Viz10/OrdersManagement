package Model;

/**
 * Represents an order placed by a client for a product.
 *
 * <p>The class encapsulates details of a client's order, including a unique identifier,
 * the client's ID, the product's ID, the quantity ordered, and the total price.
 * It provides multiple constructors for creating an instance with or without an initial ID.
 * </p>
 */
public class Orders {

    private int id = 0;
    private int id_client;
    private int id_product;
    private int quantity;
    private double total_price;

    public Orders() {}

    public Orders(int idClient, int idProduct, int quantity, double total_price) {
        this.id_client = idClient;
        this.id_product = idProduct;
        this.quantity = quantity;
        this.total_price = total_price;
    }

    public Orders(int id, int idClient, int idProduct, int quantity, double total_price) {
        this.id = id;
        this.id_client = idClient;
        this.id_product = idProduct;
        this.quantity = quantity;
        this.total_price = total_price;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId_product() {
        return id_product;
    }
    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getId_client() {
        return id_client;
    }
    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public double getTotal_price() {
        return total_price;
    }
    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", id_client=" + id_client +
                ", id_product=" + id_product +
                ", quantity=" + quantity +
                ", total_price=" + total_price +
                '}';
    }
}
