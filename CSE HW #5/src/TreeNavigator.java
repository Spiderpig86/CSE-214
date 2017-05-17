import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This class holds a collection of <code>TreeNode</code> objects and allows
 * users to be able to navigate through the binary tree structure. The class
 * allows users to import files, move around the tree, edit nodes, and classify
 * user entered queries.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */
public class TreeNavigator {

    private static TreeNode root = null;
    private static TreeNode cursor = null;

    private static String unformattedData;

    private static String objectPath = "";

    /**
     * Constructs a <code>TreeNavigator</code> object and takes in a file path
     * to allow users to create a tree with an existing tree structure. The
     * contents of the file get parsed and the tree is built.
     *
     * @param filePath
     *      The file path of the tree the user wants to import.
     */
    public TreeNavigator(String filePath) {
        // Exceptions are required by Java to run the program.
        try {
            unformattedData = readFile(filePath);

            // Now we need to parse the file information and then build a tree.
            parseData();

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        } catch (IOException f) {
            System.out.println("Error: An error occurred with I/O operations.");
        }
    }

    /**
     * Constructs a <code>TreeNavigator</code> object and takes in a file path
     * to allow users to create a tree with an existing tree structure. The
     * contents of the file get parsed and the tree is built.
     *
     * @param filePath
     *      The file path of the tree the user wants to import.
     *
     * @param controller
     *      Parent class for the extra credit.
     */
    public TreeNavigator(String filePath, DecisionTreeController controller) {
        // Exceptions are required by Java to run the program.
        try {
            unformattedData = readFile(filePath, controller);

            // Now we need to parse the file information and then build a tree.
            parseData();

        } catch (FileNotFoundException e) {
            controller.print("Error: File not found.");
        } catch (IOException f) {
            controller.print("Error: An error occurred with I/O operations.");
        }
    }

    /**
     * The default constructor for the <code>TreeNavigator</code> object where
     * a new <code>TreeNode</code> is created as the starting point of the
     * binary tree structure.
     */
    public TreeNavigator() {
        root = new TreeNode(null, null, null, "leaf");
        cursor = root;
        String[] arr = {"Tree is empty"};
        root.setKeywords(arr);
    }

    /**
     * The static method that returns a newly constructed
     * <code>TreeNavigator</code> object with a tree structure based on the
     * information in the file.
     *
     * @param treeFile
     *      The file path of the import file.
     * @return
     *      A newly constructed <code>TreeNavigator</code> object with an
     *      existing tree.
     */
    public static TreeNavigator buildTree(String treeFile) {
        return new TreeNavigator(treeFile);
    }

    /**
     * The static method that returns a newly constructed
     * <code>TreeNavigator</code> object with a tree structure based on the
     * information in the file.
     *
     * @param treeFile
     *      The file path of the import file.
     *
     * @param controller
     *      The parent class for the extra credit.
     *
     * @return
     *      A newly constructed <code>TreeNavigator</code> object with an
     *      existing tree.
     */
    public static TreeNavigator buildTree(String treeFile,
                                          DecisionTreeController controller) {
        return new TreeNavigator(treeFile, controller);
    }

    /**
     * Returns the leaf of the tree that matches most with the text that the
     * user entered into the program.
     *
     * @param text
     *      The query that the user enters.
     *
     * @return
     *      Returns the keywords of the <code>TreeNode</code> that matches
     *      most with the user entered query.
     */
    public String classify(String text) {

        if (text.length() == 0)
            return "Error empty string.";

        // 0 - Left
        // 1 - Right
        int dir;

        // User enters description and we have to guess

        // Get the keywords of the left child if it is not null
        if (containsHelper(text)) {
            dir = 1;
            objectPath += "1" + removeLastChar(cursor.getKeyWords()) + "@";
        } else {
            dir = 0; // Go left if it doesn't contain it
            objectPath += "0" + removeLastChar(cursor.getKeyWords()) + "@";
        }


        if (dir == 0)  // Go left by default as shown in example
            cursorLeft(false);
        else
            cursorRight(false);

        // Break recursion if it is a leaf
        if (cursor.isLeaf())
            return removeLastChar(cursor.getKeyWords());

        // Recursive call?
        // Will classify text in the new cursor.
        return classify(text);
    }

    /**
     * This method classifies the user entered description and then returns
     * the decision path that the program made based on the user's entries.
     *
     * @param description
     *      The description entered by the user of the item they want to find.
     *
     * @return
     *      The path the program took in order to reach its final answer.
     *
     */
    public String getPath(String description) {
        if (description.length() == 0)
            return "Error empty description";

        String path = "Decision path: ";

        // We need to get the pathing of the cursor.
        String answer = classify(description);

        // At this point, the cursor should be at the query.
        String[] directionsArr = objectPath.split("@");

        for(int i = 0; i < directionsArr.length; i++) {
            path += (directionsArr[i].startsWith("0") ? "NOT " : "IS ") +
                    directionsArr[i].substring(1) + ", ";
        }

        // Now we add the final answer
        path += "DECISION: " + answer;
        return path;
    }

    /**
     * Resets the cursor to the head or root of the binary tree structure.
     *
     * @param showMessage
     *      Boolean of whether to show the notification that the root has moved.
     */
    public void resetCursor(boolean showMessage) {
        cursor = root;

        if (showMessage)
            System.out.println("Cursor moved to root, message" +
                " is '" + removeLastChar(cursor.getKeyWords()) + "'");
    }

    /**
     * Resets the cursor to the head or root of the binary tree structure.
     *
     * @param showMessage
     *      Boolean of whether to show the notification that the root has moved.
     *
     * @param controller
     *      The parent class for the Extra Credit.
     */
    public void resetCursor(boolean showMessage, DecisionTreeController
            controller) {
        cursor = root;

        if (showMessage)
            controller.print("Cursor moved to root, message" +
                    " is '" + removeLastChar(cursor.getKeyWords()) + "'");
    }

    /**
     * Moves the cursor to the left child (the No option).
     *
     * @param showMessage
     *      Boolean of whether to show the notification that the root has moved
     *      left.
     */
    public void cursorLeft(boolean showMessage) {
        if (cursor.getLeft() != null) {
            cursor = cursor.getLeft();
            if (showMessage)
                System.out.println("Cursor moved. Cursor is at " +
                    ((cursor.isLeaf()) ? "a leaf" : "a nonleaf") + ", message" +
                    " is '" + removeLastChar(cursor.getKeyWords()) + "'");
        } else {
            System.out.println("Error: Unable to move left since this node is" +
                    " a leaf.");
        }
    }

    /**
     * Moves the cursor to the left child (the No option).
     *
     * @param showMessage
     *      Boolean of whether to show the notification that the root has moved
     *      left.
     *
     * @param controller
     *      The parent class for the GUI Extra Credit.
     */
    public void cursorLeft(boolean showMessage, DecisionTreeController
            controller) {
        if (cursor.getLeft() != null) {
            cursor = cursor.getLeft();
            if (showMessage)
                controller.print("Cursor moved. Cursor is at " +
                        ((cursor.isLeaf()) ? "a leaf" : "a nonleaf") + ", message" +
                        " is '" + removeLastChar(cursor.getKeyWords()) + "'");
        } else {
            controller.print("Error: Unable to move left since this node is" +
                    " a leaf.");
        }
    }

    /**
     * Moves the cursor to the right child (the Yes option),
     *
     * @param showMessage
     *      Boolean of whether to show the notification that the root has moved
     *      right.
     */
    public void cursorRight(boolean showMessage) {
        if (cursor.getRight() != null) {
            cursor = cursor.getRight();
            if (showMessage)
                System.out.println("Cursor moved. Cursor is at " +
                        ((cursor.isLeaf()) ? "a leaf" : "a nonleaf") + ", message" +
                        " is '" + removeLastChar(cursor.getKeyWords()) + "'");
        } else {
            System.out.println("Error: Unable to move right since this node is"
                    + " a leaf.");
        }
    }

    /**
     * Moves the cursor to the right child (the Yes option),
     *
     * @param showMessage
     *      Boolean of whether to show the notification that the root has moved
     *      right.
     *
     * @param controller
     *      The parent class for the GUI Extra Credit.
     */
    public void cursorRight(boolean showMessage, DecisionTreeController
            controller) {
        if (cursor.getRight() != null) {
            cursor = cursor.getRight();
            if (showMessage)
                controller.print("Cursor moved. Cursor is at " +
                        ((cursor.isLeaf()) ? "a leaf" : "a nonleaf") + ", message" +
                        " is '" + removeLastChar(cursor.getKeyWords()) + "'");
        } else {
            controller.print("Error: Unable to move right since this node is"
                    + " a leaf.");
        }
    }

    /**
     * Returns the reference to the <code>TreeNode</code> object located at the
     * cursor position.
     *
     * @return
     *      The cursor reference.
     */
    public TreeNode getCursor() {
        return cursor;
    }

    /**
     * Edits the current keywords located in the <code>TreeNode</code> object
     * referenced by the cursor.
     *
     * @param text
     *      The newly entered keywords by the user separated by commas if there
     *      are more than 1.
     *
     * <dt>Preconditions:
     *      <dd>The cursor should not be null.</dd></dt>
     *
     * <dt>Postconditions:
     *      <dd>The <code>TreeNode</code> referenced at the root
     *      should have the new keywords.</dd></dt>
     */
    public void editCursor(String text) {
        // Split the string at commas into an array and then update the
        // keywords.

        // Remove any extra spaces
        text = text.trim();

        String[] keys = text.split(",");
        cursor.setKeywords(keys);
    }

    /**
     * Reads the contents of the file using a <code>BufferedReader</code>
     * with the given file name. The file should be located at the base
     * directory of the program (where bin, src, etc are located)
     *
     * @param path
     *      The user entered file name.
     *
     * @return
     *      The contents of the file so it could be parsed.
     *
     * @throws IOException
     *      Exception is thrown if there are I/O operation errors such as if
     *      the file is not found.
     */
    public String readFile(String path) throws IOException {
        String fileContent = "";
        try {
            BufferedReader stdin = new BufferedReader(
                    new FileReader(path));

            System.out.println("Tree Imported");

            String stuff;
            while ((stuff = stdin.readLine()) != null) {
                fileContent += stuff + "\n";
            }
        } catch (IOException f) {
            System.out.println("Error: An error occurred with I/O operations.");
        }

        return fileContent;
    }

    /**
     * Reads the contents of the file using a <code>BufferedReader</code>
     * with the given file name. The file should be located at the base
     * directory of the program (where bin, src, etc are located)
     *
     * @param path
     *      The user entered file name.
     *
     * @return
     *      The contents of the file so it could be parsed.
     *
     * @throws IOException
     *      Exception is thrown if there are I/O operation errors such as if
     *      the file is not found.
     */
    public String readFile(String path, DecisionTreeController controller)
            throws IOException {
        String fileContent = "";
        try {
            BufferedReader stdin = new BufferedReader(
                    new FileReader(path));

            controller.print("Tree Imported");

            String stuff;
            while ((stuff = stdin.readLine()) != null) {
                fileContent += stuff + "\n";
            }
        } catch (IOException f) {
            controller.print("Error: An error occurred with I/O operations.");
        }

        return fileContent;
    }

    /**
     * Initializes a <code>BufferedReader</code> to parse the code line by line.
     *
     * @throws IOException
     *      Exception is thrown if there are I/O operation errors such as if
     *      the file is not found.
     *
     * <dt>Postconditions:
     *      <dd>The binary tree structure has been constructed.</dd></dt>
     */
    public void parseData() throws IOException {
        // Parse data and build tree and connect nodes.
        // 0 - Left
        // 1 - Right
        // Using BufferedReader to read each line.
        BufferedReader br = new BufferedReader(new StringReader
                (unformattedData));
        String temp; // temp string when parsing
        while ((temp = br.readLine()) != null) {

            // Split the data with semicolons.
            String[] formattedEntry = temp.split(";");

            // After splitting, tree directory is formattedEntry[0], keywords
            // are formattedEntry[1], leaf bool formattedEntry[2]

            // Then beginning string with the number of  '-'
            // Total number of levels is the number of '-' + 1
            char[] dir = formattedEntry[0].replace("-", "").toCharArray(); // String
            // with the file paths

            // Split the middle string with commas to add the keywords to the
            // list in the Tree Node
            String[] keyWordsArr = formattedEntry[1].split(",");

            if (root == null) { // Tree is empty
                root = new TreeNode(keyWordsArr, null, null, formattedEntry[2]);
                cursor = root;
            } else {
                for (int i = 1; i < dir.length - 1; i++) {
                    if (Character.toString(dir[i]).equals("0")) { // Go left
                        // TODO Detect if the next elements are null in the
                        // future

                        cursor = cursor.getLeft();

                    } else { // Go right

                        cursor = cursor.getRight();
                    }

                }

                // Now check the last direction for the command and add
                // the element
                if (Character.toString(dir[dir.length - 1]).equals("0"))
                // dir.length - 1 is the index of the last element
                    cursor.setLeft(new TreeNode(keyWordsArr, null, null,
                            formattedEntry[2]));
                else if ((Character.toString(dir[dir.length - 1]).equals("1")))
                    cursor.setRight(new TreeNode(keyWordsArr, null, null,
                            formattedEntry[2]));

                // Reset the cursor
                resetCursor(false);
            }
        }
    }

    /**
     * Iterates over the keywords within a <code>TreeNode</code> object
     * and matches it with the description that the user provides.
     *
     * @param match
     *      The user entered description to be matched against the node.
     *
     * @return
     *      Returns true if the node matches, false if it doesn't/
     */
    private boolean containsHelper(String match) {
            for (String s : cursor.getKeywordsList())
                if (match.toLowerCase().contains(s.toLowerCase()))
                    return true;
            return false;
    }

    /**
     * Resets the objectPath variable.
     */
    public void clearObjectPath() {
        objectPath = "";
    }

    /**
     * Deletes all the children of the current <code>TreeNode</code> object
     * and makes the node a leaf. Then, the user is asked to input new keywords
     * for the leaf.
     *
     * <dt>Postconditions:
     *      <dd>The children should have been deleted and the node
     *      is now a leaf.</dd></dt>
     */
    public void deleteChildrenEdit() {
        // Set the children to null
        cursor.setLeft(null);
        cursor.setRight(null);
        // Cursor should automatically know that it's a leaf now.

        // Now we ask the user to change the text of this node
        System.out.println("Please enter the new keywords for this node:");
        String keywords = waitForKeywords();
        if (keywords != null)
            editCursor(keywords);

    }

    /**
     * Deletes all the children of the current <code>TreeNode</code> object
     * and makes the node a leaf. Then, the user is asked to input new keywords
     * for the leaf.
     *
     * <dt>Postconditions:
     *      <dd>The children should have been deleted and the node
     *      is now a leaf.</dd></dt>
     */
    public void deleteChildrenEditGUI(DecisionTreeController controller) {
        // Set the children to null
        cursor.setLeft(null);
        cursor.setRight(null);
        // Cursor should automatically know that it's a leaf now.

        // Now we ask the user to change the text of this node
        controller.print("Please enter the new keywords for this node:");
        String keywords = waitForKeywordsGUI();
        if (keywords != null) {
            editCursor(keywords);
            controller.print("Keywords updated to: " + keywords);
        } else {
            controller.print("Error: Keywords empty, unable to update " +
                    "keywords.");
        }

    }

    /**
     * Waits for the user to input keywords for the node.
     *
     * @return
     *      Returns the list of words separated by commas.
     */
    public String waitForKeywords() {
        String res = "";
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            res = scanner.nextLine();
        }
        return res;
    }

    /**
     * Waits for the user to input keywords for the node.
     *
     * @return
     *      Returns the list of words separated by commas.
     */
    public String waitForKeywordsGUI() {
        return InputDialogGUI.showDialog("Please enter keywords separated by " +
                "commas.");
    }

    /**
     * Removes the trailing comma from the string.
     *
     * @param s
     *      The string that needs to be formatted.
     *
     * @return
     *      The formatted string.
     */
    public String removeLastChar(String s) {
        return s.substring(0, s.length() - 1);
    }

    /**
     * Moves the cursor to the parent node through recursion by comparing
     * keywords. This is the helper method.
     *
     * <dt>Postcondition:
     *      <dd>The cursor should be pointing at the parent node.</dd></dt>
     */
    public void cursorToParent() {
        if (cursor == root) {
            System.out.println("Unable to move to parent, cursor is at " +
                    "highest possible level.");
            return;
        }
        cursor = cursorToParent(cursor.getKeyWords(), this.root, null);
        System.out.println("Cursor moved to parent: " + removeLastChar(cursor
                .getKeyWords()));
    }

    /**
     * Moves the cursor to the parent node through recursion by comparing
     * keywords.
     *
     * @param controller
     *      The parent class of the program for Extra Credit.
     *
     * <dt>Postcondition:
     *      <dd>The cursor should be pointing at the parent node.</dd></dt>
     */
    public void cursorToParent(DecisionTreeController controller) {
        if (cursor == root) {
            controller.print("Unable to move to parent, cursor is at " +
                    "highest possible level.");
            return;
        }
        cursor = cursorToParent(cursor.getKeyWords(), this.root, null);
        controller.print("Cursor moved to parent: " + removeLastChar(cursor
                .getKeyWords()));
    }

    /**
     * Moves the cursor to the parent node through recursion by comparing
     * keywords.
     *
     * <dt>Postcondition:
     *      <dd>The cursor should be pointing at the parent node.</dd></dt>
     */
    public TreeNode cursorToParent(String s, TreeNode temp, TreeNode parent) {
        // Reached the end of the tree to a leaf, return null since children
        // have empty references.
        if (temp == null) {
            return null;
        } else if (!temp.getKeyWords().equals(s)) {
            // Reassign parent since the string of the current node did not
            // match the one in the parent node. This method will return what
            // the parent last was.
            parent = cursorToParent(s, temp.getLeft(), temp);

            // Check the right children
            if (parent == null)
                parent = cursorToParent(s, temp.getRight(), temp);
        }

        // Return the parent, which is set to the node before the node with
        // the matching keyword (or just this node) was found.
        return parent;
    }

    /**
     * Checks if the cursor is at the root.
     *
     * @return
     *      Returns a boolean representing if the cursor is at the root.
     */
    public boolean checkAtRoot() {
        return (getCursor() == root);
    }
}
