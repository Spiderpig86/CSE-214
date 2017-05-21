/**
 * The <code>FullListException</code> extends <code>RuntimeException</code>
 * which is a custom exception thrown in <code>MenuGUI</code> when
 * the <code>menuStore</code> is filled with items.
 *
 * @author M. D. W.
 *    e-mail: Some email
 *    Stony Brook ID: Some ID
 **/
public class FullListException extends RuntimeException {

    /**
     * Returns an instance of <code>FullListException</code>.
     *
     */
    public FullListException() {
        super();
    }

    /**
     * Returns an instance of <code>FullListException</code>.
     *
     * @param message
     *      The custom <code>message</code> that would be passed on
     *      with the exception.
     *
     */
    public FullListException(String message) {
        super(message);
    }

}
