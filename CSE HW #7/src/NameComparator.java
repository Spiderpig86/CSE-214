import java.util.Comparator;

/**
 * This class implements the <code>Comparator</code> interface to allow the
 * <code>Collections</code> interface sort each <code>Actor</code> object by
 * name.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */

// Used for both movie and actor names
public class NameComparator implements Comparator<Object> {

    /**
     * Method implemented by the <code>Comparator</code> interface to allow
     * the <code>Collections</code> interface to sort the items. This method
     * calls the <code>compareTo</code> method within the <code>String</code>
     * object that represents the actor's name.
     *
     * @param o1
     *      The first object that gets compared.
     * @param o2
     *      The second object that gets compared with the first.
     * @return
     *      An integer representing if the first or second object comes first
     *      in alphabetical order.
     */
    public int compare(Object o1, Object o2) {
        // Typecast
        Actor a1 = (Actor) o1;
        Actor a2 = (Actor) o2;

        return (a1.getName().compareTo(a2.getName()));
    }
}
