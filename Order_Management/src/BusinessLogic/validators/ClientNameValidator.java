package BusinessLogic.validators;

import Model.Client;

/**
 * Validates a {@link Model.Client}'s name, ensuring it is neither null nor empty and contains only letters and spaces.
 *
 * @see Validator
 */
public class ClientNameValidator implements Validator<Client> {

    /**
     * Validates the name of the provided {@link Model.Client}.
     *
     * @param t the client whose name is to be validated.
     * @throws IllegalArgumentException if the client's name is null, empty, or contains invalid characters.
     */
    @Override
    public void validate(Client t) {
        if (t.getName() == null || t.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (!t.getName().matches("[a-zA-Z ]*")) {
            throw new IllegalArgumentException("Name must contain only letters and spaces");
        }
    }
}
