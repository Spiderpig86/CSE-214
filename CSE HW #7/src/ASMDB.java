import java.util.Collections;
import java.util.Scanner;

/**
 * This is the driver class of the program that allows the user to execute
 * commands to add, remove, edit, and get movie and actor information.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */
public class ASMDB {

    public static Scanner scanner = new Scanner(System.in);

    private static MovieManager mManager;

    // URL placeholders
    private static final String HEAD = "http://www.omdbapi.com/?t=";
    private static final String TAIL = "&y=&plot=short&r=xml";

    /**
     * Main method which is the starting point of the program. The menu gets
     * displayed and waits for the user to enter a command.
     *
     * @param args
     *      Standard method arguments for the program.
     */
    public static void main(String[] args) {
        print("Welcome to A Smiley Movie Data Base, the happiest" +
                " way to manage your DVDs.", false);

        displayMenu();
        waitForCommand();
    }

    // HELPER METHODS

    /**
     * Displays a meny with all the commands of the program.
     */
    public static void displayMenu() {
        print("Main Menu:", false);
        print("I) Import a Movie", true);
        print("D) Delete a Movie", true);
        print("A) Sort Actors", true);
        print("M) Sort Movies", true);
        print("Q) Quit", true);
        print("Please select an option:", false);
    }

    /**
     * Waits for the user to input a command and executes the associated
     * function.
     */
    public static void waitForCommand() {

        if (scanner.hasNext()) {
            String command = scanner.nextLine();

            switch (command.toUpperCase()) {
                case "I":
                    importMovie();
                    break;
                case "D":
                    deleteMovie();
                    break;
                case "A":
                    waitForActorCommand();
                    break;
                case "M":
                    waitForMovieCommand();
                    break;
                case "Q":
                    print("That's all folks!", false);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command, please try again.");
                    break;
            }
            // Reshow the main menu
            displayMenu();

            // Wait for user input again
            waitForCommand();
        }
    }

    /**
     * Displays the actor menu and all of the commands with the
     * <code>Actor</code> objects in the <code>MovieManager</code>.
     */
    public static void displayActorMenu() {
        print("Actor Sorting Options:", false);
        print("AA) Alphabetically Ascending", true);
        print("AD) Alphabetically Descending", true);
        print("NA) By Number of Movies They Are In Ascending", true);
        print("ND) By Number of Movies They Are In Descending", true);
        print("Please select an option (actor):", false);
    }

    /**
     * Waits for the user to input a command from the actor menu.
     */
    public static void waitForActorCommand() {
        displayActorMenu();

        if (scanner.hasNext()) {
            String command = scanner.nextLine();

            switch (command.toUpperCase()) {
                case "AA":
                    actorSortNameA();
                    break;
                case "AD":
                    actorSortNameD();
                    break;
                case "NA":
                    actorSortCountA();
                    break;
                case "ND":
                    actorSortCountD();
                    break;
                default:
                    System.out.println("Invalid command, please try again.");
                    break;
            }
        }
    }

    /**
     * Displays the actor menu and all of the commands with the
     * <code>Movie</code> objects in the <code>MovieManager</code>.
     */
    public static void displayMovieMenu() {
        print("Movie Sorting Options:", false);
        print("TA) Title Ascending (A-Z)", true);
        print("TD) Title Descending (Z-A)", true);
        print("YA) Year Ascending", true);
        print("YD) Year Descending", true);
        print("Please select an option (movie):", false);
    }

    /**
     * Waits for the user to input a command from the movie menu.
     */
    public static void waitForMovieCommand() {
        displayMovieMenu();

        if (scanner.hasNext()) {
            String command = scanner.nextLine();

            switch (command.toUpperCase()) {
                case "TA":
                    movieSortTitleA();
                    break;
                case "TD":
                    movieSortTitleD();
                    break;
                case "YA":
                    movieSortYearA();
                    break;
                case "YD":
                    movieSortYearD();
                    break;
                default:
                    System.out.println("Invalid command, please try again.");
                    break;
            }
        }
    }

    /**
     * Wrapper method that prints strings to the console with optional tabing.
     *
     * @param s
     *      The string that gets printed.
     * @param tabbed
     *      If true, the text is tabbed in the front. If false, there is no
     *      tabbing.
     */
    public static void print(String s, boolean tabbed) {
        System.out.println(((tabbed) ? "\t" : "") + s);
    }

    /**
     * Waits for the user to enter a value with the given prompt.
     *
     * @param description
     *      The prompt asking the user what to enter.
     * @return
     *      Returns the user's response.
     */
    public static String waitForString(String description) {
        // Output instructions to the user
        System.out.println(description);
        Scanner scan = new Scanner(System.in);
        String ret = scan.nextLine();
        if (ret.isEmpty()) {
            ret = waitForString(description);
        }
        return ret;
    }

    // MAIN MENU METHODS

    /**
     * Adds a <code>Movie</code> object to the <code>MovieManager</code>
     * based on the name of the movie that the user gave. An error is
     * returned if the movie could not be added.
     */
    public static void importMovie() {
        String movie = waitForString("Please enter a movie title:");
        // Wait for the request. Will return title of added movie if successful.
        // TODO: Add try catch.
        String success = "";
        try {
            // Check if the movie manager is instantiated yet
            if (mManager == null) {
                mManager = new MovieManager();
                success = mManager.addMovie(HEAD + movie + TAIL);
            } else {
                success = mManager.addMovie(HEAD + movie + TAIL);
            }
        } catch (big.data.DataInstantiationException e) {
            print("Error: Unable to add movie, movie not found.", false);
            return;
        }

        // Fetching data was successful.
        if (success.length() != 0)
            print("Movie added: " + success, false);
        else
            print("Error: Unable to add movie, movie not found.", false);
    }

    /**
     * Deletes the <code>Movie</code> object based on the name given by the
     * user. An error message is printed if the movie could not be found with
     * the given name.
     */
    public static void deleteMovie() {
        try {
        String movie = waitForString("Please enter a movie title:");

            // Get the index of the movie and then remove. Then print status.
            print(mManager.deleteMovie(mManager.getMovieByName
                    (movie)), false);
        } catch (java.lang.NullPointerException e) {
            print("Error: Movie could not be deleted since it does not exist" +
                    ".", false);
        }
    }

    // ACTOR METHODS

    /**
     * Utilizes the <code>NameComparator</code> to sort the
     * <code>Actor</code> objects in the <code>MovieManager</code> in
     * alphabetical order. The method then prints out a table with with all
     * the actors sorted and their respective data.
     */
    public static void actorSortNameA() {
        mManager.getSortedActors(new NameComparator());
        print(mManager.getActorTable(), false);
    }

    /**
     * Utilizes the <code>NameComparator</code> to sort the
     * <code>Actor</code> objects in the <code>MovieManager</code> in
     * reverse alphabetical order. The method then prints out a table with with
     * all the actors sorted and their respective data.
     */
    public static void actorSortNameD() {
        mManager.getSortedActors(new NameComparator());
        Collections.reverse(mManager.getActors());
        print(mManager.getActorTable(), false);
    }

    /**
     * Utilizes the <code>CountComparator</code> to sort the
     * <code>Actor</code> objects in numerical ascending order based on the
     * number of movies each <code>Actor</code> was in.
     */
    public static void actorSortCountA() {
        mManager.getSortedActors(new CountComparator());
        print(mManager.getActorTable(), false);
    }

    /**
     * Utilizes the <code>CountComparator</code> to sort the
     * <code>Actor</code> objects in numerical descending order based on the
     * number of movies each <code>Actor</code> was in.
     */
    public static void actorSortCountD() {
        mManager.getSortedActors(new CountComparator());
        Collections.reverse(mManager.getActors());
        print(mManager.getActorTable(), false);
    }

    // MOVIE METHODS
    /**
     * Utilizes the <code>TitleComparator</code> to sort the
     * <code>Movie</code> objects in the <code>MovieManager</code> in
     * alphabetical order. The method then prints out a table with with all
     * the movies sorted and their respective data.
     */
    public static void movieSortTitleA() {
        mManager.getSortedMovies(new TitleComparator());
        print(mManager.toString(), false);
    }

    /**
     * Utilizes the <code>TitleComparator</code> to sort the
     * <code>Movie</code> objects in the <code>MovieManager</code> in
     * reverse alphabetical order. The method then prints out a table with with
     * all the movies sorted and their respective data.
     */
    public static void movieSortTitleD() {
        mManager.getSortedMovies(new TitleComparator());
        Collections.reverse(mManager.getMovies()); // Reverse the collection
        print(mManager.toString(), false);
    }

    /**
     * Utilizes the <code>YearComparator</code> to sort the
     * <code>Movie</code> objects in the <code>MovieManager</code> in
     * chronological order. The method then prints out a table with with all
     * the movies sorted and their respective data.
     */
    public static void movieSortYearA() {
        mManager.getSortedMovies(new YearComparator());
        print(mManager.toString(), false);
    }

    /**
     * Utilizes the <code>YearComparator</code> to sort the
     * <code>Movie</code> objects in the <code>MovieManager</code> in
     * reverse chronological order. The method then prints out a table with with
     * all the movies sorted and their respective data.
     */
    public static void movieSortYearD() {
        mManager.getSortedMovies(new YearComparator());
        Collections.reverse(mManager.getMovies()); // Reverse the collection
        print(mManager.toString(), false);
    }
}
