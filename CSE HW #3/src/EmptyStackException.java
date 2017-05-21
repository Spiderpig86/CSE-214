/**
 * This exception is thrown when the stack contains no more items for
 * the program to pop. Contains a constructor that allows for custom input
 * also.
 *
 * @author: Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class EmptyStackException extends RuntimeException {

    /**
     * Default constructor for the exception.
     */
    public EmptyStackException() {
        super("The stack is full.");
    }

    /**
     * The constructor of the <code>EmptyStackException</code>
     * that takes in a parameter for the exception.
     *
     * @param message
     *      Message is the string variable that allows for the program
     *      to display a custom message for the exception.
     */
    public EmptyStackException(String message) {
        super(message);
    }
}
