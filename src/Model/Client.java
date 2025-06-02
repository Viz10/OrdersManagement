package Model;

/**
 * Represents a client with a unique identifier, name, and email address.
 * <p>
 * This class provides constructors for creating client instances with or without an assigned ID.
 * It also includes standard accessors and mutators.
 * </p>
 */
public class Client {

    private int id = 0;
    private String name;
    private String email;

    public Client() {}

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Client(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public int getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + '}';
    }
}
