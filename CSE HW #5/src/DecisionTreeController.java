import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

import java.util.Scanner;

/**
 * Created by Stan on 10/31/2016.
 */
public class DecisionTreeController implements Initializable{

    private TreeNavigator tree;

    // Control instance fields

    @FXML
    SplitPane splitView;

    @FXML
    TabPane tabView;

    @FXML
    TextArea txtConsole;

    // Main commands
    @FXML private Button btnMainImport;
    @FXML private Button btnMainEdit;
    @FXML private Button btnMainClassify;
    @FXML private Button btnMainPath;
    @FXML private Button btnMainQuit;

    // Edit commands
    @FXML private Button btnEditKey;
    @FXML private Button btnEditAdd;
    @FXML private Button btnEditDelete;
    @FXML private Button btnEditNo;
    @FXML private Button btnEditYes;
    @FXML private Button btnEditRoot;
    @FXML private Button btnEditParent;
    @FXML private Button btnEditMain;

    /**
     * Called to initialize the control that implements
     * <code>Initializable.</code>
     *
     * @param location
     *      Location used to resolve relative paths for the root object, or
     *      null if the location is not known.
     *
     * @param resource
     *      The resources used to localize the root object, or null if
     *      the root object was not localized.
     */
    @FXML
    public void initialize(java.net.URL location, java.util.ResourceBundle
            resource) {
        load();
        hookFunctions();
    }

    /**
     * Sets up the user interface with instructions on how to use the program.
     */
    public void load() {
        // Time to build the menu
        print("Welcome to the DecisionTree Classifier!\nPlease " +
                "press a button to begin.");

        txtConsole.setEditable(false);
    }

    /**
     * Binds the function to a UI control.
     */
    public void hookFunctions() {
        btnMainImport.setOnAction(e-> tree = tree.buildTree(waitForFile(),
                this));
        btnMainEdit.setOnAction(e-> {
            if (tree == null)
                tree = new TreeNavigator();

            tree.resetCursor(false);
            print("Cursor moved to root");
            print("Current node keywords: " + tree
                    .removeLastChar(tree.getCursor().getKeyWords()));

            tabView.getSelectionModel().select(1);
        });
        btnMainClassify.setOnAction(e-> print(classifyHelper()));
        btnMainPath.setOnAction(e-> print(pathHelper()));
        btnMainQuit.setOnAction(e-> System.exit(1));

        btnEditKey.setOnAction(e-> {
            // If at root

            String keys = waitForKeywords();
            if (keys.length() < 1) {
                print("Unable to edit keywords, input was empty.");
                return;
            }
            tree.editCursor(keys);
            print("Keywords updated to: " + keys);
        });
        btnEditAdd.setOnAction(e-> addChildrenHelper());
        btnEditDelete.setOnAction(e-> deleteChildrenHelper());
        btnEditNo.setOnAction(e-> tree.cursorLeft(true, this));
        btnEditYes.setOnAction(e-> tree.cursorRight(true, this));
        btnEditRoot.setOnAction(e-> tree.resetCursor(true, this));
        btnEditParent.setOnAction(e-> tree.cursorToParent(this));
        btnEditMain.setOnAction(e-> tabView.getSelectionModel().select(0));
    }

    // HELPER METHODS

    public void print(String s) {
        txtConsole.appendText(s + "\n");
    }

    /**
     * Waits for the user to input the file name to import.
     *
     * @return
     *      Returns the user entered file name.
     */
    public String waitForFile() {
        print("Please enter the name of the file:");
        return InputDialogGUI.showDialog("Please enter the name of the file.");
    }

    /**
     * Waits for the user to input a description for the program to classify.
     *
     * @return
     *      The keyword of the <code>TreeNode</code> object that matches most
     *      with the user description.
     */
    public String classifyHelper() {
        if (tree == null) { // Make sure the tree has been initialized
            print("Please build the tree first by importing or " +
                    "adding elements yourself");
            return "";
        }

        // Always reset to root first or else the program will run into an
        // infinite loop.
        tree.resetCursor(false);

        print("Please enter some text:");
        String res = "";

        res = tree.classify(InputDialogGUI.showDialog("Please enter a " +
                "description."));

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
    public String pathHelper() {
        if (tree == null) { // Make sure the tree has been initialized
            System.out.println("Please build the tree first by importing or " +
                    "adding elements yourself");
            return "";
        }

        // Always reset to root first or else the program will run into an
        // infinite loop.
        tree.resetCursor(false);

        print("Please enter some text:");

        return tree.getPath(InputDialogGUI.showDialog("Please enter a " +
                "description."));
    }

    /**
     * Waits for the user to enter the keywords for a <code>TreeNode</code>
     * separated by commas.
     *
     * @return
     *      Returns the keywords that the user has entered.
     */
    public String waitForKeywords() {
        return InputDialogGUI.showDialog("Please enter keywords separated by " +
                "commas.");
    }

    /**
     * Checks if the tree is not instantiated.
     *
     * @return
     *      Return true if the tree is null and false if it isn't.
     */
    public boolean checkIfNull() {
        if (tree == null)
            return true;

        return false;
    }

    /**
     * Helper method to add children to the current <code>TreeNode</code>.
     */
    public void addChildrenHelper() {
        if (checkIfNull()) {
            print("Error: Please edit the tree or " +
                    "import a tree before executing this command.");
            return;
        }

        print("Please enter terminal text for the no" +
                " leaf:");
        String left = waitForKeywords();
        print("Please enter terminal text for the " +
                "yes leaf:");
        String right = waitForKeywords();

        // Add the tree children
        // TODO: 10/28/2016 Check if the cursor has children.
        if (tree.getCursor().getLeft() != null || tree.getCursor()
                .getRight() != null) {
            print("Error: Unable to add children." +
                    " There are already existing children.");
            return;
        }

        tree.getCursor().setLeft(new TreeNode(left.split(","),
                null, null, "leaf"));
        tree.getCursor().setRight(new TreeNode(right.split(","),
                null, null, "leaf"));

        print("Children are: yes - '" + right + "' " +
                "and no - '" + left + "'.");
    }

    /**
     * Helper method to delete children.
     */
    public void deleteChildrenHelper() {
        if (checkIfNull()) {
            print("Error: Please edit the tree or " +
                    "import a tree before executing this command.");
            return;
        }
        tree.deleteChildrenEditGUI(this);
    }

}
