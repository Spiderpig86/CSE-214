package TripProg;

/**
 * The <code>TripStop</code> class is implemented as objects in the
 * <code>TripStopNode</code> class which would keep track of the current
 * trip's location, distance, and activity present.
 *
 * @author Stanley Lim
 *      email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 *
 */
public class TripStop {

    // The location of location of the stop.
    private String currentLocation;
    // The distance away of the stop.
    private int currentDistance;
    // Description of activities in the stop.
    private String currentActivity;

    /**
     * Default constructor for the <code>TripStop</code> object which
     * gets returned.
     */
    public TripStop() {
        currentLocation = "";
        currentDistance = 0;
        currentActivity = "";
    }

    /**
     * The alternate constructor for the <code>TripStop</code> class
     * which takes in 3 parameters that set the properties for the
     * <code>TripStop</code> class.
     *
     * @param location
     *      The location associated of this stop as a string.
     * @param distance
     *      The distance away of this destination in int.
     * @param activity
     *      The description of the activity at this location in string.
     *
     * @throws IllegalArgumentException
     */
    public TripStop(String location, int distance, String activity) throws IllegalArgumentException {

        this.currentLocation = location;
        if (distance < 0)
            throw new IllegalArgumentException();
        this.currentDistance = distance;
        this.currentActivity = activity;

    }

    /**
     * Returns the location for this <code>TripStop</code> object.
     *
     * @return
     *      Returns the location of this <code>TripStop</code>.
     */
    public String getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Sets the location for this <code>TripStop</code> object with the
     * location specified by the user in the <code>currentLocation</code>
     * parameter.
     *
     * @param currentLocation
     *      The string for the new location the user sets for this
     *      <code>TripStop</code> object.
     *
     *  <dt>Preconditions:
     *      <dd>Parameter <code>currentLocation</code> must be a non
     *      null string where the length > 0.</dd></dt>
     *
     *  @throws EmptyParamException
     *      Exception is thrown if the string entered for the location
     *      is empty.
     */
    public void setCurrentLocation(String currentLocation)
            throws EmptyParamException {
        if (currentLocation.isEmpty())
            throw new EmptyParamException("Error: The currentLocation" +
                    "parameter was empty. Please enter a valid location.");
        this.currentLocation = currentLocation;
    }

    /**
     * Returns the distance for this <code>TripStop</code> object.
     *
     * @return
     *      Returns the distance of this <code>TripStop</code>.
     */
    public int getCurrentDistance() {
        return currentDistance;
    }

    /**
     * Sets the distance for this <code>TripStop</code> object with the
     * location specified by the user in the <code>currentDistance</code>
     * parameter.
     *
     * @param currentDistance
     *      The string for the new location the user sets for this
     *      <code>TripStop</code> object.
     *
     *  <dt>Preconditions:
     *      <dd>Parameter <code>currentDistance</code> must be a
     *      non-negative value where <code>currentDistance</code> > 0.</dd></dt>
     */
    public void setCurrentDistance(int currentDistance)
            throws IllegalArgumentException {
        if (currentDistance < 0)
            throw new IllegalArgumentException("Error: The entered distance" +
                    "cannot be negative. Please try again.");
        this.currentDistance = currentDistance;
    }

    /**
     * Returns the activity for this <code>TripStop</code> object.
     *
     * @return
     *      Returns the activity of this <code>TripStop</code>.
     */
    public String getCurrentActivity() {
        return currentActivity;
    }

    /**
     * Sets the activity for this <code>TripStop</code> object with the
     * location specified by the user in the <code>currentActivity</code>
     * parameter.
     *
     * @param currentActivity
     *      The string for the new activity the user sets for this
     *      <code>TripStop</code> object.
     *
     *  <dt>Preconditions:
     *      <dd>Parameter <code>currentActivity</code> must be a non
     *      null string where the length > 0.</dd></dt>
     *
     *  @throws EmptyParamException
     *      Exception is thrown if the string entered for the activity
     *      is empty.
     */
    public void setCurrentActivity(String currentActivity)
            throws EmptyParamException {
        if (currentActivity.isEmpty())
            throw new EmptyParamException("Error: The given description" +
                    "for the activity is empty. Please try again.");
        this.currentActivity = currentActivity;
    }

    /**
     * Returns the formatted representation of the data associated with this
     * <code>TripStop</code> object.
     *
     * @return
     *      Returns the formatted string displaying all the data of this
     *      <code>TripStop</code> object.
     */
    @Override
    public String toString() {
        return String.format("%-30s%-40s%10s", getCurrentLocation(),
                getCurrentActivity(), getCurrentDistance() + " miles");
    }

    /**
     * Clones this <code>TripStop</code> object by returning a new
     * <code>TripStop</code> object with the same properties as this
     * <code>TripStop</code> object.
     *
     * @return
     *      Returns a new copy of this object with the same properties
     *      as this one.
     */
    @Override
    public Object clone() {
        return new TripStop(getCurrentLocation(), getCurrentDistance(),
                getCurrentActivity());
    }

    /**
     * Compares this <code>TripStop</code> object to another
     * <code>TripStop</code> object. If both objects have the same location,
     * activity, and distance, the function would return true. If not, it
     * would return false.
     *
     * @param trip
     *      An object to which this <code>TripStop</code> is compared.
     *
     * @return
     *      If true, this indicates that <code>trip</code> refers to the same
     *      <code>TripStop</code> object with the same location, activity,
     *      and distance.
     *
     */
    public boolean equals(TripStop trip) {
//        System.out.println(trip.getCurrentLocation().equals(getCurrentLocation()));
//        System.out.println(trip.getCurrentActivity().equals(getCurrentActivity()));
//        System.out.println(trip.getCurrentDistance() == getCurrentDistance());
        return (trip.getCurrentLocation().equals(getCurrentLocation()) &&
                trip.getCurrentActivity().equals(getCurrentActivity()) &&
                trip.getCurrentDistance() == getCurrentDistance());
    }

}
