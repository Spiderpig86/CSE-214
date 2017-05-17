package TripProg;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <code>TripPlanner</code> class is the driver class tha twill
 * instantiate a new instance of the <code>Itinerary</code> class which
 * will be used to store the travel information of the user.
 *
 * @author Stanley Lim
 *      email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 *
 */
public class TripPlanner {

    // Define the variables.
    private static Itinerary tripA;
    private static Itinerary tripB;

    private static Itinerary currentTrip; // Reference to the currently selected
    // itinerary.
    private static String currentTripID = "A";

    private static final String EDIT_STRING_HELP = ", or press enter " +
            "without typing anything to keep.";

    private static final String EDIT_INT_HELP = "or press -1 without " +
            "typing anything to keep.";

    // Global Scanner object.
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Runs the console application by instantiating <code>Itinerary</code>
     * objects, assigns values to variables, displays the menu, and then
     * waits for the user to enter a command.
     *
     * @param args
     *      Default arguments for the main function.
     */
    public static void main(String[] args) {
        // Initialize the itinerary objects.
        tripA = new Itinerary();
        tripB = (Itinerary) tripA.clone();

        // Give the currentTrip a target
        currentTrip = tripA;
        currentTripID = "A";

        displayMenu();

        waitForInput();

    }

    /**
     * Outputs the program commands into the console window.
     */
    public static void displayMenu() {
        System.out.println("F-Cursor forward");
        System.out.println("B-Cursor backward");
        System.out.println("I-Insert before cursor");
        System.out.println("A-Append to tail");
        System.out.println("D-Delete and move cursor forward");
        System.out.println("H-Cursor to Head");
        System.out.println("T-Cursor to Tail");
        System.out.println("E-Edit cursor");
        System.out.println("S-Switch itinerary");
        System.out.println("O-Insert cursor from other itinerary after" +
                " cursor from this itinerary after cursor from this " +
                "itinerary");
        System.out.println("R-Replace this itinerary with a copy of the itinerary.");
        System.out.println("P-Print current itinerary, including summary.");
        System.out.println("C-Clear current itinerary.");
        System.out.println("Q-Quit");

        System.out.println();
    }

    /**
     * Creates a scanner object that allows users to input a command. If
     * the command is invalid, the user will be notified.
     *
     * <dt>Postcondition:
     *      <dd>If the command is valid, the associated function will
     *      be executed.</dd></dt>
     */
    public static void waitForInput() {
        System.out.println("Please enter a command:");
        if (scanner.hasNext()) {
            String command = scanner.nextLine();
            executeCommand(command);
            displayMenu();
            waitForInput();
        }
    }

    /**
     * Takes in the user input and executes the associated command with
     * the user input. If the parameter <code>command</code> is invalid,
     * a message will be shown stating that there is no function that exists
     * with that command.
     *
     * @param command
     *      The associated command that the user input which would execute
     *      a specified function.
     */
    public static void executeCommand(String command) {

        switch (command.toUpperCase()) {
            case "F":
                moveCursorForward();
                break;
            case "B":
                moveCursorBackward();
                break;
            case "I":
                insertBeforeCursor();
                break;
            case "A":
                appendToTail();
                break;
            case "D":
                deleteCurrent();
                break;
            case "H":
                resetToHead();
                break;
            case "T":
                resetToTail();
                break;
            case "E":
                editCursor();
                break;
            case "S":
                switchItinerary(false);
                break;
            case "O":
                insertFromOtherItinerary();
                break;
            case "R":
                replaceItinerary();
                break;
            case "P":
                printItinerary();
                break;
            case "C":
                clearItinerary();
                break;
            case "Q":
                quit();
                break;
            default:
                System.out.println("Command does not exist.");
                break;
        }
    }

    // HELPER METHODS

    /**
     * This method will wait for the user to input a location for a
     * <code>TripStop</code> object.
     *
     * @param editMode
     *      If true, this means that the user is editing a
     *      <code>TripStop</code> object and allows for empty parameters
     *      if the user decides to keep the location that the
     *      <code>TripStop</code> object originally had.
     *
     * @return
     *      Return the string that the user specified for the location.
     */
    public static String waitForLocation(boolean editMode) {
        System.out.println("Enter Location" + (((editMode) ? EDIT_STRING_HELP :
                ":")));
        Scanner scanner = new Scanner(System.in);
        String location = scanner.nextLine();

        if (location.isEmpty() && !editMode) {
            // Recursively ask for valid input.
            location = waitForLocation(false);
        }

        return location;
    }

    /**
     * This method will wait for the user to input an activity for a
     * <code>TripStop</code> object.
     *
     * @param editMode
     *      If true, this means that the user is editing a
     *      <code>TripStop</code> object and allows for empty parameters
     *      if the user decides to keep the activity that the
     *      <code>TripStop</code> object originally had.
     *
     * @return
     *      Return the string that the user specified for the activity.
     */
    public static String waitForActivity(boolean editMode) {
        System.out.println("Enter Activity" + ((editMode) ? EDIT_STRING_HELP :
                ":"));
        String activity = scanner.nextLine();

        if (activity.isEmpty() && !editMode) {
            activity = waitForActivity(false);
        }

        return activity;
    }

    /**
     * This method will wait for the user to input a distance for a
     * <code>TripStop</code> object.
     *
     * @param editMode
     *      If true, this means that the user is editing a
     *      <code>TripStop</code> object and allows for empty parameters
     *      if the user decides to keep the distance that the
     *      <code>TripStop</code> object originally had.
     *
     * @return
     *      Return the value that the user specified for the distance.
     */
    public static int waitForDistance(boolean editMode)
            throws IllegalArgumentException, InputMismatchException {
        System.out.println("Enter Distance" + ((editMode) ? EDIT_INT_HELP :
                ":"));
        int distance;
        try {
            //distance = Integer.parseInt(scanner.nextLine());
            //distance = scanner.nextInt();
            Scanner s = new Scanner(System.in);
            distance = s.nextInt();
//            } else {
//                System.out.println("Please enter only positive integers.");
//                distance = waitForDistance(false);
//            }

            if (distance == -1)
                return -1;

            if (distance < 0)
                throw new IllegalArgumentException("Error: Invalid distance." +
                        " distances must be >= 0. Try again.");

            return distance;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return waitForDistance(false);
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a non-negative numerical" +
                    " value.");
            return waitForDistance(false);
        }
    }

    /**
     * Returns the pointer to <code>tripA</code>.
     *
     * @return
     *      Returns a pointer to tripA.
     */
    private static Itinerary switchToA() {
        currentTripID = "A";
        return tripA;
    }

    /**
     * Returns the pointer to <code>tripB</code>.
     *
     * @return
     *      Returns a pointer to tripB.
     */
    private static Itinerary switchToB() {
        currentTripID = "B";
        return tripB;
    }

    // COMMAND METHODS

    /**
     * Moves the cursor forward by 1 position.
     *
     * <dt>Postconditions:
     *      <dd>Moves the cursor forward by 1 position. A message will
     *      be displayed if it is at the tail of the list.</dd></dt>
     */
    public static void moveCursorForward() {
        try {
            currentTrip.cursorForward();
            System.out.println("Cursor moved forward.");
        } catch (EndOfItineraryException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Moves the cursor back by 1 position.
     *
     * <dt>Postconditions:
     *      <dd>Moves the cursor back 1 position. A message will be
     *      displayed if it is at the head of the list.</dd></dt>
     */
    public static void moveCursorBackward() {
        try {
            currentTrip.cursorBackward();
            System.out.println("Cursor moved back.");
        } catch (EndOfItineraryException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Asks for the user to input the location, activity, and distance for the new
     * <code>TripStop</code> object. The newly created object is then added
     * before the cursor.
     *
     * <dt>Postcondition:
     *      <dd>The new <code>TripStop</code> has been added before the cursor.</dd></dt>
     */
    public static void insertBeforeCursor() {
        String location = waitForLocation(false);
        String activity = waitForActivity(false);
        int distance = waitForDistance(false);

        TripStop newStop = new TripStop(location, distance, activity);
        currentTrip.insertBeforeCursor(newStop);
        System.out.println("Added item before cursor.");
    }

    /**
     * Asks the user to input the location, activity, and distance for
     * the new <code>TripStop</code> object. The newly created object is then added
     * to the tail of the list.
     *
     * <dt>Postcondition:
     *      <dd>The new <code>TripStop</code> object has been added to the
     *      tail of the <code>Itinerary</code>.</dd></dt>
     *
     * @throws IllegalArgumentException
     */
    public static void appendToTail()
            throws IllegalArgumentException {
        String location = waitForLocation(false);
        String activity = waitForActivity(false);
        int distance;
        try {
            distance = waitForDistance(false);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            distance = waitForDistance(false);
        }

        TripStop newStop = new TripStop(location, distance, activity);
        currentTrip.appendToTail(newStop);
        System.out.println("Added item to the tail.");
    }

    /**
     * Deletes the <code>TripStop</code> item located at the cursor. An
     * error message will be printed if the cursor is currently set
     * to null.
     *
     * <dt>Postcondition:
     *      <dd>Deletes the current <code>TripStop</code> item at the cursor
     *      and outputs a message if it is not possible.</dd></dt>
     */
    public static void deleteCurrent() {
        try {
            currentTrip.removeCursor();
            System.out.println("The item at the cursor has been deleted.");
        } catch (EndOfListException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Moves the cursor to the head of the <code>Itinerary</code>.
     *
     * <dt>Postcondition:
     *      <dd>The cursor is moved to the head.</dd></dt>
     */
    public static void resetToHead() {
        currentTrip.resetCursorToHead();
        System.out.println("Cursor reset to head.");
    }

    /**
     * Moves the cursor to the tail of the <code>Itinerary</code>.
     *
     * <dt>Postcondition:
     *      <dd>The cursor is moved to the tail.</dd></dt>
     */
    public static void resetToTail() {
        currentTrip.resetCursorToTail();
        System.out.println("Cursor reset to tail.");
    }

    /**
     * Prompts the user to enter a new location, activity, and distance for
     * the <code>TripStop</code> object located in the cursor.
     *
     * REMEMBER TO CHECK IF THE TRIPDATA IS NULL.
     */
    public static void editCursor() {
        TripStop tripData = currentTrip.getCursorStop();
        if (tripData == null) {
            System.out.println("Unable to edit cursor since it is null.");
            return;
        }
        String newLocation = waitForLocation(true);
        String newActivity = waitForActivity(true);
        int newDistance = waitForDistance(true);

        int oldDistance = tripData.getCurrentDistance();

        // Check if any of the edits are null or -1
        if (newLocation.isEmpty())
            newLocation = tripData.getCurrentLocation();

        if (newActivity.isEmpty())
            newActivity = tripData.getCurrentActivity();

        if (newDistance == -1)
            newDistance = tripData.getCurrentDistance();

        // Set the edits into the tripStop item.
        tripData.setCurrentLocation(newLocation);
        tripData.setCurrentActivity(newActivity);
        tripData.setCurrentDistance(newDistance);

        if (newDistance != oldDistance) {
            currentTrip.setTotalDistance((currentTrip.getTotalDistance()
                    - oldDistance) + newDistance);
        }

        System.out.println("Edits applied.");
    }

    /**
     * Switches the reference of <code>currentTrip</code> between
     * <code>tripA</code> and <code>tripB</code>. If A is selected, the
     * <code>currentTrip</code> will point to B and vice versa.
     *
     * @param surpressMessage
     *      True to make the program not print messages when switching
     *      <code>Itinerary</code> objects. False to print the message.
     *
     * <dt>Postconditions:
     *      <dd>The reference that <code>currentTrip</code> has has
     *      alternated between tripA and tripB.</dd></dt>
     */
    public static void switchItinerary(boolean surpressMessage) {
        currentTrip = (currentTripID.equals("A") ? switchToB() :
                switchToA());
        if (!surpressMessage)
            System.out.println("Itinerary switched.");
    }

    /**
     * Takes the <code>TripStop</code> currently selected by the cursor
     * of the non-selected trip and inserts it into <code>currentTrip</code>
     * before the cursor.
     *
     * <dt>Postcondition:
     *      <dd>The <code>TripStop</code> object from the other
     *      <code>Itinerary</code> object has been added before the cursor
     *      in <code>currentTrip</code>.</dd></dt>
     */
    public static void insertFromOtherItinerary() {
        switchItinerary(true);
        if (currentTrip.getCursorStop() != null) {
            TripStop otherTrip = (TripStop) currentTrip.getCursorStop().clone();
            switchItinerary(true);
            currentTrip.insertBeforeCursor(otherTrip);
        } else {
            switchItinerary(true);
            System.out.println("Error: Other itinerary is empty.");
            return;
        }
    }

    /**
     * Replaces the contents of this current <code>Itinerary</code> with the
     * contents of the other <code>Itinerary</code>.
     * The cursor positions are also identical.
     *
     * <dt>Postconditions:
     *      <dd>The <code>currentTrip</code> should be an identical copy
     *      of the other <code>Itinerary</code> object.</dd></dt>
     */
    public static void replaceItinerary() {
        // Clear references in current itinerary. Let Java GC do the rest.
        // currentTrip.reset();

        Itinerary otherItinerary = (Itinerary) ((currentTripID.equals("A")) ? tripB
                : tripA).clone();

        switch (currentTripID) {
            case "A":
                tripA = otherItinerary;
                currentTrip = switchToA();
                break;
            case "B":
                tripB = otherItinerary;
                currentTrip = switchToB();
                break;
        }
    }

    /**
     * Prints the formatted String representation of all <code>TripStops</code>
     * in the <code>currentTrip</code> object.
     */
    public static void printItinerary() {
        System.out.println("Itinerary:\n" +
                currentTrip.toString());
    }

    /**
     * Clears the <code>currentTrip</code> object with is an instance
     * <code>Itinerary</code> where the head, tail, cursor, cursorIndex,
     * and number of trips are reset to their default values.
     */
    public static void clearItinerary() {
        currentTrip.reset();
        System.out.println("Itinerary cleared.");
    }

    /**
     * Exits the program.
     */
    public static void quit() {
        System.exit(0);
    }
}
