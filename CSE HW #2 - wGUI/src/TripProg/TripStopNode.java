package TripProg;

/**
 * The <code>TripStopNode</code> class serves as a wrapper for
 * <code>TripStopNode</code> objects which keeps track of the node
 * <code>data</code> with links to the <code>prev</code> and <code>next</code>
 * nodes.
 *
 * @author Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class TripStopNode {

    private TripStop data; // The trip data associated with this link node.
    private TripStopNode next; // The reference to the next TripStopNode object
    private TripStopNode prev; // The reference to the previous TripStopNode
    // object.

    /**
     * The constructor for the <code>TripStopNode</code> which returns an
     * instance of <code>TripStopNode</code>.
     *
     * @param initData
     *      The <code>TripStop</code> object that is associated with this
     *      <code>TripStopNode</code> object.
     */
    public TripStopNode(TripStop initData) {
        data = initData;
    }

    /**
     * Returns the reference to the data member variable of the list node.
     *
     * @return
     *      The reference to the data member variable of the list node.
     */
    public TripStop getData() {
        return data;
    }

    /**
     * Sets the data private field to the one passed as a parameter.
     *
     * <dt>Preconditions
     *      <dd><code>newData</code> is not null.</dd></dt>
     *
     * @param newData
     *      <code>newData</code> is a reference to a new <code>TripStop</code>
     *      object to update the data member variable. This parameter must
     *      not be null, since we should never have a <code>TripStopNode</code>
     *      with null data since it serves only as a wrapper for the
     *      <code>TripStop</code> class.
     *
     * @throws IllegalArgumentException
     *      Thrown if <code>newData</code> is null.
     */
    public void setData(TripStop newData) throws IllegalArgumentException {
        this.data = newData;
    }

    /**
     * Returns the reference to the next member variable of the list node.
     * Can be null, meaning that there's no next <code>TripStopNode</code>.
     *
     * @return
     *      The reference to the next member variable of the list node. Can
     *      be null, meaning that there's no next <code>TripStopNode</code>.
     */
    public TripStopNode getNext() {
        return next;
    }

    /**
     * Updates the next member variable with a new <code>TripStopNode</code>
     * reference.
     *
     * @param newNext
     *      Reference to a new <code>TripStopNode</code> object to update
     *      the next member variable. This parameter may be null, since
     *      it is okay to have no next <code>TripStopNode</code>.
     */
    public void setNext(TripStopNode newNext) {
        this.next = newNext;
    }

    /**
     * Gets the reference to the prev member variable of the list node.
     *
     * @return
     *      The reference of the prev member variable. Note that this return
     *      value can be null, meaning that there is no previous
     *      <code>TripStopNode</code> in the list.
     *
     */
    public TripStopNode getPrev() {
        return prev;
    }

    /**
     * Updates the prev member variable with a new <code>TripStopNode</code>
     * reference.
     *
     * @param newPrev
     *      Reference to a new <code>TripStopNode</code> object to update
     *      the prev member variable. The parameter may be null, since it is
     *      okay to have no previous <code>TripStopNode</code>.
     *
     */
    public void setPrev(TripStopNode newPrev) {
        this.prev = newPrev;
    }

    /**
     * Clones this <code>TripStopNode</code> by returning a new
     * <code>TripStopNode</code> object with the same properties from
     * the <code>TripStop</code> associated with this object.
     *
     * @return
     *      Returns a copy of this object with the same properties as the
     *      <code>TripStop</code> object associated with this object.
     *
     */
    @Override
    public Object clone() {
        TripStop newTripStop = (TripStop) this.getData().clone();
        TripStopNode newNode = new TripStopNode(newTripStop);
        return newNode;
    }

    /**
     * Gets the String representation of this <code>TripStopNode</code> object
     * which is neatly formatted with proper spacing for location, activity,
     * and distance away.
     *
     * @return
     *      Gets the formatted String representation of the
     *      <code>TripStopNode</code> object and returns it.
     *
     */
    public String toString() {
        return data.toString();
    }

    /**
     * Compares this <code>TripStopNode</code> object with the parameter
     * <code>trip</code> object and returns a boolean if all of their
     * properties matched.
     *
     * @param trip
     *      An object to which this <code>TripStopNode</code> object is
     *      compared.
     *
     * @return
     *      If true, this indicates that the <code>trip</code> has the same
     *      properties as the current <code>TripStopNode</code> object with
     *      the same location, activity, and distance. Otherwise, return
     *      false.
     *
     */
    public boolean equals(TripStopNode trip) {
        return getData().equals(trip.getData());
    }
}
