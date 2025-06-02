package BusinessLogic;

import java.util.*;
import BusinessLogic.validators.ClientEmailValidator;
import BusinessLogic.validators.ClientNameValidator;
import BusinessLogic.validators.Validation;
import BusinessLogic.validators.Validator;
import DataAccess.ClientDAO;
import Model.Client;

/**
 * Business Logic Layer for Client operations
 * Handles validation and coordinates with the DAO layer
 */
public class ClientBLL extends Validation<Client> {

    private final List<Validator<Client>> validators;
    private final ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<>();
        validators.add(new ClientNameValidator());
        validators.add(new ClientEmailValidator());
        clientDAO = new ClientDAO();
    }

    /**
     * Gets all clients for table display
     * @return Map.Entry containing table data and column names
     */
    public Map.Entry<Object[][], ArrayList<String>> clientTable() {
        return clientDAO.tableHelper();
    }

    /**
     * Deletes a client by ID
     * @param id the client ID to delete
     * @return true if deletion was successful
     */
    public boolean deleteClient(int id) {
        return clientDAO.delete(id);
    }

    /**
     * Adds a new client
     * @param fields String array containing client data (name, email)
     * @return ArrayList of object properties for UI display
     * @throws InputMismatchException if validation fails
     */
    public ArrayList<Object> addClient(String[] fields) {
        ArrayList<Object> values = new ArrayList<>();
        Client client = new Client(fields[0], fields[1]);
        Client result;
        try {
            isValid(validators, client);
            if(clientDAO.clientExists(client)!=null){
                throw new RuntimeException("Insertion Error: Client already exists!.");
            }
            result = clientDAO.insert(client);
            if (result == null) {
                throw new RuntimeException("Insertion Error: Could not add client.");
            }
        } catch (Exception e) {
            String errorMsg = (e.getMessage() != null) ? e.getMessage() : "Unknown database error";
            throw new InputMismatchException(errorMsg);
        }

        clientDAO.retrieveProperties(result, values, new ArrayList<>());
        return values;
    }

    /**
     * Updates an existing client
     * @param id the client ID to update
     * @param fields String array containing updated client data (name, email)
     * @throws InputMismatchException if validation fails
     */
    public void updateClient(int id, String[] fields) {
        Client client = new Client(id, fields[0], fields[1]);

        try {
            isValid(validators, client);
            Client result = clientDAO.update(client);
            if (result == null) {
                throw new RuntimeException("Update Error");
            }
        } catch (Exception e) {
            String errorMsg = (e.getMessage() != null) ? e.getMessage() : "Unknown database error";
            throw new InputMismatchException(errorMsg);
        }
    }
}