package TripProg;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * The <code>ControllerGUI</code> class that gets implemented
 * in the <code>TripPlannerGUI</code> class which controls variable
 * values, UI control functions, and other functions. Implements
 * <code>Initializable</code> to allow an initialize function to allow
 * variable values to be initialized and set.
 *
 * @author: Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class ControllerGUI implements Initializable {

    // Define the variables.
    private Itinerary tripA;
    private Itinerary tripB;

    private Itinerary currentTrip; // Reference to the currently selected
    // itinerary.
    private String currentTripID = "A";

    private final String EDIT_STRING_HELP = ", or press enter " +
            "without typing anything to keep.";

    private final String EDIT_INT_HELP = "or press -1 without " +
            "typing anything to keep.";

    // Assigns the buttons to private fields.
    @FXML private TextArea textArea;
    @FXML private TextArea txtSummary;
    @FXML private Button btnCursorForward;
    @FXML private Button btnCursorBack;
    @FXML private Button btnInsertBefore;
    @FXML private Button btnAppend;
    @FXML private Button btnDeleteCursor;
    @FXML private Button btnCursorHead;
    @FXML private Button btnCursorTail;
    @FXML private Button btnEdit;
    @FXML private Button btnSwitch;
    @FXML private Button btnInsertFromOther;
    @FXML private Button btnReplace;
    @FXML private Button btnPrint;
    @FXML private Button btnClear;
    @FXML private Button btnQuit;

    /**
     * Prints the string into the textarea.
     *
     * @param s
     *      The string that the user wants to be printed.
     */
    public void printString(String s) {
        textArea.appendText(s + "\n");
    }

    /**
     * Called to initialize the control that implements
     * <code>Initializable.</code>
     *
     * @param location
     *      Location used to resolve relative paths for the root object, or
     *      null if the location is not known.
     *
     * @param resources
     *      The resources used to localize the root object, or null if
     *      the root object was not localized.
     */
    @FXML
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        load();
        hookFunctions();
    }


    public void load() {
        // Initialize the itinerary objects.
        tripA = new Itinerary();
        tripB = (Itinerary) tripA.clone();

        // Give the currentTrip a target
        currentTrip = tripA;
        currentTripID = "A";

        displayMenu();
    }

    public void hookFunctions() {
        btnCursorBack.setOnAction(e->moveCursorBackward());
        btnCursorForward.setOnAction(e->moveCursorForward());
        btnInsertBefore.setOnAction(e->insertBeforeCursor());
        btnAppend.setOnAction(e->appendToTail());
        btnDeleteCursor.setOnAction(e->deleteCurrent());
        btnCursorHead.setOnAction(e->resetToHead());
        btnCursorTail.setOnAction(e->resetToTail());
        btnEdit.setOnAction(e->editCursor());
        btnSwitch.setOnAction(e->switchItinerary(false));
        btnInsertFromOther.setOnAction(e->insertFromOtherItinerary());
        btnReplace.setOnAction(e->replaceItinerary());
        btnPrint.setOnAction(e->printItinerary());
        btnClear.setOnAction(e->clearItinerary());
        btnQuit.setOnAction(e->quit());
    }

    /**
     * Outputs the program commands into the console window.
     */
    public void displayMenu() {
        printString("F-Cursor forward");
        printString("B-Cursor backward");
        printString("I-Insert before cursor");
        printString("A-Append to tail");
        printString("D-Delete and move cursor forward");
        printString("H-Cursor to Head");
        printString("T-Cursor to Tail");
        printString("E-Edit cursor");
        printString("S-Switch itinerary");
        printString("O-Insert cursor from other itinerary after" +
                " cursor from this itinerary after cursor from this " +
                "itinerary");
        printString("R-Replace this itinerary with a copy of the itinerary.");
        printString("P-Print current itinerary, including summary.");
        printString("C-Clear current itinerary.");
        printString("Q-Quit");

        printString("");
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
    public String waitForLocation(boolean editMode) {
        printString("Enter Location" + (((editMode) ? EDIT_STRING_HELP :
                ":")));
        String location = StringDialog.showDialog("Please enter the" +
                " location.");

        if (location.length() < 1)
            location = "NULL";

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
    public String waitForActivity(boolean editMode) {
        printString("Enter Activity" + ((editMode) ? EDIT_STRING_HELP :
                ":"));
        String activity = StringDialog.showDialog("Please enter an" +
                " activity.");

        if (activity.length() < 1)
            activity = "NULL";

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
    public int waitForDistance(boolean editMode)
            throws IllegalArgumentException {
        printString("Enter Distance" + ((editMode) ? EDIT_INT_HELP :
                ":"));
        int distance;
        try {
            distance = IntegerDialog.showDialog("Please enter the distance."
                + ((editMode) ? EDIT_INT_HELP : ""));

            if (distance == -1)
                return -1;

            if (distance < 0)
                throw new IllegalArgumentException("Error: Invalid distance." +
                        " distances must be >= 0. Try again.");

            return distance;
        } catch (IllegalArgumentException e) {
            printString(e.getMessage());
            return waitForDistance(false);
        }
    }

    /**
     * Returns the pointer to <code>tripA</code>.
     *
     * @return
     *      Returns a pointer to tripA.
     */
    private Itinerary switchToA() {
        currentTripID = "A";
        return tripA;
    }

    /**
     * Returns the pointer to <code>tripB</code>.
     *
     * @return
     *      Returns a pointer to tripB.
     */
    private Itinerary switchToB() {
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
    public void moveCursorForward() {
        try {
            currentTrip.cursorForward();
            printString("Cursor moved forward.");
        } catch (EndOfItineraryException e) {
            printString(e.getMessage());
        }
    }

    /**
     * Moves the cursor back by 1 position.
     *
     * <dt>Postconditions:
     *      <dd>Moves the cursor back 1 position. A message will be
     *      displayed if it is at the head of the list.</dd></dt>
     */
    public void moveCursorBackward() {
        try {
            currentTrip.cursorBackward();
            printString("Cursor moved back.");
        } catch (EndOfItineraryException e) {
            printString(e.getMessage());
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
    public void insertBeforeCursor() {
        String location = waitForLocation(false);
        String activity = waitForActivity(false);
        int distance = waitForDistance(false);

        // If the user closed the dialogs
        if (location.equals("NULL") || activity.equals("NULL")) {
            printString("Error: Unable to add item since some fields" +
                    " are empty");
            return;
        }

        TripStop newStop = new TripStop(location, distance, activity);
        currentTrip.insertBeforeCursor(newStop);
        printString("Added item before cursor.");
        printSummary(currentTrip.toString()); // Updates summary view
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
    public void appendToTail()
            throws IllegalArgumentException {
        String location = waitForLocation(false);
        String activity = waitForActivity(false);
        int distance;
        try {
            distance = waitForDistance(false);
        } catch (IllegalArgumentException e) {
            printString(e.getMessage());
            distance = waitForDistance(false);
        }

        // If the user closed the dialogs
        if (location.equals("NULL") || activity.equals("NULL")) {
            printString("Error: Unable to add item since some fields" +
                    " are empty");
            return;
        }

        TripStop newStop = new TripStop(location, distance, activity);
        currentTrip.appendToTail(newStop);
        printString("Added item to the tail.");
        printSummary(currentTrip.toString()); // Updates summary view
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
    public void deleteCurrent() {
        try {
            currentTrip.removeCursor();
            printString("The item at the cursor has been deleted.");
        } catch (EndOfListException e) {
            printString(e.getMessage());
        }
    }

    /**
     * Moves the cursor to the head of the <code>Itinerary</code>.
     *
     * <dt>Postcondition:
     *      <dd>The cursor is moved to the head.</dd></dt>
     */
    public void resetToHead() {
        currentTrip.resetCursorToHead();
        printString("Cursor reset to head.");
    }

    /**
     * Moves the cursor to the tail of the <code>Itinerary</code>.
     *
     * <dt>Postcondition:
     *      <dd>The cursor is moved to the tail.</dd></dt>
     */
    public void resetToTail() {
        currentTrip.resetCursorToTail();
        printString("Cursor reset to tail.");;
    }

    /**
     * Prompts the user to enter a new location, activity, and distance for
     * the <code>TripStop</code> object located in the cursor.
     *
     * REMEMBER TO CHECK IF THE TRIPDATA IS NULL.
     */
    public void editCursor() {
        TripStop tripData = currentTrip.getCursorStop();
        if (tripData == null) {
            printString("Unable to edit cursor since it is null.");
            return;
        }
        String newLocation = waitForLocation(true);
        String newActivity = waitForActivity(true);
        int newDistance = waitForDistance(true);

        // Check if any of the edits are null or -1
        if (newLocation.equals("NULL"))
            newLocation = tripData.getCurrentLocation();

        if (newActivity.equals("NULL"))
            newActivity = tripData.getCurrentActivity();

        if (newDistance == -1)
            newDistance = tripData.getCurrentDistance();

        // Set the edits into the tripStop item.
        tripData.setCurrentLocation(newLocation);
        tripData.setCurrentActivity(newActivity);
        tripData.setCurrentDistance(newDistance);

        printString("Edits applied.");
        printSummary(currentTrip.toString()); // Updates summary view
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
    public void switchItinerary(boolean surpressMessage) {
        currentTrip = (currentTripID.equals("A") ? switchToB() :
                switchToA());
        if (!surpressMessage)
            printString("Itinerary switched.");

        printSummary(currentTrip.toString());
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
    public void insertFromOtherItinerary() {
        switchItinerary(true);
        if (currentTrip.getCursorStop() != null) {
            TripStop otherTrip = (TripStop) currentTrip.getCursorStop().clone();
            switchItinerary(true);
            currentTrip.insertBeforeCursor(otherTrip);
        } else {
            switchItinerary(true);
            printString("Error: Other itinerary is empty.");
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
    public void replaceItinerary() {
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
    public void printItinerary() {
        printString("Itinerary:\n" +
                currentTrip.toString());
        printSummary(currentTrip.toString());
    }

    /**
     * Clears the <code>currentTrip</code> object with is an instance
     * <code>Itinerary</code> where the head, tail, cursor, cursorIndex,
     * and number of trips are reset to their default values.
     */
    public void clearItinerary() {
        currentTrip.reset();
        printString("Itinerary cleared.");
    }

    /**
     * Exits the program.
     */
    public void quit() {
        System.exit(0);
    }

    public void printSummary(String trips) {
        if (trips.indexOf("Summary:") < 0)
            return;
        String summary = trips.substring(trips.indexOf("Summary:")
                , trips.length());

        txtSummary.setText(summary);
    }
}


