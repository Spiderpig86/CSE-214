import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The window or form that will allow users to interact with the
 * <code>MenuGUI</code> objects with a GUI.
 */
public class MenuOpsGUI extends JFrame {

    // MenuGUI data fields
    // The main MenuGUI object that the user will manipulate.
    private MenuGUI currentMenu = new MenuGUI(this);

    // The MenuGUI object that will hold MenuItem entries for the order.
    private MenuGUI currentOrder = new MenuGUI(this);


    // GUI elements
    private JTextField commandInput; // The textfield for user input.
    private JTextArea outputPanel; // Where the outputs will be
                                     // displayed.
    private JScrollPane scrollPanel; // Allows user to scroll outputPanel.

    /**
     * Instantiates a new instance of this class and constructs a GUI
     * for user interaction.
     *
     * <dt>Postcondition:
     *      <dd>A window appears when the program is executed and all elements
     *      and variables have been instantiated.</dd></dt>
     */
    public MenuOpsGUI() {
        super("Menu Editor"); // Sets the title of the window.

        // Initialize the textbox for user input.
        commandInput = new JTextField("Enter a command."); // Placeholder text.
        commandInput.addActionListener(
                new ActionListener() { // Anon class to handle enter key event.
                    // Detects when the 'ENTER' key is pressed.
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Gets the text entered from getActionCommand()
                        // and runs the associated function.
                        executeCommand(e.getActionCommand());
                    }
                }
        );

        // Initialize the textarea for the outputs.
        outputPanel = new JTextArea();
        outputPanel.setEditable(false);

        // Initialize the scroll panel
        scrollPanel = new JScrollPane(outputPanel);

        // Add the controls to the window and set up the GUI.
        add(commandInput, BorderLayout.SOUTH);
        add(scrollPanel, BorderLayout.CENTER);
        setSize(800, 600); // Fixed window size.
        setVisible(true); // Makes the form visible.

        displayMenu();

        currentOrder = (MenuGUI) currentMenu.clone();

    }

    /**
     * A method that prints out string <code>m</code> that is appended
     * to <code>commandInput</code>, a JTextField. A method called
     * <code>invokeLater</code> is used to update the control with the text
     * in the textarea.
     *
     * @param m
     *      The message that will be output into the textarea.
     *
     * <dt>Postconditions:
     *      <dd>The specified message is displayed in the textarea
     *      and the command textfield <code>commandInput</code>
     *      is set blank to reset the command.</dd></dt>
     */
    public void printMessage(final String m) {
        SwingUtilities.invokeLater(
                new Runnable() { // New thread for updating the text area.
                    public void run() { // Needed method.
                        outputPanel.append(m + "\n"); // Appends the text.
                        commandInput.setText("");
                    }
                }
        );
    }

    /**
     * Displays a list of commands that could be input.
     */
    public void displayMenu() {
        printMessage("Main Menu \n");
        printMessage(String.format("%3s%s", "A)", "Add Item"));
        printMessage(String.format("%3s%s", "G)", "Get Item"));
        printMessage(String.format("%3s%s", "R)", "Remove Item"));
        printMessage(String.format("%3s%s", "P)", "Print All Items"));
        printMessage(String.format("%3s%s", "S)", "Size"));
        printMessage(String.format("%3s%s", "D)", "Update description"));
        printMessage(String.format("%3s%s", "C)", "Update price"));
        printMessage(String.format("%3s%s", "O)", "Add to order"));
        printMessage(String.format("%3s%s", "I)", "Remove from order"));
        printMessage(String.format("%3s%s", "V)", "View order"));
        printMessage(String.format("%3s%s", "Q)", "Quit"));

        printMessage("");
    }

    public void executeCommand(String option) {

        switch (option.toUpperCase()) { // Converts the char to uppercase
            // in case the user enters a lower
            // letter.
            case "A":
                addItem();
                break;
            case "G":
                getMenuItem();
                break;
            case "R":
                removeItem();
                break;
            case "P":
                printAllItems();
                break;
            case "S":
                returnSize();
                break;
            case "D":
                updateDescription();
                break;
            case "C":
                updatePrice();
                break;
            case "O":
                addToOrder();
                break;
            case "I":
                removeFromOrder();
                break;
            case "V":
                viewOrder();
                break;
            case "Q":
                quitMenu();
                break;
//            case "E":
//                equals();
//                break;
            default:
                printMessage("Command does not exist.");
                break;
        }
    }

    // Helper Methods

    /**
     * This method will wait for the user to input a name for a
     * <code>MenuItem</code> entry.
     *
     * @return
     *      Return the string that the user has input.
     */
    public String waitForName() {
        printMessage("Please enter the name for this item:");

        // Creates a new input dialog for the user to enter the item's name.
        String name = JOptionPane.showInputDialog("Please enter the item's" +
                " name.");
        if (name == null) {
            printMessage("Please enter a valid name.");
            name = waitForName();
        }
        if (name.isEmpty()) {
            printMessage("Please enter a valid name.");
            name = waitForName();
        }
        return name;
    }

    /**
     * This method will wait for the user to input a description
     * for the <code>MenuItem</code> entry.
     *
     * @return
     *      Returns the new description that the user specified.
     *
     */
    public String waitForDescription() {
        printMessage("Please enter a new description for this item:");

        // Creates a new input dialog for the user to enter the item's name.
        String description = JOptionPane.showInputDialog(
                "Please enter the item's description.");
        if (description == null) {
            printMessage("Please enter a valid description.");
            description = waitForDescription();
        }
        if (description.isEmpty()) {
            printMessage("Please enter a valid description.");
             description = waitForDescription();
        }
        return description;
    }

    /**
     * This method will wait for the user to input a new price of type
     * double for the <code>MenuItem</code> entry.
     *
     * @return
     *      Returns the new price that the user specified.
     *
     * @throws NumberFormatException
     *      Throws an exception if the user enters a non numerical input
     *      for the <code>price</code>.
     */
    public double waitForPrice() throws NumberFormatException,
            NullPointerException {
        try {
            printMessage("Please enter a new price for this item:");

            // Creates a new input dialog for the user to enter the item's
            // name.
            double price = Double.parseDouble(JOptionPane.showInputDialog(
                    "Please enter the item's price."));

            if (price < 0)
                throw new NumberFormatException("Error: The value of the " +
                        "price is negative. Unable to add the item to the " +
                        "menu.");

            return price;
        }catch (NumberFormatException e) {
            printMessage("Please enter a valid price.");
            return waitForPrice();
        }catch (NullPointerException f) {
            return 1; // Return default value of 1 for the price if the user
            // decides to hit cancel.
        }
    }

    /**
     * This method will wait for the user to input a new position for the
     * <code>MenuItem</code> entry.
     *
     * @return
     *      Returns the new position that the user has entered.
     *
     * @throws NumberFormatException
     *      Throws an exception if the user enters a non numerical input
     *      for the <code>position</code>.
     */
    public int waitForPosition() throws NumberFormatException {
        try {
            printMessage("Please enter the position of the item you want" +
                    " to use:");

            // Creates a new input dialog for the user to enter the item's
            // name.
            int position = Integer.parseInt(JOptionPane.showInputDialog(
                    "Please enter the item's position."));
            return position;
        }catch (NumberFormatException e) {
            printMessage("Please enter a valid price.");
            return waitForPosition();
        }
    }

    /**
     * This method will output the dashed lines to provide a clearer UI
     * when reading information from the console window.
     */
    public void outputFancyHeading(){
        printMessage(String.format("%-5s%-25s%-75s%s\n", "#",
                "Name", "Description", "Price"));

        String dashedLine = "";
        for (int i = 0; i < 120; i++)
            dashedLine += "-";

        printMessage(dashedLine);
    }

    /**
     * This method will return a formatted String for the <code>MenuItem</code>
     * with its position, name, description, and price listed.
     *
     * @param item
     *      The <code>MenuItem</code> that this function has to format.
     *
     * @param position
     *      The index where the <code>MenuItem</code> is located in the
     *      <code>MenuGUI</code>.
     *
     * <dt>Preconditons:
     *      <dd>The <code>MenuItem</code> has been constructed.</dd></dt>
     *
     * @return
     *      Returns the formatted string of the <code>MenuItem's</code>
     *      attributes.
     *
     */
    public String outputFancyItem(MenuItemGUI item, int position) {
        return String.format("%-5d%-25s%-75s$%.2f\n", position,
                item.getItemName(), item.getItemDescription(),
                item.getItemPrice());
    }

    // Main Functions

    /**
     * Takes in the necessary parameters (name, description, price,
     * and position) to initialize a new <code>MenuItem</code> and adds
     * it to the <code>currentMenu</code> object.
     *
     * <dt>Postconditions:</dt>
     *      <dd>The <code>MenuItem</code> gets added to the
     *      <code>currentMenu</code> object.</dd>
     */
    public void addItem() {
        String name = waitForName();
        String description = waitForDescription();
        double price = waitForPrice();
        int position = waitForPosition();

        MenuItemGUI item = new MenuItemGUI(name, description, price);

        currentMenu.addItem(item, position);
    }

    /**
     * Outputs the requested <code>MenuItem</code> into the console based
     * on the position that the user entered.
     *
     * <dt>Preconditions:
     *      <dd>The <code>MenuItem</code> retrieved from
     *      <code>getItem(position)</code> has been instantiated.</dd></dt>
     *
     * @throws IllegalArgumentException
     *      Means that the position that the user entered is not within
     *      the valid range.
     *
     */
    public void getMenuItem() throws IllegalArgumentException {
        try {
            int position = waitForPosition();

            MenuItemGUI item = currentMenu.getItem(position);
            outputFancyHeading(); // Generates the fancy border in the console.

            printMessage(outputFancyItem(item, position));
        } catch (IllegalArgumentException e) {
            printMessage("Error: The given position is not in the valid" +
                    " range.");
            return;
        }
    }

    /**
     * Removes the item at the specified <code>position</code> that the user
     * specifies.
     *
     * <dt>Preconditions:
     *      <dd>The <code>MenuItem</code> retrieved from
     *      <code>getItem(position)</code> has been instantiated.</dd></dt>
     *
     * @throws IllegalArgumentException
     *      Throws an exception if the given position is outside of the
     *      valid bounds.
     *
     */
    public void removeItem() throws IllegalArgumentException {
        try {
            String name = waitForName();

            MenuItemGUI item = currentMenu.getItemByName(name);
            currentMenu.removeItemByName(name);
            printMessage(item.getItemName() + " has been removed from the " +
                    "menu.");
        } catch (IllegalArgumentException e) {
            printMessage(e.getMessage());
            return;
        }

    }

    /**
     * Outputs a formatted list of all the <code>MenuItems</code> in the
     * <code>MenuGUI</code> into the console window.
     *
     * <dt>Preconditions:
     *      <dd>The <code>MenuGUI</code> object <code>currentMenu</code>
     *      has been initialized.</dd></dt>
     *
     * <dt>PostCondition:
     *      <dd>A neatly formatted table of each <code>MenuItem</code>
     *      in the <code>MenuGUI</code> on its own line with its
     *      <code>position</code> number and has been displayed to the user.
     *      </dd></dt>
     *
     * @throws IllegalArgumentException
     *      Throws an exception if the given position is outside of the
     *      valid bounds.
     */
    public void printAllItems() {
        printMessage("\nCurrent Menu:\n" + currentMenu.toString());
    }

    /**
     * Outputs the number of items within the <code>MenuGUI</code>.
     */
    public void returnSize() {
        printMessage(String.format("There are %d items in the menu.",
                currentMenu.size()));
    }

    /**
     * Updates the description of the <code>MenuItem</code> with the
     * given <code>name</code> input by the user.
     *
     * <dt>Preconditions:
     *      <dd>The <code>MenuGUI</code> object <code>currentMenu</code>
     *      has been initialized.</dd></dt>
     *
     * <dt>Postconditions:
     *      <dd>The <code>MenuItem</code> has been updated with the new
     *      description.</dd></dt>
     *
     * @throws IllegalArgumentException
     *      Throws an exception if the given position is outside of the
     *      valid bounds.
     */
    public void updateDescription() throws IllegalArgumentException {
        try {
            String name = waitForName();
            MenuItemGUI item = currentMenu.getItemByName(name);

            String description = waitForDescription();
            item.setItemDescription(description);

            printMessage("The description for " + item.getItemName() + " has" +
                    " been updated.");
        } catch (IllegalArgumentException e) {
            printMessage("Error: The given name is not in the valid " +
                    "range.");
            return;
        }
    }

    /**
     * Updates the price of the <code>MenuItem</code> with the given
     * <code>name</code> and new price input by the user.
     *
     * <dt>Preconditions:
     *      <dd>The <code>MenuGUI</code> object <code>currentMenu</code> has
     *      been initialized.</dd></dt>
     *
     * <dt>Postconditions:
     *      <dd>The <code>MenuItem</code> has been updated with the new price.
     *      </dd></dt>
     *
     * @throws IllegalArgumentException
     *      Throws an exception if the given price is outside of the
     *      valid bounds.
     */
    public void updatePrice() throws IllegalArgumentException {
        try {
            String name = waitForName();
            MenuItemGUI item = currentMenu.getItemByName(name);

            double price = waitForPrice();

            if (price < 0) {
                throw new IllegalArgumentException("The value of the " +
                        "price is negative. Unable to add the item to " +
                        "the menu.");
            }

            item.setItemPrice(price);

            printMessage("The price for " + item.getItemName() + " has" +
                    " been updated.");
        } catch (IllegalArgumentException e) {
            printMessage(e.getMessage());
            return;
        }
    }

    /**
     * Adds a <code>MenuItem</code> from the given <code>position</code> in
     * the <code>currentMenu</code> object to the <code>currentOrder</code>
     * object.
     *
     * <dt>Precondition:
     *      <dd>The <code>currentMenu</code> and <code>currentOrder</code>
     *      objects have been initialized.</dd></dt>
     *
     * <dt>Postcondition:
     *      <dd>The <code>MenuItem</code> is added to the order.</dd></dt>
     *
     * @throws IllegalArgumentException
     *      Throws an exception if the given position is outside of the
     *      valid bounds.
     */
    public void addToOrder() throws IllegalArgumentException {
        try {
            int position = waitForPosition();
            MenuItemGUI item = currentMenu.getItem(position);
            currentOrder.addItem(item, currentOrder.size() + 1);
            printMessage(item.getItemName() + " has been added to the order.");
        } catch (IllegalArgumentException e) {
            printMessage("Error: The given position is not in the valid " +
                    "range.");
            return;
        }
    }

    /**
     * Removes the <code>MenuItem</code> from the given <code>position</code>
     * in the <code>currentOrder</code> object.
     *
     * <dt>Precondition:
     *      <dd>The <code>currentMenu</code> and <code>currentOrder</code>
     *      objects have been initialized.</dd></dt>
     *
     * <dt>Postcondition:
     *      <dd>The <code>MenuItem</code> is added to the order.</dd></dt>
     *
     * @throws IllegalArgumentException
     *      Throws an exception if the given position is outside of the
     *      valid bounds.
     */
    public void removeFromOrder(){
            int position = waitForPosition();
            MenuItemGUI item = currentOrder.getItem(position);
            currentOrder.removeItem(position);
            printMessage(item.getItemName() + " has been removed from the" +
                    " order.");
    }

    /**
     * Prints the list of all the <code>MenuItems</code> within the
     * <code>currentOrder</code> object in a neatly formatted list.
     */
    public void viewOrder() {
        printMessage("\nCurrent Order:");
        currentOrder.printAllItems();
    }

    /**
     * Exits the program.
     */
    public void quitMenu() {
        System.exit(0);
    }

}
