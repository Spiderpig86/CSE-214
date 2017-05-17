
/**
 * The <code>MenuItem</code> class stores data fields
 * for the <code>MenuItem</code> objects added to the Menu
 * class.
 *
 *
 * @author M. D. W.
 *    e-mail: stanley.lim@stsonybrook.edu
 *    Stony Brook ID: 110869393
 **/
public class MenuItem {

    String itemName; // This will hold the menu item's name.
    String itemDescription; // This will hold the menu item's description.
    double itemPrice; // This will hold the item's price.

    /**
     * Creates a new instance of <code>MenuItem</code>
     * @param name
     *      The name of the menu item.
     * @param description
     *      The description of the menu item.
     * @param price
     *      The price of the menu item.
     *
     * <dt>Precondition:
     *      <dd><code>price</code> must be greater than or equal to 0.
     *      </dd></dt>
     *
     *  @exception IllegalArgumentException
     *          Indicates that <code>position</code> is not in the valid
     *          range.
     */
    public MenuItem(String name, String description, double price) {
        try {
            itemName = name;
            itemDescription = description;

            if (price < 1) throw new IllegalArgumentException();
            itemPrice = price;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: The value of the price is negative." +
                    "Unable to add the item to the menu.");
        }
    }

    /**
     * Returns the name of the menu item.
     *
     * @return
     *      Returns the name of the menu item of type string.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the name of the item with the param.
     *
     * @param name
     *      A string passed into the method to replace the current name of the
     *      item.
     */
    public void setItemName(String name) {
        itemName = name;
    }

    /**
     * Returns the description of the menu item.
     *
     * @return
     *      Returns the description of the menu item of type string.
     */
    public String getItemDescription() {
        return itemDescription;
    }


    /**
     * Sets the description of the item with the param.
     *
     * @param description
     *      A string passed into the method to replace the current description
     *      of the item.
     */
    public void setItemDescription(String description) {
        itemDescription = description;
    }

    /**
     * Returns the price of the menu item.
     *
     * @return
     *      Returns the price of the menu item of type double.
     */
    public double getItemPrice() {
        return itemPrice;
    }

    /**
     * Sets the price of the item with the param.
     *
     * @param price
     *      A double passed into the method to replace the current price
     *      of the item.
     *
     * @exception IllegalArgumentException
     *      Indicates that the given parameter has a negative value.
     */
    public void setItemPrice(double price) throws IllegalArgumentException {
        //try {
            if (price > 0)
                itemPrice = price;
            else
                throw new IllegalArgumentException("Cannot set a negative value for the price.");
//        }catch (IllegalArgumentException e) {
//            System.out.println("Error: Unable to modify item price since the value is negative.");
//        }
    }

    /**
     * Clones this <code>MenuItem</code> by returning a new
     * <code>MenuItem</code> object with the same properties
     * as this one.
     *
     * @return item
     *      Returns a copy of this object with the same properties.
     */
    public MenuItem clone() {
        MenuItem item = new MenuItem(getItemName(), getItemDescription(), getItemPrice());
        return item;
    }

    /**
     * Compares this <code>MenuItem</code> to another <code>MenuItem</code> for
     * equality by checking all the properties of both objects.
     *
     * @param obj
     *      An object to which this Menu is compared.
     *
     * @return
     *      If true, this indicates that obj refers to a Menu object with the
     *      same <code>MenuItems</code> in the same order as this
     *      <code>Menu</code>. Otherwise, the return value is false.
     */
    public boolean equals(Object obj) {
        MenuItem item = (MenuItem) obj;

        if (item.getItemName().equals(this.getItemName()) &&
                item.getItemDescription().equals(this.getItemDescription())
                && item.getItemPrice() == this.getItemPrice())
            return true;
        else
            return false;

    }
}
