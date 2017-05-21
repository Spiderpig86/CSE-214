import java.util.Comparator;

/**
 * This class implements the <code>Comparator</code> interface to allow the
 * <code>Collections</code> interface sort each <code>Movie</code> object by
 * title.
 *
 * @author: Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class TitleComparator implements Comparator<Object> {

    /**
     * Method implemented by the <code>Comparator</code> interface to allow
     * the <code>Collections</code> interface to sort the items. This method
     * calls the <code>compareTo</code> method within the <code>String</code>
     * object which represents the movie's name.
     *
     * @param o1
     *      The first object that gets compared which gets typecasted to a
     *      <code>Movie</code>.
     * @param o2
     *      The second object that gets compared with the first after it is
     *      typecasted to a <code>Movie</code>.
     * @return
     *      An integer representing if the first or second object comes first
     *      in alphabetical order.
     */
    public int compare(Object o1, Object o2) {
        // Typecast
        Movie m1 = (Movie) o1;
        Movie m2 = (Movie) o2;

        return (m1.getTitle().compareTo(m2.getTitle()));
    }
}
