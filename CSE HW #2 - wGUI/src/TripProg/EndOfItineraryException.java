package TripProg;

/**
 * This exception is thrown whenever the user tries to advance the cursor
 * backward at the head and forward at the tail.
 *
 * @author: Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class EndOfItineraryException extends RuntimeException {

    /**
     * Default constructor of the exception.
     */
    public EndOfItineraryException() {
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
    public EndOfItineraryException(String message) {
        super(message);
    }
}

