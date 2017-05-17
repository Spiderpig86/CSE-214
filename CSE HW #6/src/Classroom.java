import java.io.Serializable;
import java.util.List;

/**
 * The <code>Classroom</code> class is implemented by the
 * <code>Building</code> class which represents an individual classroom
 * within the building. The class stores instance fields for if it has a
 * whiteboard and/or chalkboard, number of seats, and a string array for the
 * AV equipment. This class implements <code>Serializable</code> so data can
 * be output into an object file.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */
public class Classroom implements Serializable {

    // Data fields
    private boolean hasWhiteboard;
    private boolean hasChalkboard;
    private int numSeats;
    private List<String> AVEquipmentList;

    /**
     * Constructs a <code>Classroom</code> object which is used by the
     * <code>Building</code> class.
     *
     * @param whiteboard
     *      A boolean that determines if this classroom has a whiteboard or
     *      not. This is true if it does and false if it doesn't.
     * @param chalkboard
     *      A boolean that determines if this classroom has a chalkboard or
     *      note. This is true if it does and false if it doesn't.
     * @param seats
     *      An integer that represents the number of seats within this
     *      <code>Classroom</code> object.
     * @param equipment
     *      A list of string objects which stores all the different AV
     *      equipment the classroom has.
     *
     *  <dt>Postcondition:
     *      <dd>A new <code>Classroom</code> object is constructed
     *      with the following params.</dd></dt>
     */
    public Classroom(boolean whiteboard, boolean chalkboard, int seats,
                     List<String> equipment) {

        this.hasWhiteboard = whiteboard;
        this.hasChalkboard = chalkboard;
        this.numSeats = seats;
        this.AVEquipmentList = equipment;

    }

    /**
     * Returns a boolean representing if the classroom has a whiteboard.
     *
     * @return
     *      Returns true if the classroom has a whiteboard and returns false
     *      if it doesn't.
     */
    public boolean isHasWhiteboard() {
        return hasWhiteboard;
    }

    /**
     * Sets if the classroom object contains a whiteboard or not.
     *
     * @param hasWhiteboard
     *      The parameter is set to true if this object contains a whiteboard
     *      and false if it doesn't.
     */
    public void setHasWhiteboard(boolean hasWhiteboard) {
        this.hasWhiteboard = hasWhiteboard;
    }

    /**
     * Sets if the classroom object contains a chalkboard or not.
     *
     * @return
     *      Returns true if the classroom has a chalkboard and false if it
     *      doesn't.
     */
    public boolean isHasChalkboard() {
        return hasChalkboard;
    }

    /**
     * Sets if the classroom has a chalkboard or not.
     *
     * @param hasChalkboard
     *      Parameter is set to true if the classroom has a chalkboard and
     *      set to false if it doesn't.
     */
    public void setHasChalkboard(boolean hasChalkboard) {
        this.hasChalkboard = hasChalkboard;
    }

    /**
     * Returns an integer representing the number of seats within this
     * classroom object.
     *
     * @return
     *      The number of seats in the classroom.
     */
    public int getNumSeats() {
        return numSeats;
    }

    /**
     * Sets the number of seats within the classroom.
     *
     * @param numSeats
     *      The integer with the number of seats within the classroom.
     */
    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    /**
     * Returns a list of string objects of the different AV equipment
     * available in the classroom.
     *
     * @return
     *      A list of strings with all the different AV equipment.
     */
    public List<String> getAVEquipmentList() {
        return AVEquipmentList;
    }

    /**
     * Sets the different AV equipment available in the classroom.
     *
     * @param AVEquipmentList
     *      A list of string objects with all the different AV equipment
     *      available in the classroom.
     */
    public void setAVEquipmentList(List<String> AVEquipmentList) {
        this.AVEquipmentList = AVEquipmentList;
    }

    /**
     * Returns a string representation of all the AV equipment within the
     * classroom.
     *
     * @return
     *      Returns a string with all the AV equipment separated by commas.
     */
    public String getEquipmentString() {
        String equipment = "";
        for (String s: getAVEquipmentList()) {
            equipment += s + ",";
        }
        return equipment;
    }
}
