import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a <code>Movie</code> object which is implemented by
 * the <code>MovieManager</code> in order to represent the associated movie
 * with this object. This object stores the title, year, and an
 * <code>ArrayList</code> of actors associated with this movie.
 *
 * @author Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class Movie {

    // Data fields
    private String title;
    private int year;
    private List<Actor> actors;

    /**
     * This is the default constructor of the <code>Movie</code> object where
     * the title is an empty string, the year is -1, and the arraylist of
     * actors is empty.
     */
    public Movie() {
        this.title = "";
        this.year = -1;
        this.actors = new ArrayList<Actor>();
    }

    /**
     * The overloaded constructor for the <code>Movie</code> object which
     * takes in a title, year, and arraylist of actors. The associated
     * variables are set to the corresponding parameters.
     *
     * @param title
     *      The title of the movie.
     * @param year
     *      The year the movie was released.
     */
    public Movie(String title, int year) {
        this.title = title;
        this.year = year;
        this.actors = new ArrayList<Actor>();;
    }

    /**
     * Returns the title of the movie in this <code>Movie</code> object.
     *
     * @return
     *      Returns the title of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the movie in this <code>Movie</code> object.
     *
     * @param title
     *      Sets the title of the movie to the parameter.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the year of the movie in this <code>Movie</code> object.
     *
     * @return
     *      Returns the year the movie was made.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of the movie in this <code>Movie</code> object.
     *
     * @param year
     *      Sets the year of the movie to the parameter.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns the list of actors associated with the <code>Movie</code>.
     *
     * @return
     *      A list of all the actors in this movie.
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     * Sets the list of actors in this <code>Movie</code> object.
     *
     * @param actors
     *      The list of actors in the movie.
     */
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    /**
     * Returns a string representation of all the actors within the movie
     * separated by commas.
     *
     * @return
     *      A string with a list of all the actors in the movie.
     */
    public String getActorsString() {
        String actorStr = "";
        for (Actor a: actors) {
            actorStr += a.getName() + ",";
        }
        return actorStr;
    }

    /**
     * Returns a string representation of this <code>Movie</code> with the
     * title of the movie, the year it was made, and the actors that are in
     * the movie.
     *
     * @return
     *      Returns a string representation of the <code>Movie</code> object
     *      with the title, the year, and the actors.
     */
    public String toString() {
        return String.format("%-35s%-10d%s\n", this.title, this.year,
                removeTrailingChar(this.getActorsString()));
    }

    /**
     * Removes the last character within the string.
     *
     * @param s
     *      The string that the user wants to remove the last character from.
     * @return
     *      Returns the formatted string without the last character.
     */
    public String removeTrailingChar(String s) {
        return s.substring(0, s.length() - 1);
    }
}
