import java.io.Serializable;
import java.util.HashMap;

/**
 * This <code>Campus</code> object stores <code>Building</code> objects and
 * more buildings can be added, edited, or deleted.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */
public class Campus extends HashMap<String, Building> implements Serializable {

    /**
     * Adds a <code>Building</code> object into the <code>Campus</code>.
     *
     * @param buildingName
     *      The name or the key used to identify the <code>Building</code>
     *      object.
     * @param building
     *      The new building object.
     * @throws IllegalArgumentException
     *      Thrown if the requested <code>Building</code> with respect to the
     *      key or the building name is existing.
     */
    public void addBuilding(String buildingName, Building building) throws
            IllegalArgumentException {
        if (super.get(buildingName) != null)
            throw new IllegalArgumentException("Building already exists");

        super.put(buildingName, building);
    }

    /**
     * Returns the <code>Building</code> object that gets requested with the
     * associated key or building name.
     *
     * @param buildingName
     *      The key used to identify the associated <code>Building</code>
     *      object.
     * @return
     *      Returns the <code>Building</code> object that gets requested.
     * @throws IllegalArgumentException
     *      Exception is thrown when the associated building could not be
     *      found with the given key or building name.
     */
    public Building getBuilding(String buildingName) throws
            IllegalArgumentException {
        Building building = super.get(buildingName);
        if (building == null)
            throw new IllegalArgumentException("Error: Building " +
                    buildingName + " does not exist");

        return building;
    }

    /**
     * Removes the <code>Building</code> object associated with the given key
     * or building name.
     *
     * @param buildingName
     *      The key used to identify the associated <code>Building</code>
     *      object.
     * @throws IllegalArgumentException
     *      Exception is thrown when the building name is null or it could
     *      not be found within the <code>Campus</code> object.
     */
    public void removeBuilding(String buildingName) throws
            IllegalArgumentException {

        // Check if the buildingName is null
        if (buildingName == null)
            throw new IllegalArgumentException("Error: Building name was " +
                    "empty.");

        // Check if the buildName could not be found in the map
        if (this.get(buildingName) == null)
            throw new IllegalArgumentException("Error: Building name was " +
                    "not found.");

        super.remove(buildingName);
    }
}
