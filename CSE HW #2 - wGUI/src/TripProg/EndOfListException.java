package TripProg;

/**
 * This exception is thrown when the user has deleted all the
 * <code>TripStop</code> objects in the <code>Itinerary</code> and the
 * linked list of trips contains no more objects.
 *
 * @author: Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class EndOfListException extends RuntimeException {

    /**
     * Default constructor of the exception.
     */
    public EndOfListException() { super();}

    /**
     * The constructor of the constructor that takes in a parameter
     * for the message.
     *
     * @param message
     *      The defined message that would be output to the user as opposed
     *      to the default stack trace.
     */
    public EndOfListException(String message) {
        super(message);
    }
}

