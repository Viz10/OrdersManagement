package BusinessLogic.validators;

/**
 * Represents a generic validator that can validate objects of type T.
 *
 * @param <T> the type of object for which the validation is performed.
 */
public interface Validator<T> {

    /**
     * Validates the provided object.
     *
     * @param t the object to be validated.
     */
    void validate(T t);
}
