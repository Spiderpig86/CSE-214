import java.util.Comparator;

/**
 * This class implements the <code>Comparator</code> interface and compares
 * the year each movie was made. This gets used with the
 * <code>Collections</code> interface to sort the <code>Movie</code> objects
 * based on the year each movie was made.
 *
 * @author: Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class YearComparator implements Comparator<Object> {

    /**
     * Method implemented by the <code>Comparator</code> interface to allow
     * the <code>Collections</code> interface to sort the items.
     * @param o1
     *      The first object that gets compared which gets typecasted to a
     *      <code>Movie</code>.
     * @param o2
     *      The second object that gets compared with the first after getting
     *      typecasted to a <code>Movie</code> object.
     * @return
     *      An integer representing if a1 is greater, a2 is greater, or that
     *      they are both equal.
     */
    public int compare(Object o1, Object o2) {
        // Typecast
        Movie m1 = (Movie) o1;
        Movie m2 = (Movie) o2;

        if (m1.getYear() == m2.getYear())
            return 0;
        else if (m1.getYear() > m2.getYear())
            return 1;
        else
            return -1;
    }
}
