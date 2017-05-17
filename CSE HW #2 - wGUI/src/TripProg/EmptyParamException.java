package TripProg;

/**
 * The <code>EmptyParamException</code> that is thrown whenever a String
 * parameter for a method or constructor is empty.
 *
 * @author Stanley Lim
 *      email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */
public class EmptyParamException extends RuntimeException {

    /**
     * Default constructor of the exception.
     */
    public EmptyParamException() {
        super();
    }

    /**
     * The constructor of the constructor that takes in a parameter
     * for the message.
     *
     * @param message
     *      The defined message that would be output to the user as opposed
     *      to the default stack trace.
     */
    public EmptyParamException(String message) {
        super(message);
    }
}

