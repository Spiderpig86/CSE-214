package TripProg;

/**
 * This class contains the travel or trip information for the user
 * and allows user manipulation and access to information. The
 * <code>Itinerary</code> class has a collection of destinations with
 * distance to travel, a location, and an activity.
 *
 * @author Stanley Lim
 *      email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 *
 */
public class Itinerary {

    // Keeps a reference of the head of the linked list (the last item added
    // to the list).
    private TripStopNode head;

    // Keeps a reference to the tail of the linked list (the first item added
    // to the list.
    private TripStopNode tail;

    private TripStopNode cursor;

    private int tripStopNodeCount = 0;
    private int totalDistance = 0;

    private int cursorIndex = 0;

    // Constructor of the class. Initializes an empty itinerary with
    // head, tail, and cursor set to null in the reset method

    /**
     * Constructor: Initializes an empty itinerary with no elements --
     * where head, tail, and cursor are set to null. Instance fields
     * <code>tripStopNodeCount</code> and <code>totalDistance</code>
     * are set to 0.
     */
    public Itinerary() {
        reset();
        this.tripStopNodeCount = 0;
        this.cursorIndex = 1;
    }

    /**
     * Returns the number of stops in the <code>Itinerary</code>.
     *
     * @return
     *      Returns the number of stops in the current <code>Itinerary</code>.
     */
    public int getStopsCount() {
        return tripStopNodeCount;
    }

    /**
     * Returns the sum of distances over all <code>TripStopNodes</code>.
     *
     * @return
     *      The sum of distances over all <code>TripStopNodes</code>/
     */
    public int getTotalDistance() {
        return totalDistance;
    }

    /**
     * Returns a reference to the <code>TripStop</code> wrapped by the
     * <code>TripStopNode</code> that cursor points to.
     *
     * @return
     *      Returns a reference to the <code>TripStop</code> wrapped
     *      by the <code>TripStopNode</code> that the cursor points to.
     *      Will return null if the cursor is null.
     */
    public TripStop getCursorStop() {
        if (cursor == null)
            return null;
        return cursor.getData();
    }

    /**
     * Returns the cursor to the start of the list.
     *
     * <dt>Postconditions:
     *      If head is not null, the cursor now reference the first
     *      <code>TripStopNode</code> in the list. If head is null,
     *      the cursor is set to null as well.</dt>
     */
    public void resetCursorToHead() {
        if (head != null)
            cursor = head;
        else {
            cursor = null;
        }
        cursorIndex = 1;
    }

    /**
     * Moves the cursor to select the next <code>TripStopNode</code> object
     * in the list. If cursor == tail, throw an exception.
     * @throws EndOfItineraryException
     *      The cursor could no longer move forward since it has already
     *      reached the end of the list.
     */
    public void cursorForward() throws EndOfItineraryException {
        if (cursor == tail)
            throw new EndOfItineraryException("Error: Reached the tail of" +
                    " the itinerary.");

        cursor = cursor.getNext();
        cursorIndex++;
    }

    /**
     * Moves the cursor to select the previous <code>TripStopNode</code>
     * object in the list. If cursor == head, thrown an exception.
     * @throws EndOfItineraryException
     *      The cursor could no longer move backwards since it has
     *      already reached the head of the list.
     */
    public void cursorBackward() throws EndOfItineraryException {
        if (cursor == head)
            throw new EndOfItineraryException("Error: Reached the head of" +
                    " the itinerary.");
        cursor = cursor.getPrev();
        cursorIndex--;
    }

    /**
     * Inserts a new <code>TripStop</code> object into the
     * <code>Itinerary</code> before the cursor.
     *
     * @param newStop
     *      The new <code>TripStop</code> object that would be inserted
     *      into the <code>Itinerary</code> before the current
     *      cursor position.
     *
     * <dt>Preconditions:
     *      <dd>Param <code>newStop</code> is not null</dd></dt>
     *
     * <dt>Postconditions:
     *      <dd>Param <code>newStop</code> has been wrapped in a
     *      <code>TripStopNode</code> object. If the cursor was previously
     *      not null, the newly created <code>TripStopNode</code> has been
     *      set as the new head of the list (as well as the tail).</dd></dt>
     *
     * @throws IllegalArgumentException
     *      Thrown if <code>newStop</code> is null.
     */
    public void insertBeforeCursor(TripStop newStop)
            throws IllegalArgumentException {
        if (newStop != null) { // Checks if the newStop object is null.
            // Wraps the tripstop info into a new TripStopNode.
            TripStopNode tripNode = new TripStopNode(newStop);
            if (cursor != null) {
                // Set the rest of the list to the newly created object.
                tripNode.setPrev(cursor.getPrev());
                tripNode.setNext(cursor);

                if (cursor.getPrev() == null)
                    head = tripNode;
                else {
                    cursor.getPrev().setNext(tripNode);
                }
                // Sets the link to the rest of the list to the cursor object.
                cursor.setPrev(tripNode);
            } else {
                head = tripNode;
                tail = tripNode;
            }
            cursor = tripNode; // Advance the cursor

            tripStopNodeCount++;
            totalDistance += tripNode.getData().getCurrentDistance();
        } else {
            throw new IllegalArgumentException("Error: Unable to add" +
                    "trip node to list since it is null.");
        }
    }

    /**
     * Inserts the indicated <code>TripStop</code> after the tail of the list.
     *
     * @param newStop
     *      The <code>TripStop</code> object to be wrapped and inserted
     *      into the list after the tail of the list.
     *
     * <dt>Preconditions:
     *      <dd><code>newStop</code> is not null.</dd></dt>
     *
     * <dt>Postconditions:
     *      <dd><code>newStop</code> has been wrapped in a new
     *      <code>TripStopNode</code> object. If tail was previously not null,
     *      the newly created <code>TripStopNode</code> has been inserted
     *      into the list after the tail. If the tail was previously null,
     *      the newly created <code>TripStopNode</code> has been set as the
     *      new head of the list (as well as the tail). The tail now
     *      references the newly created <code>TripStopNode</code>.</dd></dt>
     * @throws IllegalArgumentException
     *      Thrown if <code>newStop</code> is null.
     */
    public void appendToTail(TripStop newStop)
            throws IllegalArgumentException{

        if (newStop == null)
            throw new IllegalArgumentException("Error: The given trip node" +
                    "is null.");

        TripStopNode newNode = new TripStopNode(newStop);

        if (tail != null) {
            TripStopNode tempNode = tail; // Assigns reference of tail to temp.

            tempNode.setNext(newNode); // Appends the new node at the end of the
            // linked list.

            newNode.setPrev(tempNode);

            tail = newNode;
        } else {
            head = newNode;
            tail = newNode;
            cursor = newNode;
        }

        tripStopNodeCount++;
        totalDistance += newNode.getData().getCurrentDistance();
    }

    /**
     * Removes the <code>TripStopNode</code> referenced by cursor and returns
     * the <code>TripStop</code> inside.
     *
     * <dt>Preconditions:
     *      <dd>cursor is not null.</dd></dt>
     *
     * <dt>Postconditions:
     *      <dd>The <code>TripStopNode</code> referenced by cursor has been
     *      removed from the list. All other <code>TripStopNode</code>
     *      objects in the list exist in the same order as before. The cursor
     *      now references the previous <code>TripStopNode</code>.
     *      Exceptions: If the cursor was originally the head, the cursor
     *      will now reference the current head.</dd></dt>
     *
     * @return
     *      The <code>TripStop</code> object that was removed.
     *
     * @throws EndOfListException
     *      If the cursor is null.
     */
    public TripStop removeCursor() throws EndOfListException  {

        if (cursor == null)
            throw new EndOfListException("Error: The given trip node" +
                    "is null.");

        TripStop removed = cursor.getData();

        if (cursor.getPrev() == null) {
            if (cursor.getNext() == null) {
                // This means that the list only has 1 item. Head, tail, and
                // cursor are set to null.
                cursor = null;
                head = null;
                tail = null;
            } else {
                // Runs when cursor is at the head but list has > 1 trips.
                cursor.getNext().setPrev(cursor.getPrev());
                head = cursor.getNext();
                cursor = head;
            }
        } else {
            if (cursor != tail) {
                cursor.getNext().setPrev(cursor.getPrev());
                cursor.getPrev().setNext(cursor.getNext());
                cursor = cursor.getNext();
            } else {
                cursor.getPrev().setNext(cursor.getNext());
                tail = cursor.getPrev();
                resetCursorToTail();
                cursorIndex--;
            }
        }

        // Should work since the references didn't change for the removed
        // node.

        tripStopNodeCount--;
        totalDistance -= removed.getCurrentDistance();

        return removed;
    }

    /**
     * Copies all the elements that existed within the original
     * <code>Itinerary</code> object and appends it into the new
     * <code>Itinerary</code> object called newItinerary and returns it.
     *
     * @return
     *      Returns a new <code>Itinerary</code> object with
     *      <code>TripStop</code> objects that have the same properties
     *      as the objects in the previous <code>Itinerary</code> object
     *      with the same order.
     *
     * <dt>Postcondition:
     *      <dd>A new <code>Itinerary</code> object with the elements
     *      with the same properties and order as the previous</dd></dt>
     */
    @Override
    public Object clone() {
        Itinerary newItinerary = new Itinerary();

        if (getCursorPosition() < 1)
            setCursorPosition(1);

        TripStopNode tempNode = head;

        if (tempNode != null) {
            newItinerary.appendToTail((TripStop) tempNode.getData().clone());
            // Iterates from the head of the list to 1 item before the tail.
            while (tempNode.getNext() != null) {
                tempNode = tempNode.getNext(); // Advance the tempNode
                newItinerary.appendToTail((TripStop) tempNode.getData().clone());
            }

            newItinerary.setCursorPosition(getCursorPosition());
        }

        return newItinerary;
    }

//    public void insertAfterCursor(TripStop newStop)
//            throws IllegalArgumentException {
//
//        if (newStop != null) { // Checks if the newStop object is null.
//            // Wraps the tripstop info into a new TripStopNode.
//            TripStopNode tripNode = new TripStopNode(newStop);
//            if (cursor != null) {
//                // Set the rest of the list to the newly created object.
//                tripNode.setNext(cursor.getNext());
//                tripNode.setPrev(cursor);
//                // Sets the link to the rest of the list to the cursor object.
//                cursor.setPrev(tripNode);
//            } else {
//                head = tripNode;
//                tail = tripNode;
//            }
//            cursor = tripNode; // Advance the cursor
//
//            if (tail == null) head = tail;
//
//            tripStopNodeCount++;
//            totalDistance += tripNode.getData().getCurrentDistance();
//        } else {
//            throw new IllegalArgumentException("Error: Unable to add" +
//                    "trip node to list since it is null.");
//        }
//    }

    /**
     * Returns the cursor to the end of the list.
     *
     * <dt>Postconditions:
     *      <dd>If tail is null, the cursor is set to null as well
     *      which means that the list is empty.</dd></dt>
     */
    public void resetCursorToTail() {
        if (tail != null)
            cursor = tail;
        else {
            cursor = null;
        }
        cursorIndex = getStopsCount();
    }

    /**
     * Sets the head, tail, and cursor to null and setting the
     * <code>tripStopNodeCount</code> and <code>cursorIndex</code> to 0.
     *
     * <dt>Postconditions:
     *      <dd>The head, tail, and cursor are set to null and
     *      <code>tripStopCount</code> and <code>totalDistance</code>
     *      are set to 0.</dd></dt>
     */
    public void reset() {
        head = null;
        tail = null;
        cursor = null;
        tripStopNodeCount = 0;
        totalDistance = 0;
    }

    /**
     * Returns a formatted String representation of the trip information
     * which displays locations, activities, and distance for each
     * <code>TripStopNode</code> data in the <code>Itinerary</code> object.
     *
     * @return
     *      Returns a formatted table of all the trip information in the
     *      <code>Itinerary</code> object.s
     */
    @Override
    public String toString() {
        String tripData = "";
        TripStopNode tempNode = head;

        int i = 1;

        if (tempNode != null) {
            tripData += ((cursorIndex == i) ?
                    ">" : "") + tempNode.toString() + "\n";
            while (tempNode.getNext() != null) {
                i++;
                tempNode = tempNode.getNext();
                tripData += ((cursorIndex == i) ?
                        ">" : "") + tempNode.toString() + "\n";
            }
        }


        if (!tripData.isEmpty())
            tripData += String.format("\nSummary:\nThis trip has %d stops, totaling %d" +
                            " miles. There are %d stops before the cursor and %d stops" +
                            " after the cursor.", getStopsCount(), getTotalDistance(),
                    cursorIndex - 1, getStopsCount() - cursorIndex);
        else
            tripData = "This trip has no stops.";

        return tripData;
    }

    /**
     * Returns the index that the cursor is at.
     *
     * @return
     *      The cursor position of this <code>Itinerary</code>.
     */
    public int getCursorPosition() {
        return cursorIndex;
    }

    /**
     * Sets the current cursor position to position <code>n</code>.
     *
     * @param n
     *      The index that the cursor will be set to.
     */
    public void setCursorPosition(int n) {
        cursorIndex = n;
    }

    /**
     * Updates the total distance in the Intinerary.
     * @param n
     */
    public void setTotalDistance(int n) {
        totalDistance = n;
    }
}
