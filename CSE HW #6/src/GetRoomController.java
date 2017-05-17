import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The controller class of where all the program functions and commands occur.
 */
public class GetRoomController implements Initializable{

    // Control instance fields
    private Campus campus;
    private Building curBuilding;
    private Classroom curRoom;

    @FXML
    SplitPane splitView;

    @FXML
    TabPane tabView;

    @FXML
    TextArea txtConsole;

    // Time for the buttons

    // Main Menu
    @FXML Button btnAdd;
    @FXML Button btnDelete;
    @FXML Button btnEdit;
    @FXML Button btnFind;
    @FXML Button btnSearch;
    @FXML Button btnListRooms;
    @FXML Button btnListBuildings;
    @FXML Button btnQuit;

    // Edit Menu
    @FXML Button btnEditAdd;
    @FXML Button btnEditDelete;
    @FXML Button btnEditEdit;
    @FXML Button btnEditMain;

    // Search Menu
    @FXML Button btnSearchWhite;
    @FXML Button btnSearchChalk;
    @FXML Button btnSearchAV;
    @FXML Button btnSearchMain;

    // Quit Menu
    @FXML Button btnQuitSave;
    @FXML Button btnQuitNoSave;
    @FXML Button btnQuitMain;

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
        txtConsole.setEditable(false);
        print("Welcome to SBGetARoom, Stony Brook's premium room" +
                " lookup system.");

        // Initialize the campus object to prevent null exceptions
        checkForSave();
    }

    /**
     * Binds the function to a UI control.
     */
    public void hookFunctions() {
        // Main
        btnEdit.setOnAction(e->editBuilding());
        btnAdd.setOnAction(e->addBuilding());
        btnDelete.setOnAction(e->deleteBuilding());
        btnFind.setOnAction(e->findRoomByName());
        btnSearch.setOnAction(e->tabView.getSelectionModel().select(2));
        btnListRooms.setOnAction(e->listAllRooms());
        btnListBuildings.setOnAction(e->listAllRoomsByName());
        btnQuit.setOnAction(e->tabView.getSelectionModel().select(3));

        // Edit Menu
        btnEditAdd.setOnAction(e->addRoom());
        btnEditDelete.setOnAction(e->deleteRoom());
        btnEditEdit.setOnAction(e->editRoom());

        // Search Menu
        btnSearchWhite.setOnAction(e->searchWithWhiteboard());
        btnSearchChalk.setOnAction(e->searchWithChalkboard());
        btnSearchAV.setOnAction(e->searchForAV());

        // Quit Menu
        btnQuitSave.setOnAction(e->saveQuit());
        btnQuitNoSave.setOnAction(e->quitNoSave());

        // Mapping Main Menu Buttons
        btnEditMain.setOnAction(e->tabView.getSelectionModel().select(0));
        btnSearchMain.setOnAction(e->tabView.getSelectionModel().select(0));
        btnQuitMain.setOnAction(e->tabView.getSelectionModel().select(0));
    }

    /**
     * Checks if the save file exists in the working directory. If it exists,
     * it would read the object file and then load the data into the program
     * where the user last left off. If it doesn't exist, a new
     * <code>Campus</code> is constructed and it is empty.
     */
    public void checkForSave() {
        File f = new File("storage.obj");
        //print(f.getAbsolutePath());
        if (f.exists() && !f.isDirectory()) {
            // If the file does exists
            try {
                FileInputStream file = new FileInputStream("storage.obj");
                ObjectInputStream inStream = new ObjectInputStream(file);
                campus = (Campus) inStream.readObject();
                inStream.close();
                print("Save file loaded...");
            } catch (IOException e) {
                print("Error: There was an error trying to find " +
                        "the file. It might not exist.");
                e.printStackTrace();
            } catch (ClassNotFoundException c) {
                print("Error: Mismatched data where the Campus " +
                        "class was not found");
            }
        } else {
            // If the file does not exist
            print("No save file found. Creating an empty campus.");
            // Initialize the empty campus
            campus = new Campus();
        }
    }

    // HELPER METHODS

    public void print(String s) {
        txtConsole.appendText(s + "\n");
    }

    /**
     * Returns the given string with a leading tab character.
     * @param s
     *      The parameter for the string we want to format.
     * @return
     *      Returns the formatted string.
     */
    public String tabText(String s) {
        return "\t" + s;
    }

    /**
     * If edit mode is false, the user will be prompted to keep entering the
     * text. If it the text is not null, the string will be returned. If edit
     * mode is true, the user will be allowed to enter in text that is empty.
     * @param description
     *      The directions for what the user should do for this prompt.
     * @param editMode
     *      Determines whether if blank text is allowed to be entered or not.
     * @return
     *      Returns the response that the user entered.
     */
    public String waitForString(String description, boolean editMode) {
        // Output instructions to the user
        String ret = InputDialogGUI.showDialog(description, editMode);
        if (ret.isEmpty() && !editMode) {
            ret = waitForString(description, editMode);
        }
        return ret;
    }

    /**
     * Returns the integer that the user entered in response to the prompt
     * for the user.
     * @param description
     *      The directions for what the user should do for this prompt.
     * @param editMode
     *      If true, the user is allowed to enter a blank line. If false, the
     *      user cannot.
     * @return
     *      Returns the integer that the user had entered.
     */
    public int waitForInt(String description, boolean editMode) {
        // Output instructions to the user
        print(description);
        String ret = "-1";
        ret = InputDialogGUI.showDialog(description, editMode);
        if ((ret.isEmpty() && !editMode)) {
            ret = Integer.toString(waitForInt(description, editMode));
        } else if (ret.isEmpty() && editMode){
            ret = "-1";
        }

        try {
            return Integer.parseInt(ret);
        } catch (NumberFormatException e) {
            return waitForInt(description, editMode);
        }
    }

    /**
     * Removes the last character within the string.
     *
     * @param s
     *      The string that the user wants to remove the last character from.
     * @return
     *      Returns the formatted string without the last character.
     */
    public String removeTrailingChar(String s) {
        return s.substring(0, s.length() - 1);
    }

    // MAIN MENU METHODS

    /**
     * Adds the building into the <code>Campus</code> object with the name of
     * the building that the user entered.
     */
    public void addBuilding() {
        try {
            String building = waitForString("Please enter a building name:",
                    false);
            campus.addBuilding(building, new Building(building));
            print("Building " + building + " added.");
        } catch (IllegalArgumentException e) {
            print(e.getMessage());
        }
    }

    /**
     * Deletes the building from the <code>Campus</code> object based on the
     * name of the building that the user entered. If the building is not
     * found, an error message is displayed.
     */
    public void deleteBuilding() {
        try {
            String building = waitForString("Please enter a building name:",
                    false);
            campus.removeBuilding(building);
            print("Building " + building + " deleted");
        } catch (IllegalArgumentException e) {
            print(e.getMessage());
        }
    }

    /**
     * Displays the edit menu for the <code>Building</code> object with the
     * name entered by the user. If the building is not found, an error
     * message is displayed.
     */
    public void editBuilding() {
        try {
            String building = waitForString("Please enter a building name:",
                    false);
            // Print that the building is selected
            curBuilding = campus.getBuilding(building);
            print("Building " + building + " selected:");
            tabView.getSelectionModel().select(1);
        } catch (IllegalArgumentException e) {
            // This is when the building is not found
            print(e.getMessage());
        }
    }

    /**
     * Displays information of the room specified by the user. The user
     * enters in the building name and then the room number. If it is not
     * found, an error message is shown.
     */
    public void findRoomByName() {
        try {
            String room = waitForString("Please enter a room name:",
                    false);
            String building = room.split(" ")[0];
            int roomNum = Integer.parseInt(room.split(" ")[1]);

            // Now find the building with building name as key
            curBuilding = campus.getBuilding(building);
            // Now get the room number with roomNum as key
            curRoom = curBuilding.getClassroom(roomNum);

            // Output room details
            print("Room Details:");
            print(tabText("Seats: " + Integer.toString(curRoom
                    .getNumSeats())));
            print(tabText((curRoom.isHasWhiteboard()) ? "Has Whiteboard" :
                    "Doesn't have Whiteboard"));
            print(tabText((curRoom.isHasChalkboard()) ? "Has Chalkboard" :
                    "Doesn't have Chalkboard"));
            // Output list of equipment
            print(tabText("AV Amenities: " + removeTrailingChar
                    (curRoom.getEquipmentString())));
        } catch (IllegalArgumentException e) {
            print("Error: Building/Room not found.");
        }
    }

    /**
     * Lists all the rooms within every <code>Building</code> object within
     * the campus.
     */
    public void listAllRooms() {
        String details = "";
        for (String building: campus.keySet()) {
            curBuilding = campus.getBuilding(building);
            details += building + ": ";
            for (int roomNum: curBuilding.keySet()) {
                details += Integer.toString(roomNum) + ",";
            }
            print(tabText(details));
            details = "";
        }
    }

    /**
     * Lists all the rooms in a <code>Building</code> based on the name of
     * the building the user entered.
     */
    // TODO: 11/16/2016
    // See if the program crashes if the entered building name is not found
    public void listAllRoomsByName() {
        try {
            String buildingName = waitForString("Please enter a building name:",
                    false);

            // Output the Details
            print("Details:");

            String roomNums = "";
            int totalSeats = 0;
            int whiteBoardCount = 0;
            int chalkBoardCount = 0;
            int roomCount = 0;
            ArrayList<String> equipArr = new ArrayList<String>();
            String AVequip = "";

            // Get the rooms
            curBuilding = campus.getBuilding(buildingName);
            // Iterate over the rooms in the building
            for (int roomNum : curBuilding.keySet()) {
                curRoom = curBuilding.get(roomNum);
                // Get the rooms
                roomNums += Integer.toString(roomNum) + ", ";
                roomCount++;
                // Get the seats
                totalSeats += curRoom.getNumSeats();
                // Get whiteboard count
                if (curRoom.isHasWhiteboard())
                    whiteBoardCount++;
                // Get chalkboard count
                if (curRoom.isHasChalkboard())
                    chalkBoardCount++;
                // Get the list of equipment
                for (String equip : curRoom.getAVEquipmentList()) {
                    if (!equipArr.contains(equip))
                        equipArr.add(equip);
                }
            }

            for (String s : equipArr) {
                AVequip += s + ",";
            }

            print(tabText("Rooms: " + roomNums));
            print(tabText("Total Seats: " + Integer.toString
                    (totalSeats)));
            print(tabText(Integer.toString((whiteBoardCount /
                    roomCount) * 100) + "% of rooms have whiteboards"));
            print(tabText(Integer.toString((chalkBoardCount /
                    roomCount) * 100) + "% of rooms have chalkboards"));
            print(tabText("AV Equipment present: ") +
                    removeTrailingChar(AVequip));
        } catch (IllegalArgumentException e) {
            print(e.getMessage());
        }
    }

    // EDIT MENU METHODS

    /**
     * Adds the room into the currently selected <code>Building</code> object
     * . The user is asked to enter the room number, the number of seats, the
     * list of AV equipment, if it has a chalkboard and if it has a whiteboard.
     */
    public void addRoom() {
        try {
            int roomNumber = waitForInt("Please enter room number:", false);
            int seats = waitForInt("Please enter number of seats:", false);
            String equipment = waitForString("Please enter AV Equipment " +
                            "(separated by commas):",
                    false);
            String white = waitForString("Does it have a whiteboard? " +
                    "(Y/n):", false);
            if (!white.toLowerCase().equals("y") && !white.toLowerCase()
                    .equals("n")) {
                white = waitForString("Does it have a whiteboard? " +
                        "(Y/n):", false);
            }
            String chalk = waitForString("Does it have a chalkboard? (Y/n)" +
                            ":",
                    false);
            if (!chalk.toLowerCase().equals("y") && !chalk.toLowerCase()
                    .equals("n")) {
                white = waitForString("Does it have a chalkboard? (Y/n)" +
                                ":",
                        false);
            }
            boolean whiteBoard = white.toUpperCase().equals("Y");
            boolean chalkBoard = chalk.toUpperCase().equals("Y");
            // Change above vars to bool with conditional operator

            // Add the item to the building
            curBuilding.addClassroom(roomNumber, new Classroom(whiteBoard,
                    chalkBoard, seats, Arrays.asList(equipment.split(","))));
            print(String.format("Room %s %d Added.", curBuilding
                    .getName(), roomNumber));

            // Reset to main menu
            tabView.getSelectionModel().select(0);
        } catch (IllegalArgumentException e) {
            print(e.getMessage());
        }
    }

    /**
     * Deletes the room from the user specified <code>Building</code> object.
     */
    public void deleteRoom() {
        try {
            int room = waitForInt("Please enter room number:", false);
            curBuilding.removeClassroom(room);
            print("Room " + Integer.toString(room) + " removed");

            // Reset to main menu
            tabView.getSelectionModel().select(0);
        } catch (IllegalArgumentException e) {
            print("Error: Illegal argument entered or room not " +
                    "found.");
        }
    }

    /**
     * Allows the user to edit the properties of the current
     * <code>Classroom</code> object with the given user information of the
     * number seats, equipment, if it has a chalkboard or if it has a
     * whiteboard. Users are allowed to hit the enter key to keep the same
     * old value for the property.
     */
    public void editRoom() {
        try {
            int room = waitForInt("Please enter room number:", false);
            // Check if room exists
            if (curBuilding.getClassroom(room) == null) {
                print("Error: Room not found.");
                return;
            }
            // Set cursor to the specified room number
            curRoom = curBuilding.getClassroom(room);

            // Display previous data and then ask for entries
            print(String.format("Old number of seats: %d", curRoom
                    .getNumSeats()));
            // Ask for new data. If empty, use old data
            int seats = waitForInt("Please enter the number of seats or press " +
                    "enter to skip:", true);
            if (seats < 0) {
                curRoom.setNumSeats(curRoom.getNumSeats()); // Set it back to
                // old data
            } else {
                // If new num of seats is different
                print("Number of seats updated");
            }

            // Equipment
            String equipment = curRoom.getEquipmentString();
            print(String.format("Old AV Equipment: %s", equipment));
            String newEquip = waitForString("Please enter new AV equipment " +
                            "(separated by commas) or press enter to skip:",
                    true);
            if (newEquip.length() > 0) {
                curRoom.setAVEquipmentList(Arrays.asList(newEquip.split(",")));
                print("AV Equipment updated");
            }

            // Ask for whiteboard
            boolean hasWhite = curRoom.isHasWhiteboard();
            String white = waitForString("Does it have a whiteboard? (Y/n) or " +
                            "press " + "enter to skip:",
                    true).toLowerCase();
            if (white.length() > 0) {
                hasWhite = (white.equals("y"));
            }
            curRoom.setHasWhiteboard(hasWhite);

            // Ask for chalkboard
            boolean hasChalk = curRoom.isHasChalkboard();
            String chalk = waitForString("Does it have a chalkboard? (Y/n) or" +
                            " " +
                            "press " + "enter to skip:",
                    true).toLowerCase();
            if (chalk.length() > 0) {
                hasChalk = (chalk.equals("y"));
            }
            curRoom.setHasChalkboard(hasChalk);

            print(String.format("%s %d updated", curBuilding.getName
                    (), room));

            // Reset to main menu
            tabView.getSelectionModel().select(0);
        } catch (IllegalArgumentException e) {
            print(e.getMessage());
        }
    }

    // SEARCH MENU METHODS

    /**
     * Lists all the rooms from each building and room number that contains
     * whiteboards.
     */
    public void searchWithWhiteboard() {
        try {
            String hasWhiteboard = "";
            // Get all buildings and then get all rooms with whiteboards
            for (String building: campus.keySet()) {
                // The building name is the key
                curBuilding = campus.getBuilding(building);
                // Now iterate over all the rooms
                for (int room: curBuilding.keySet()) {
                    curRoom = curBuilding.getClassroom(room);
                    // Check if it contains a chalkboard
                    if (curRoom.isHasWhiteboard())
                        hasWhiteboard += Integer.toString(room) + ", ";
                }
                // Add the building name and print if there are rooms that have it
                if (hasWhiteboard.length() > 0) {
                    print(building + " has whiteboard:");
                    hasWhiteboard = building + ": " + hasWhiteboard;
                    print(removeTrailingChar(hasWhiteboard));
                    // Reset
                    hasWhiteboard = "";
                }
            }

            // Reset to main menu
            tabView.getSelectionModel().select(0);

        } catch (IllegalArgumentException e) {
            print(e.getMessage());
        }
    }

    /**
     * Lists all the rooms from each building and room number that contains
     * whiteboards.
     */
    public void searchWithChalkboard() {
        try {
            boolean isTrue = false;
            String hasChalkboard = "";
            // Get all buildings and then get all rooms with chalkboards
            for (String building: campus.keySet()) {
                // The building name is the key
                curBuilding = campus.getBuilding(building);
                // Now iterate over all the rooms
                for (int room: curBuilding.keySet()) {
                    curRoom = curBuilding.getClassroom(room);
                    // Check if it contains a chalkboard
                    if (curRoom.isHasChalkboard())
                        hasChalkboard += Integer.toString(room) + ", ";
                }
                // Add the building name and print if there are rooms that have it
                if (hasChalkboard.length() > 0) {
                    print(building + " has chalkboard:");
                    hasChalkboard = building + ": " + hasChalkboard;
                    print(removeTrailingChar(hasChalkboard));
                    // Reset
                    hasChalkboard = "";
                }
            }

            // Reset to main menu
            tabView.getSelectionModel().select(0);
        } catch (IllegalArgumentException e) {
            print(e.getMessage());
        }
    }

    /**
     * Lists all the rooms from each building and room number that contains
     * the AV equipment that the user entered.
     */
    public void searchForAV() {
        try {
            String roomsWithAV = "";

            String keyword = waitForString("Please enter a keyword:", false);

            // Get all buildings and then get all rooms with the AVs
            for (String building: campus.keySet()) {
                // The building name is the key
                curBuilding = campus.getBuilding(building);
                // Now iterate over all the rooms
                for (int room: curBuilding.keySet()) {
                    curRoom = curBuilding.getClassroom(room);
                    // Check if it contains a chalkboard
                    if (curRoom.getAVEquipmentList().contains(keyword))
                        roomsWithAV += Integer.toString(room) + ", ";
                }
                // Add the building name and print if there are rooms that have it
                if (roomsWithAV.length() > 0) {
                    print(building + " has " + keyword + ":");
                    roomsWithAV = building + ": " + roomsWithAV;
                    print(removeTrailingChar(roomsWithAV));
                    // Reset
                    roomsWithAV = "";
                } else {
                    print("No rooms with AV found in " + curBuilding
                            .getName());
                }
            }

            // Reset to main menu
            tabView.getSelectionModel().select(0);
        } catch (IllegalArgumentException e) {
            print(e.getMessage());
        }
    }

    // QUIT MENU METHODS

    /**
     * Saves the class data in the program so it could be loaded later. An
     * exception if the file cannot be saved.
     */
    public void saveQuit() {
        // Save the object data in a binary file in a local directory
        try {
            // Create a new object file for the class data
            FileOutputStream file = new FileOutputStream("storage.obj");
            ObjectOutputStream outStream = new ObjectOutputStream (file);
            // Write the campus data
            outStream.writeObject(campus);
            outStream.close();
            print("Saved data...");
        } catch (IOException e){
            print("Error: Unable to write data to file.");
        }
        print("Quitting program...");
        System.exit(0);
    }

    /**
     * Quits program without saving.
     */
    public void quitNoSave() {
        print("Quitting without saving...");
        System.exit(0);
    }
}
