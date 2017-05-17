import java.util.*;

import big.data.*;

/**
 * This class is used by the driver class <code>ASMDB</code> to help manage,
 * sort, edit, and display the different actors and movies associated with
 * each <code>Movie</code> object.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */
public class MovieManager {

    private List<Movie> movies;
    private List<Actor> actors;

    /**
     * The default constructor that creates two empty arraylists for movies
     * and actors.
     */
    public MovieManager() {

        movies = new ArrayList<Movie>();
        actors = new ArrayList<Actor>();

    }

    /**
     * The overloaded constructor for the <code>MovieManager</code> class
     * which constructs the two arraylists and takes in a url to add the
     * information to the movie movies and actors arraylists.
     *
     * @param url
     *      The url of the movie to be added.
     */
    public MovieManager(String url) {
        movies = new ArrayList<Movie>();
        actors = new ArrayList<Actor>();
        addMovie(url);
    }

    /**
     * Returns a list of all the <code>Movie</code> objects in this
     * <code>MovieManager</code> object.
     *
     * @return
     *      A list of all the movies added.
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * Returns a list of all the <code>Actor</code> objects in this
     * <code>MovieManager</code> object.
     *
     * @return
     *      A list of all the actors from all the movie objects added.
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     * Sorts all the <code>Movie</code> objects in the arraylist based on the
     * comparator that is given and then returns the arraylist of movies.
     *
     * @param comp
     *      The comparator used to sort the movies.
     * @return
     *      Returns a sorted list of movies.
     */
    public List<Movie> getSortedMovies(Comparator comp) {
        Collections.sort(movies, comp);
        return movies;
    }

    /**
     * Sorts all the <code>Actor</code> objects in the arraylst based on the
     * comparator that is given and returns the arraylist.
     *
     * @param comp
     *      The comparator used to sort the list of actors.
     * @return
     *      Returns a sorted list of actors.
     */
    public List<Actor> getSortedActors(Comparator comp) {
        Collections.sort(actors, comp);
        return actors;
    }

    /**
     * Adds a new <code>Movie</code> with the the given url and information
     * returned from the web request. If no information is returned from the
     * web request, an error is thrown signalling that the movie cannot be
     * added.
     *
     * @param url
     *      The url to retrieve the information of the movie that needs to be
     *      added.
     * @return
     *      Returns the title of the movie added showing that it was
     *      successful.
     */
    public String addMovie(String url) throws big.data.DataInstantiationException {
        try {
            DataSource ds = DataSource.connectXML(url.replace(" ", "+"));
            ds.load(); // Load the data from the source

            // Get the list of actors
            String[] strActors = ds.fetchString("movie/actors").split(",");

            // Create temp list of actors for the movie
            List<Actor> curActors = new ArrayList<Actor>();

            // Add actors into actors list and to temp actor list
            for (String s : strActors) {
                Actor a = new Actor(s);
                // Check if actor already exists and add to count
                int actorIndex = getActorByName(a.getName().trim());
                if (actorIndex > -1) {
                    actors.get(actorIndex).incCount();
                } else { // If it does not exist
                    actors.add(a);
                }
                curActors.add(a);
            }

            // Insert movie and actor entries into the lists
            Movie movie = new Movie(ds.fetchString("movie/title"), Integer
                    .parseInt(ds.fetchString("movie/year")));

            // Set the actors
            movie.setActors(curActors);

            movies.add(movie);

            // Return the name of the movie
            return movie.getTitle();

        } catch (big.data.DataSourceException e) {
            return "Error: Unable to add movie, move not found.";
        }

    }

    /**
     * Searches the list of movies for the movie given its name and returns
     * the index.
     *
     * @param name
     *      The title of the movie that needs to be searched for.
     * @return
     *      Returns the index of where the movie was found. Returns -1 if it
     *      was not found.
     */
    public int getMovieByName(String name) throws java.lang
            .NullPointerException {
        for (int i = 0; i < movies.size(); i++) {
            if (name.equals(movies.get(i).getTitle())) {
                return i;
            }
        }

        // Not found
        return -1;
    }

    /**
     * Searches the list of actors for the actor given its name and returns
     * the index.
     *
     * @param name
     *      The name of the actor that needs to be searched for.
     * @return
     *      Returns the index of where the actor was found. Returns -1 if it
     *      was not found.
     */
    public int getActorByName(String name) {
        for (int i = 0; i < actors.size(); i++) {
            if (name.equals(actors.get(i).getName())) {
                return i;
            }
        }

        // Not found
        return -1;
    }

    /**
     * Deletes the <code>Movie</code> object from the movies arraylist given
     * its index. This would also decrement the count of all actors that
     * appear in it with more than 1 film and remove them if this is their
     * only film. If the movie was unable to be found and deleted, an error
     * message is returned.
     *
     * @param i
     *      The index of the movie found in the movies arraylist.
     * @return
     *      Returns a message telling the user if the deletion was successful
     *      or that the movie was not able to be found.
     *
     */
    public String deleteMovie(int i) {
        try {
            if (i > -1) {
                // First delete the actors in the overall DB
                Movie movie = movies.get(i);

                String title = movie.getTitle();

                for (Actor a : movie.getActors()) {

                    // Check if the actor has more than 1 movie
                    if (a.getCount() > 1) {
                        a.decCount();
                    } else {
                        // Remove the actor with the associated name
                        actors.remove(getActorByName(a.getName().trim()));
                    }
                }

                // Now we can delete the movie
                movies.remove(i);

                // Return status
                return "\"" + title + "\" deleted.";
            } else {
                return "Error: Movie not found.";
            }
        } catch (java.lang.NullPointerException e) {
            return "Error: Movie could not be deleted since it does not exist.";
        }
    }

    /**
     * Returns a string representation in a table of all the
     * <code>Movie</code> objects in the movie manager with its title, year,
     * and actors.
     *
     * @return
     *      Returns a table with all the information for all the movie objects.
     */
    public String toString() {
        String formattedTable = "";
        // Title
        formattedTable += String.format("%-35s%-10s%s", "Title", "Year",
                "Actors\n");
        // Dashed lines
        formattedTable += new String(new char[80]).replace("\0", "-") + "\n";

        // Iterate over all the movies
        int i = 0;
        for (Movie m: movies) {
            formattedTable += m.toString();
            i++;
        }

        if (i > 0)
            return formattedTable;
        else
            return "No data to sort.";
    }

    /**
     * Returns a table showing all the actors in the movie manager and how
     * many movies each actor stars in given the list of movies in the movies
     * arraylist.
     *
     * @return
     *      Returns a table with the information associated with each actor.
     */
    public String getActorTable() {
        String formattedTable = "";

        // Title
        formattedTable += String.format("%-35s%s", "Actor", "Number of " +
                        "Movies\n");
        // Dashed lines
        formattedTable += new String(new char[80]).replace("\0", "-") + "\n";

        // Iterate over all actors
        int i = 0;
        for (Actor a: actors) {
            formattedTable += a.toString();
            i++;
        }

        if (i > 0)
            return formattedTable;
        else
            return "No data to sort.";
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
