/**
 * The <code>Actor</code> object stores the information associated with the
 * actor such as their name and the number of movies that they star in given
 * the <code>Movie</code> objects stored in the <code>MovieManager</code>
 * class. The name of the actor and the number of movies they star in.
 *
 * @author Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class Actor implements Comparable<Actor> {

    private String name;
    private int count = 1;

    /**
     * The constructor for the <code>Actor</code> object that takes in a name
     * and sets the name of the actor.
     *
     * @param name
     *      The name for the <code>Actor</code> object.
     */
    public Actor(String name) {
        this.name = name.trim();
    }

    /**
     * Returns the name of the actor stored in this <code>Actor</code> object.
     *
     * @return
     *      Returns the name of the actor.
     */
    public String getName() {
        return name.trim();
    }

    /**
     * Sets the name of the <code>Actor</code> object.
     *
     * @param name
     *      The name to be set to this actor.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the number of movies that the <code>Actor</code> is in given
     * the list of movies in the <code>MovieManager</code>.
     *
     * @return
     *      Returns the number of movies that the actor is in.
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the number of movies that the <code>Actor</code> is in.
     *
     * @param count
     *      Sets the number of movies the actor is in.
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Compares the number of movies that this <code>Actor</code> stars in
     * with the <code>Actor</code> given in the parameter.
     *
     * @param a
     *      The <code>Actor</code> object that will be compared to with this
     *      object.
     *
     * <dt>Preconditions:
     *      <dd>The parameter actor object is not null.</dd></dt>
     *
     * <dt>Postconditions:
     *      <dd>The integer representing the relation of this
     *      <code>Actor</code> object and the parameter are returned.</dd></dt>
     *
     * @return
     *      Returns an integer telling us if the <code>Actor</code> the same,
     *      greater, or fewer number of movies.
     */
    public int compareTo(Actor a) {
            if (this.count == a.getCount())
                return 0;
            else if (this.count > a.getCount())
                return 1;
            else
                return -1;
    }

    /**
     * Increments the number of movies by 1.
     */
    public void incCount() {
        setCount(this.count + 1);
    }

    /**
     * Decrements the number of movies by 1.
     */
    public void decCount() {
        setCount(this.count - 1);
    }

    /**
     * Returns a string representation of the <code>Actor</code> object with
     * the Actor's name and the number of movies they star in.
     *
     * @return
            Returns a string representation of the <code>Actor</code> object with
     *      the Actor's name and the number of movies they star in.
     */
    public String toString() {
        return String.format("%-35s%d\n", this.getName(), this.getCount());
    }
}
