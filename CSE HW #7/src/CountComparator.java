import java.util.Comparator;

/**
 * This class implements <code>Comparator</code> and compares the number of
 * movies each actor has been in. This gets used with the
 * <code>Collections</code> interface to sort the <code>Actor</code> objects
 * based on movie count.
 *
 * @author: Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */

public class CountComparator implements Comparator<Object> {

    /**
     * Method implemented by the <code>Comparator</code> interface to allow
     * the <code>Collections</code> interface to sort the items.
     *
     * @param o1
     *      The first object that gets compared.
     * @param o2
     *      The second object that gets compared with the first.
     * @return
     *      An integer representing if a1 is greater, a2 is greater, or that
     *      they are both equal.
     */
    public int compare(Object o1, Object o2) {
        // Typecast
        Actor a1 = (Actor) o1;
        Actor a2 = (Actor) o2;

        if (a1.getCount() == a2.getCount())
            return 0;
        else if (a1.getCount() > a2.getCount())
            return 1;
        else
            return -1;
    }
}
