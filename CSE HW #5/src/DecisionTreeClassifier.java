import java.util.Scanner;

/**
 * Uses the <code>TreeNavigator</code> class to navigate the binary tree
 * structure of <code>TreeNodes</code>. This class displays a list of commands
 * and waits for user input. The program will attempt to match the user entered
 * description with the correct <code>TreeNode</code> that have matching
 * keywords.
 *
 * @author: Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class DecisionTreeClassifier {

    // Global Scanner
    private static Scanner scanner = new Scanner(System.in);
    private static TreeNavigator tree;

    /**
     * The starting point of the program which displays the menu and waits for
     * the user to enter a command.
     *
     * @param args
     *      Standard main method args.
     */
    public static void main(String[] args) {

        // Time to build the menu
        System.out.println("Welcome to the DecisionTree Classifier!");

        displayMainMenu();
        waitForOption();
    }

    // HELPER METHODS

    /**
     * Displays a list commands that the user can enter and their associated
     * functions.
     */
    private static void displayMainMenu() {
        System.out.println("\nMenu:");

        System.out.println(tabText("I) Import a tree from a file"));
        System.out.println(tabText("E) Edit current tree"));
        System.out.println(tabText("C) Classify a Description"));
        System.out.println(tabText("P) Show decision path for a Description"));
        System.out.println(tabText("Q) Quit"));
    }

    /**
     * Adds a tab in front of the string.
     *
     * @param s
     *      The string that needs to be formatted.
     *
     * @return
     *      The formatted string.
     */
    private static String tabText(String s) {
        return "\t" + s;
    }

    /**
     * Waits for the user to enter a command option and executes the associated
     * command.
     */
    private static void waitForOption() {
        System.out.println("Please select an option (main):");

        if (scanner.hasNext()) {
            String option = scanner.nextLine();

            switch (option.toUpperCase()) {
                case "I":
                    tree = TreeNavigator.buildTree(waitForFile());
                    break;
                case "E":
                    displayEditMenu();
                    break;
                case "C":
                    if (checkIfNull()) {
                        System.out.println("Error: Please edit the tree or " +
                                "import a tree before executing this command.");
                        return;
                    }
                    tree.clearObjectPath(); // Clears the path before finding
                    // a new one.
                    System.out.println(classifyHelper());
                    break;
                case "P":
                    if (checkIfNull()) {
                        System.out.println("Error: Please edit the tree or " +
                                "import a tree before executing this command.");
                        return;
                    }
                    tree.clearObjectPath();
                    System.out.println(pathHelper());
                    break;
                case "Q":
                    System.exit(1);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;

            }

            // Reshow main menu
            displayMainMenu();

            // Recursively wait for command
            waitForOption();
        }
    }

    /**
     * Displays the edit menu options to the user.
     */
    private static void displayEditMenu() {
        if (tree == null) {
            tree = new TreeNavigator();
            tree.resetCursor(true);
            System.out.println("Current node keywords: " + tree
                    .removeLastChar(tree.getCursor().getKeyWords()));
        }

        System.out.println("\nPlease select an option:");

        System.out.println(tabText("E) Edit Keywords"));
        System.out.println(tabText("C) Add Children"));
        System.out.println(tabText("D) Delete Children, and Make Leaf Ask " +
                "user for new value for keyword (only one)."));
        System.out.println(tabText("N) Cursor to No Child"));
        System.out.println(tabText("Y) Cursor to Yes Child"));
        System.out.println(tabText("R) Cursor to Root"));
        System.out.println(tabText("P) Cursor to Parent Extra Credit"));
        System.out.println(tabText("M) Main Menu"));

        waitForEditOption();
    }

    /**
     * Waits for the user to input a command for the edit menu.
     */
    private static void waitForEditOption() {

        System.out.println("Select a command (edit):");

        if (scanner.hasNext()) {
            String command = scanner.nextLine();
            switch (command.toUpperCase()) {
                case "E":
                    System.out.println("Please enter keywords for this node, " +
                            "separated by a comma:");
                    String keywords = waitForKeywords();
                    // If at root
                    if (tree == null)
                        tree = new TreeNavigator();

                    tree.editCursor(keywords);
                    System.out.println("Keywords updated to: " + keywords);
                    break;
                case "C": // Adding children
                    if (checkIfNull()) {
                        System.out.println("Error: Please edit the tree or " +
                                "import a tree before executing this command.");
                        return;
                    }

                    System.out.println("Please enter terminal text for the no" +
                            " leaf:");
                    String left = waitForKeywords();
                    System.out.println("Please enter terminal text for the " +
                            "yes leaf:");
                    String right = waitForKeywords();

                    // Add the tree children
                    // TODO: 10/28/2016 Check if the cursor has children.
                    if (tree.getCursor().getLeft() != null || tree.getCursor()
                            .getRight() != null) {
                        System.out.println("Error: Unable to add children." +
                                " There are already existing children.");
                        return;
                    }

                    tree.getCursor().setLeft(new TreeNode(left.split(","),
                            null, null, "leaf"));
                    tree.getCursor().setRight(new TreeNode(right.split(","),
                            null, null, "leaf"));

                    System.out.println("Children are: yes - '" + right + "' " +
                            "and no - '" + left + "'.");
                    break;
                case "D": // Delete all children and make cursor node a leaf
                    if (checkIfNull()) {
                        System.out.println("Error: Please edit the tree or " +
                                "import a tree before executing this command.");
                        return;
                    }
                    tree.deleteChildrenEdit();
                    break;
                case "N":
                    if (checkIfNull()) {
                        System.out.println("Error: Please edit the tree or " +
                                "import a tree before executing this command.");
                        return;
                    }
                    tree.cursorLeft(true);
                    break;
                case "Y":
                    if (checkIfNull()) {
                        System.out.println("Error: Please edit the tree or " +
                                "import a tree before executing this command.");
                        return;
                    }
                    tree.cursorRight(true);
                    break;
                case "R":
                    if (checkIfNull()) {
                        System.out.println("Error: Please edit the tree or " +
                                "import a tree before executing this command.");
                        return;
                    }
                    tree.resetCursor(true);
                    break;
                case "P":
                    if (checkIfNull() || tree.checkAtRoot()) {
                        System.out.println("Error: Please edit the tree or " +
                                "import a tree before executing this command.");
                        return;
                    }
                    tree.cursorToParent();
                    break;
                case "M":
                    return; // Return to main menu and exit method
                default:
                    System.out.println("Invalid command. Please try again");
                    break;
            }
            displayEditMenu();
        }
    }

    /**
     * Waits for the user to input the file name to import.
     *
     * @return
     *      Returns the user entered file name.
     */
    public static String waitForFile() {
        System.out.println("Please enter the name of the file:");
        String res = "";
        if (scanner.hasNext()) {
            res = scanner.nextLine();
        }
        return res;
    }

    /**
     * Waits for the user to input a description for the program to classify.
     *
     * @return
     *      The keyword of the <code>TreeNode</code> object that matches most
     *      with the user description.
     */
    public static String classifyHelper() {
        if (tree == null) { // Make sure the tree has been initialized
            System.out.println("Please build the tree first by importing or " +
                    "adding elements yourself");
            return "";
        }

        // Always reset to root first or else the program will run into an
        // infinite loop.
        tree.resetCursor(false);

        System.out.println("Please enter some text:");
        String res = "";

        if (scanner.hasNext()) {
            res = tree.classify(scanner.nextLine());
        }

        return "Your request is classified as: " + res;
    }

    /**
     * Wait for the user to input a description for the program to classify.
     * The program will return the full path of directions that the program took
     * to reach the final answer.
     *
     * @return
     *      The path the program took to reach the final answer.
     */
    public static String pathHelper() {
        if (tree == null) { // Make sure the tree has been initialized
            System.out.println("Please build the tree first by importing or " +
                    "adding elements yourself");
            return "";
        }

        // Always reset to root first or else the program will run into an
        // infinite loop.
        tree.resetCursor(false);

        System.out.println("Please enter some text:");
        String res = "";

        if (scanner.hasNext()) {
            res = tree.getPath(scanner.nextLine());
        }

        return res;
    }

    /**
     * Waits for the user to enter the keywords for a <code>TreeNode</code>
     * separated by commas.
     *
     * @return
     *      Returns the keywords that the user has entered.
     */
    public static String waitForKeywords() {
        String res = "";
        if (scanner.hasNext()) {
            res = scanner.nextLine();
        }
        return res;
    }

    /**
     * Checks if the tree is currently null.
     *
     * @return
     *      Returns true if the tree is null, false if it isn't.
     */
    public static boolean checkIfNull() {
        if (tree == null)
            return true;

        return false;
    }
}
