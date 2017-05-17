/**
 * This exception is thrown when the <code>DownloadQueue</code> contains no
 * more items for program. This class contains a default and custom
 * constructor for custom messages.
 */
public class EmptyQueueException extends RuntimeException {

    /**
     * Default constructor for the exception.
     */
    public EmptyQueueException() {
        super("Error: The queue is empty.");
    }

    /**
     * The constructor of the <code>EmptyQueueException</code> that takes in
     * a parameter for the exception.
     *
     * @param message
     *      Message is the string variable that allows for the program to
     *      display a custom message for the exception.
     */
    public EmptyQueueException(String message) {
        super(message);
    }
}
