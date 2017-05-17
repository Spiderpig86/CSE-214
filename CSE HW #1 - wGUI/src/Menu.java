/**
 * The <code>Menu</code> class stores <code>MenuItem</code>
 * objects and can manipulate and retrieve data fields of said items.
 *
 *
 * @author M. D. W.
 *    e-mail: stanley.lim@stsonybrook.edu
 *    Stony Brook ID: 110869393
 **/

public class Menu { // NOTE: Starting index of menu items is 1.
    // Remember to leave the first item null.

    private final int MAX_ITEMS = 50; // The fixed number of max items in the
    // menu.
    private MenuItem[] menuStore; // The array that will hold all of the menu
    // items.

    private MenuOpsGUI parent;

    private int menuSize = 0;

    /**
     * Creates a new instance of <code>Menu</code> with no menuItems within
     * <code>menuStore</code>.
     *
     * <dt>Postcondition:
     *      <dd>This <code>Menu</code> has been initialized to an empty
     *      list of <code>MenuItems</code>.</dd></dt>
     *
     */
    public Menu() {
        menuStore = new MenuItem[MAX_ITEMS + 1];
        parent = null;
    }

    /**
     * Creates a new instance of <code>Menu</code> with no menuItems within
     * <code>menuStore</code>.
     *
     * @param parent
     *      Stores the value for the <code>parent</code> window so the
     *      <code>Menu</code> object can call methods in that class.
     *
     * <dt>Postcondition:
     *      <dd>This <code>Menu</code> has been initialized to an empty
     *      list of <code>MenuItems</code> and parent is assigned the value
     *      of the class acting on the <code>Menu</code>.</dd></dt>
     *
     */
    public Menu(MenuOpsGUI parent) {
        menuStore = new MenuItem[MAX_ITEMS + 1];
        this.parent = parent;
    }

    /**
     * Generates a copy of this Menu
     *
     * @return
     *      Returns a value copy of this <code>Menu</code>. Subsequent changes
     *      to the copy will not affect the original, nor vice versa. Note
     *      that the return value must be typecast to a <code>Menu</code>
     *      before it can be used.
     */
    public Object clone() {
        Menu cloned = new Menu(parent);

        for (int i = 1; i < size(); i++) {
            cloned.getMenuList()[i] = menuStore[i].clone();
        }
        return (Menu) cloned;
    }

    /**
     * Compares this <code>Menu</code> to another <code>Menu</code> for
     * equality.
     *
     * @param obj
     *      An object to which this Menu is compared.
     *
     * @return
     *      If true, this indicates that obj refers to a Menu object with the
     *      same <code>MenuItems</code> in the same order as this
     *      <code>Menu</code>. Otherwise, the return value is false.
     */
    public boolean equals (Object obj) {

        if (!(obj instanceof Menu))
            return false;

        Menu otherMenu = (Menu) obj;

        for (int i = 1; i < MAX_ITEMS + 1; i++) {
            if (!menuStore[i].equals(otherMenu.getMenuList()[i]))
                return false;
        }
        return true;
    }

    /**
     * This method will return a reference to the current <code>Menu</code>.
     *
     * @return
     *  Returns a reference to the current <code>Menu</code>.
     */
    public MenuItem[] getMenuList() { // Not sure if it could be added
        return menuStore;
    }

    /**
     * This method will allow users to set the <code>Menu</code> object
     * called <code>currentMenu</code>.
     *
     */
    public void setMenuList(MenuItem[] m) {
        this.menuStore = m;
    }


    /**
     * Determines the number of items currently in this <code>Menu</code>.
     *
     * <dt>Preconditions:
     *      <dd>This <code>Menu</code> object has been instantiated.</dd></dt>
     *
     * @return
     *      The number of <code>MenuItems</code> in this Menu.
     *
     */
    public int size() {
        return menuSize;
    }

    /**
     * Inserts a <code>MenuItem</code> in the specified index of the
     * array.
     *
     * @param item
     *      The new <code>MenuItem</code> to add to this <code>Menu</code>.
     *
     * @param position
     *      The position in the <code>Menu</code> where item will be
     *      inserted.
     *
     *  <dt>Preconditon:
     *      <dd>This <code>Menu</code> object has been instantiated and
     *      1<=<code>position</code><=items_currently_in_list + 1. The number
     *      of <code>MenuItem</code> objects in this <code>Menu</code>
     *      is less than <code>MAX_ITEMS</code>.</dd></dt>
     *
     * <dt>Postcondition:
     *      <dd>The new <code>MenuItem</code> is now stored at the desired
     *      position in the <code>Menu</code>. All <code>MenuItems</code> that
     *      were originally in positions greater than or equal
     *      to <code>position</code> are moved back one position.</dd></dt>
     *
     * @throws FullListException
     *      Indicates that there is no more room inside of the <code>Menu
     *      </code> to store the new <code>MenuItem</code> object.
     *
     * @throws IllegalArgumentException
     *      Indicates that there is no more room inside of this
     *      <code>Menu</code> to store the new <code>MenuItem</code>
     *      object.
     *
     *      NOTES are excluded as of now. Remember to add if needed.
     */
    public void addItem(MenuItem item, int position)
            throws IllegalArgumentException, FullListException{

        try {
            if (this.size() + 1 > MAX_ITEMS) // Checks if the Menu is able
            // to hold more items or not.
                throw new FullListException("Error: The Menu is currently " +
                        "full and unable to add more items.");
        } catch (FullListException e) {
            parent.printMessage(e.getMessage());
            return; // Exits the function.
        }

        try {
            if (position < 1 || position > this.size() + 1) // Checks if the
            // entry position is valid.
                throw new IllegalArgumentException("Error: Unable to insert " +
                        "menu item since the given position does not fall " +
                        "in the valid range.");
        } catch (IllegalArgumentException e) {
            parent.printMessage(e.getMessage());
            return;
        }

        for (int i = MAX_ITEMS; i > 1; i--) {
            if (i >= position)
                menuStore[i] = menuStore[i-1]; // This will shift over all the
                // items to the right.
        }

        // This will replace the existing MenuItem within menuStore with the one
        // that the user specified.
        menuStore[position] = item;

        // Increment the size by 1 after the item has been inserted.
        menuSize++;

        parent.printMessage(String.format("\"%s%s%s%s", item.getItemName() +
                        ": ", item.getItemDescription() + "\" ", "for $" +
                        item.getItemPrice(), " has been added at position " +
                        position));
    }

    /**
     * Removes a <code>MenuItem</code> at the specified <code>position</code>
     * by making the element at that index null.
     *
     * @param position
     *      The position in the <code>Menu</code> where the
     *      <code>MenuItem</code> will be removed from.
     *
     * <dt>Preconditions:</dt>
     *      <dd>The <code>Menu</code> object has been instatianted and
     *       1 <= <code>position</code> <= items_currently_in_list.</dd>
     *
     * <dt>Postconditions:
     *       <dd>The <code>MenuItem</code> at the desired position
     *       in the <code>Menu</code> has been removed. All
     *       <code>MenuItems</code> that were originally in positions
     *       greater than or equal to <code>position</code> are moved
     *       forward(decrement) one position.</dd></dt>
     */
    public void removeItem(int position) {

        try {
            if (this.size() < 1) // Checks if the list is not empty.
                throw new FullListException("Error: Unable to remove the " +
                        "specified item since the list is empty.");
        } catch (FullListException e){
            parent.printMessage(e.getMessage());
        }

        try {
            // Checks if the entry position is valid.
            if (isValidPosition(position))
                throw new IllegalArgumentException("Error: Unable to remove " +
                        "the specified item since the item does not exist " +
                        "at the given position.");
        } catch (IllegalArgumentException e) {
            parent.printMessage(e.getMessage());
        }

        // String removedItem = menuStore[position].getItemName();

        // "Removes" or sets the element at that index to null.
        menuStore[position] = null;

        // Decrement the menu size by 1 when the item is removed.
        menuSize--;

        for (int i = 1; i < MAX_ITEMS; i++)
            if (i >= position)
                menuStore[i] = menuStore[i + 1]; // Shifts the items to the left.


    }

    /**
     * Removes the first instance of a <code>MenuItem</code> with the
     * specified <code>name</code> by making the element at
     * that index null.
     *
     * @param name
     *      The <code>name</code> of the <code>MenuItem</code> that will
     *      be removed in the <code>Menu</code> from the array.
     *
     * <dt>Preconditions:</dt>
     *      <dd>The <code>Menu</code> object has been instantiated and
     *       1 <= <code>position</code> <= items_currently_in_list.</dd>
     *
     * <dt>Postconditions:
     *       <dd>The first <code>MenuItem</code> with the specified name
     *       in the array <code>currentMenu</code> has been removed. All
     *       <code>MenuItems</code> that were originally in positions
     *       greater than or equal to <code>position</code> are moved
     *       forward(decrement) one position.</dd></dt>
     */
    public void removeItemByName(String name) throws IllegalArgumentException {
            for (int i = 1; i < size() + 1; i++) {
                if (!(menuStore[i].getItemName() == null) &&
                        menuStore[i].getItemName().equals(name)) {
                    removeItem(i);
                    return;
                }
            }
            throw new IllegalArgumentException("Error: The given item name" +
                    " does not exist in this Menu.");
    }

    /**
     * Get the <code>MenuItem</code> at the given position in this
     * <code>Menu</code> object.
     *
     * @param position
     *      The position of the <code>MenuItem</code> to retrieve.
     *
     * <dt>Preconditions:
     *      <dd>This <code>Menu</code> object has been instantiated and
     *      1 <= <code>position</code> <= items_currently_in_list.</dd></dt>
     *
     * @return
     *      The <code>MenuItem</code> at the specified <code>position</code>
     *      in this <code>Menu</code> object.
     *
     * @throws IllegalArgumentException
     *      Indicates that <code>position</code> is not within the valid
     *      range.
     *
     */

    public MenuItem getItem(int position) throws IllegalArgumentException {
        // Checks if the entry position is valid.
        if (isValidPosition(position))
            throw new IllegalArgumentException("Error: The given position is " +
                    "not in the valid range.");
        return menuStore[position];
    }

    /**
     * Get the <code>MenuItem</code> at the given position in this
     * <code>Menu</code> object.
     *
     * @param name
     *      Name of the item to retrieve.
     *
     * <dt>Preconditions:
     *      <dd>This <code>Menu</code> object has been instantiated.</dd></dt>
     *
     * @return
     *      The <code>MenuItem</code> with the specified name.
     *
     * @throws IllegalArgumentException
     *      Indicates that the given item does not exist in this
     *      <code>Menu</code>.
     *
     */
    public MenuItem getItemByName(String name) throws IllegalArgumentException {
        // try {
            for (int i = 1; i < size() + 1; i++) {
                if (!(menuStore[i].getItemName() == null) &&
                        menuStore[i].getItemName().equals(name)) {
                    return menuStore[i];
                }
            }
            throw new IllegalArgumentException("Error: The given item name" +
                    " does not exist in this Menu.");
//        } catch (IllegalArgumentException e) {
//            parent.printMessage(e.getMessage());
//        }
    }

    /**
     * Prints a neatly formatted table of each item in the <code>Menu</code>
     * on its own line with its <code>position</code> number as shown in the
     * sample output.
     *
     * <dt>Preconditions:
     *      <dd>This <code>Menu</code> object has been instantiated.</dd></dt>
     *
     * <dt>PostCondition:
     *      <dd>A neatly formatted table of each <code>MenuItem</code>
     *      in the <code>Menu</code> on its own line with its
     *      <code>position</code> number and has been displayed to the user.
     *      </dd></dt>
     */
    public void printAllItems() {
        parent.printMessage(this.toString());
    }

    /**
     * Gets the String representation of this <code>Menu</code> object,
     * which is a neatly formatted table of each <code>MenuItem</code>
     * in the <code>Menu</code> on its own line with its <code>position</code>
     * number as shown in the sample output.
     *
     * @return
     *      The String representation of this <code>Menu</code> object.
     *
     */
    public String toString() {
        String out = String.format("%-5s%-25s%-75s%s\n", "#", "Name",
                "Description", "Price");

        String dashedLine = "";
        for (int i = 0; i < 120; i++)
            dashedLine += "-";

        out += dashedLine + "\n";

        for (int i = 1; i < size() + 1; i++) {

            if (menuStore[i].getItemName() != null) {
                String name = menuStore[i].getItemName();
                String description = menuStore[i].getItemDescription();
                double price = menuStore[i].getItemPrice();

                out += String.format("%-5d%-25s%-75s$%.2f%n\n", i, name,
                        description, price);
            }
        }

        return out;
    }

    /**
     * Returns a boolean that shows whether if the <code>position</code>
     * entered by the user is in the valid bounds of the <code>Menu</code>
     * and that the list is not empty.
     *
     * @param position
     *      The index that the user desires to access or manipulate.
     *
     * @return
     *      Returns a boolean showing if the <code>position</code> entered
     *      by the user is valid na d list is not empty.
     */
    public boolean isValidPosition(int position) {
        return (position < 1 ||
                position > this.size() || this.size() < 1);
    }
}
