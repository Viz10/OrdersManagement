package DataAccess;

import Model.Client;

/**
 * The class handles database operations for {@link Model.Client} objects.
 * It extends {@link AbstractDAO<Client>} to inherit common data access functionalities.
 *
 */
public class ClientDAO extends AbstractDAO<Client> {

    /**
     * Checks if a client with the same name and email already exists in the database.
     *
     * @param client the {@link Model.Client} object to check for existence based on its properties
     * @return the matching {@link Model.Client} if found otherwise {@code null}
     */
    public Client clientExists(Client client) {
        return findByFields(client);
    }
}
