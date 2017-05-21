import java.io.Serializable;
import java.util.HashMap;

/**
 * The <code>Building</code> class stores different properties of the
 * building. This is implemented by the <code>Campus</code> class. The
 * <code>Building</code> class extends a <code>HashMap</code> so it can store
 * key values (integers) that reference the <code>Classroom</code> objects in
 * the building. This class allows items to be added, deleted, and edited.
 * This class implements <code>Serializable</code> so it's data could be
 * saved in an object file.
 *
 * @author: Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class Building extends HashMap<Integer, Classroom> implements
        Serializable {

    private String name = "";

    /**
     * Constructs a new <code>Building</code> object with a specific name.
     *
     * @param name
     *      The name of the building.
     */
    public Building(String name) {
        this.name = name;
    }

    /**
     * Adds a classroom into the <code>Building</code> object which can be
     * searchable.
     *
     * @param roomNumber
     *      The room number associated with the new <code>Classroom</code>
     *      object in the Hashmap.
     * @param classroom
     *      The new <code>Classroom</code> object that will be added into
     *      the <code>Building</code>.
     * @throws IllegalArgumentException
     *      The exception is thrown if the room number for the classroom
     *      already exists.
     */
    public void addClassroom(int roomNumber, Classroom classroom) throws
            IllegalArgumentException {
        if (super.get(roomNumber) != null)
            throw new IllegalArgumentException("Room already exists");

        super.put(roomNumber, classroom);
    }

    /**
     * Returns the <code>Classroom</code> object from the collection in this
     * <code>Building</code> object.
     *
     * @param roomNumber
     *      The room number of the <code>Classroom</code> object the user is
     *      trying to serach for.
     * @return
     *      Returns the <code>Classroom</code> object associated with the
     *      room number.
     *  @throws IllegalArgumentException
     *      Exception is thrown if the room is not found in the collection.
     */
    public Classroom getClassroom(int roomNumber) throws
            IllegalArgumentException  {
        Classroom room =  super.get(roomNumber);
        if (room == null)
            throw new IllegalArgumentException("Error: Room number " +
                    roomNumber + " does not exist");

        return room;
    }

    /**
     * Removes the <code>Classroom</code> objected associated with the user
     * input room number.
     *
     * @param roomNumber
     *      The room number of the classroom that the user is trying to remove.
     * @throws IllegalArgumentException
     *      Exception is thrown if the room number is null or if the room
     *      number could not be found.
     */
    public void removeClassroom(int roomNumber) throws IllegalArgumentException {

        // Check if the roomNumber is invalid
        if (roomNumber == 0)
            throw new IllegalArgumentException("Error: The given room number " +
                    "is null.");

        // Check if the key or the roomNumber exists in the HashMap
        if (this.get(roomNumber) ==  null)
            throw new IllegalArgumentException("Error: The given room number " +
                    "could not be found.");


        super.remove(roomNumber);
    }

    /**
     * Returns the name of this <code>Building</code> object.
     *
     * @return
     *      The name of this building.
     */
    public String getName() {
        return name;
    }
}
