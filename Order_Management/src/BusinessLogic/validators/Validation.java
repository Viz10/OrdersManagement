package BusinessLogic.validators;

import java.util.InputMismatchException;
import java.util.List;

/**
 * A generic class that provides validation mechanisms using a list of {@link Validator} instances.
 *
 * @param <T> the type of object to be validated.
 */
public class Validation<T> {

    /**
     * Validates the given object using a list of validators.
     *
     * @param validators the list of validators used to check the validity of the object.
     * @param object     the object to validate.
     * @throws InputMismatchException if one or more validators fail the validation.
     */
    public void isValid(List<Validator<T>> validators, T object) {
        StringBuilder errorMessages = new StringBuilder();
        boolean valid = true;

        for (var v : validators) {
            try {
                v.validate(object);
            } catch (Exception e) {
                valid = false;
                errorMessages.append(e.getMessage()).append("\n");
            }
        }
        if (!valid) {
            throw new InputMismatchException(errorMessages.toString());
        }
    }

    /**
     * Validates that the provided string represents a valid integer.
     *
     * @param number the string to test for integer validity.
     * @throws IllegalArgumentException if the string is null, empty or does not represent a valid integer.
     */
    public void validateInteger(String number) {
        if (number == null || number.isEmpty()) {
            throw new IllegalArgumentException("Cannot insert empty number field");
        }
        if (!number.matches("\\d+")) {
            throw new IllegalArgumentException("Field must be a valid integer");
        }
    }

    /**
     * Validates that the provided string represents a valid double.
     *
     * @param number the string to validate as a double.
     * @throws IllegalArgumentException if the string is null, empty or does not represent a valid double.
     */
    public void validateDouble(String number) {
        if (number == null || number.isEmpty()) {
            throw new IllegalArgumentException("Cannot insert empty number field");
        }
        if (!number.matches("\\d+(\\.\\d+)?")) {
            throw new IllegalArgumentException("Field must be a valid double");
        }
    }
}
