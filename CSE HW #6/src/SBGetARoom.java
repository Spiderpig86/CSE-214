import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This program is designed to help map out different <code>Classroom</code>
 * objects and <code>Building</code> objects within the <code>Campus</code>
 * with that allows the user to add, edit, and delete buildings and
 * classrooms. This also allows the user to save the user data in the program
 * in an object file with serialization.
 *
 * @author: Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class SBGetARoom {

    private static Campus campus;
    private static Building curBuilding;
    private static Classroom curRoom;

    /**
     * The starting point of the program which displays the menu and waits for
     * the user to enter a command.
     *
     * @param args
     *      Standard main method args.
     */
    public static void main(String[] args) {
        displayHeading();
        displayMenu();
        waitForMainCommand();
    }

    // HELPER METHODS

    /**
     * Prints the welcome message for the program and checks if the save file
     * exists.
     */
    public static void displayHeading() {
        System.out.println("Welcome to SBGetARoom, Stony Brook's premium room" +
                " lookup system.");

        // Initialize the campus object to prevent null exceptions
        checkForSave();
    }

    /**
     * Checks if the save file exists in the working directory. If it exists,
     * it would read the object file and then load the data into the program
     * where the user last left off. If it doesn't exist, a new
     * <code>Campus</code> is constructed and it is empty.
     */
    public static void checkForSave() {
        File f = new File("storage.obj");
        //System.out.println(f.getAbsolutePath());
        if (f.exists() && !f.isDirectory()) {
            // If the file does exists
            try {
                FileInputStream file = new FileInputStream("storage.obj");
                ObjectInputStream inStream = new ObjectInputStream(file);
                campus = (Campus) inStream.readObject();
                inStream.close();
                System.out.println("Save file loaded...");
            } catch (IOException e) {
                System.out.println("Error: There was an error trying to find " +
                        "the file. It might not exist.");
                e.printStackTrace();
            } catch (ClassNotFoundException c) {
                System.out.println("Error: Mismatched data where the Campus " +
                        "class was not found");
            }
        } else {
            // If the file does not exist
            System.out.println("No save file found. Creating an empty campus.");
            // Initialize the empty campus
            campus = new Campus();
        }
    }

    /**
     * Displays the main menu with a bunch of commands.
     */
    public static void displayMenu() {
        System.out.println("Menu:");
        System.out.println(tabText("A) Add a building"));
        System.out.println(tabText("D) Delete a building"));
        System.out.println(tabText("E) Edit a building"));
        System.out.println(tabText("F) Find a room"));
        System.out.println(tabText("S) Search for rooms"));
        System.out.println(tabText("C) List all rooms on Campus"));
        System.out.println(tabText("L) List rooms by name"));
        System.out.println(tabText("Q) Quit"));
        System.out.println("Please select an option (main):");
    }

    /**
     * Displays the edit menu.
     */
    public static void displayEditMenu() {
        System.out.println("Options:");
        System.out.println(tabText("A) Add room"));
        System.out.println(tabText("D) Delete room"));
        System.out.println(tabText("E) Edit room"));
        System.out.println("Please select an option (edit):");
    }

    /**
     * Displays the search engine.
     */
    public static void displaySearchMenu() {
        System.out.println("Options:");
        System.out.println(tabText("W) Whiteboard"));
        System.out.println(tabText("B) Blackboard"));
        System.out.println(tabText("A) AV Keyword"));
        System.out.println("Please select an option (search):");
    }

    /**
     * Displays the quit menu.
     */
    public static void displayQuitMenu() {
        System.out.println("Options");
        System.out.println(tabText("S) Save"));
        System.out.println(tabText("D) Don't Save"));
        System.out.println("Please select an option (quit):");
    }

    /**
     * Waits for the user to enter a command and executes the associated
     * function.
     */
    public static void waitForMainCommand() {

        Scanner scan = new Scanner(System.in);

        if (scan.hasNext()) {
            String command = scan.nextLine();

            switch (command.toUpperCase()) {
                case "A":
                    addBuilding();
                    break;
                case "D":
                    deleteBuilding();
                    break;
                case "E":
                    editBuilding();
                    break;
                case "F":
                    findRoomByName();
                    break;
                case "S":
                    waitForSearchCommand();
                    break;
                case "C":
                    listAllRooms();
                    break;
                case "L":
                    listAllRoomsByName();
                    break;
                case "Q":
                    waitForQuitCommand();
                    break;
                default:
                    System.out.println("Invalid command, please try again.");
                    break;
            }
            // Reshow the main menu
            displayMenu();

            // Wait for user input again
            waitForMainCommand();
        }
    }

    /**
     * Waits for the user to enter a command from the edit menu and then
     * executes the associated function.
     */
    public static void waitForEditCommand() {
        displayEditMenu();
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            String command = scanner.nextLine();

            switch (command.toUpperCase()) {
                case "A":
                    addRoom();
                    break;
                case "D":
                    deleteRoom();
                    break;
                case "E":
                    editRoom();
                    break;
                default:
                    System.out.println("Invalid command, please try again.");
                    break;
            }
        }
    }

    /**
     * Waits for the user to enter a command for the search menu and executes
     * the associated function.
     */
    public static void waitForSearchCommand() {
        displaySearchMenu();
        Scanner scan = new Scanner(System.in);

        if (scan.hasNext()) {
            String command = scan.nextLine();

            switch (command.toUpperCase()) {
                case "B":
                    searchWithChalkboard();
                    break;
                case "W":
                    searchWithWhiteboard();
                    break;
                case "A":
                    searchForAV();
                    break;
                default:
                    System.out.println("Invalid command, please try again.");
                    break;
            }
        }

    }

    /**
     * Waits for the user to enter a command associated with the quit menu
     * command.
     */
    public static void waitForQuitCommand() {
        displayQuitMenu();
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            String command = scanner.nextLine();

            switch (command.toUpperCase()) {
                case "S":
                    saveQuit();
                    break;
                case "D":
                    quitNoSave();
                    break;
                default:
                    System.out.println("Invalid command, please try again.");
                    break;
            }
        }
    }

    /**
     * Returns the given string with a leading tab character.
     * @param s
     *      The parameter for the string we want to format.
     * @return
     *      Returns the formatted string.
     */
    public static String tabText(String s) {
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
    public static String waitForString(String description, boolean editMode) {
        // Output instructions to the user
        System.out.println(description);
        Scanner scan = new Scanner(System.in);
        String ret = scan.nextLine();
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
    public static int waitForInt(String description, boolean editMode) {
        // Output instructions to the user
        System.out.println(description);
        Scanner scanner = new Scanner(System.in);
        String ret = "-1";
        ret = scanner.nextLine();
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
    public static String removeTrailingChar(String s) {
        return s.substring(0, s.length() - 2);
    }

    // MAIN MENU METHODS

    /**
     * Adds the building into the <code>Campus</code> object with the name of
     * the building that the user entered.
     */
    public static void addBuilding() {
        try {
            String building = waitForString("Please enter a building name:",
                    false);
            campus.addBuilding(building, new Building(building));
            System.out.println("Building " + building + " added.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes the building from the <code>Campus</code> object based on the
     * name of the building that the user entered. If the building is not
     * found, an error message is displayed.
     */
    public static void deleteBuilding() {
        try {
            String building = waitForString("Please enter a building name:",
                    false);
            campus.removeBuilding(building);
            System.out.println("Building " + building + " deleted");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays the edit menu for the <code>Building</code> object with the
     * name entered by the user. If the building is not found, an error
     * message is displayed.
     */
    public static void editBuilding() {
        try {
            String building = waitForString("Please enter a building name:",
                    false);
            // Print that the building is selected
            curBuilding = campus.getBuilding(building);
            System.out.println("Building " + building + " selected:");

            // Show edit options
            waitForEditCommand();

        } catch (IllegalArgumentException e) {
            // This is when the building is not found
            System.out.println(e.getMessage());
        }
        // Show edit options
        //waitForEditCommand();
    }

    /**
     * Displays information of the room specified by the user. The user
     * enters in the building name and then the room number. If it is not
     * found, an error message is shown.
     */
    public static void findRoomByName() {
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
            System.out.println("Room Details:");
            System.out.println(tabText("Seats: " + Integer.toString(curRoom
                    .getNumSeats())));
            System.out.println(tabText((curRoom.isHasWhiteboard()) ? "Has Whiteboard" :
                    "Doesn't have Whiteboard"));
            System.out.println(tabText((curRoom.isHasChalkboard()) ? "Has Chalkboard" :
                    "Doesn't have Chalkboard"));
            // Output list of equipment
            System.out.println(tabText("AV Amenities: " + removeTrailingChar
                    (curRoom.getEquipmentString())));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Building/Room not found.");
        }
    }

    /**
     * Lists all the rooms within every <code>Building</code> object within
     * the campus.
     */
    public static void listAllRooms() {
        String details = "";
        for (String building: campus.keySet()) {
            curBuilding = campus.getBuilding(building);
            details += building + ": ";
            for (int roomNum: curBuilding.keySet()) {
                details += Integer.toString(roomNum) + ",";
            }
            System.out.println(tabText(details));
            details = "";
        }
    }

    /**
     * Lists all the rooms in a <code>Building</code> based on the name of
     * the building the user entered.
     */
    // TODO: 11/16/2016
    // See if the program crashes if the entered building name is not found
    public static void listAllRoomsByName() {
        try {
            String buildingName = waitForString("Please enter a building name:",
                    false);

            // Output the Details
            System.out.println("Details:");

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

            System.out.println(tabText("Rooms: " + roomNums));
            System.out.println(tabText("Total Seats: " + Integer.toString
                    (totalSeats)));
            System.out.println(tabText(Integer.toString((whiteBoardCount /
                    roomCount) * 100) + "% of rooms have whiteboards"));
            System.out.println(tabText(Integer.toString((chalkBoardCount /
                    roomCount) * 100) + "% of rooms have chalkboards"));
            System.out.println(tabText("AV Equipment present: ") + AVequip);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // EDIT MENU METHODS

    /**
     * Adds the room into the currently selected <code>Building</code> object
     * . The user is asked to enter the room number, the number of seats, the
     * list of AV equipment, if it has a chalkboard and if it has a whiteboard.
     */
    public static void addRoom() {
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
            System.out.println(String.format("Room %s %d Added.", curBuilding
                    .getName(), roomNumber));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes the room from the user specified <code>Building</code> object.
     */
    public static void deleteRoom() {
        try {
            int room = waitForInt("Please enter room number:", false);
            curBuilding.removeClassroom(room);
            System.out.println("Room " + Integer.toString(room) + " removed");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Illegal argument entered or room not " +
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
    public static void editRoom() {
        try {
            int room = waitForInt("Please enter room number:", false);
            // Check if room exists
            if (curBuilding.getClassroom(room) == null) {
                System.out.println("Error: Room not found.");
                return;
            }
            // Set cursor to the specified room number
            curRoom = curBuilding.getClassroom(room);

            // Display previous data and then ask for entries
            System.out.println(String.format("Old number of seats: %d", curRoom
                    .getNumSeats()));
            // Ask for new data. If empty, use old data
            int seats = waitForInt("Please enter the number of seats or press " +
                    "enter to skip:", true);
            if (seats < 0) {
                curRoom.setNumSeats(curRoom.getNumSeats()); // Set it back to
                // old data
            } else {
                // If new num of seats is different
                System.out.println("Number of seats updated");
            }

            // Equipment
            String equipment = curRoom.getEquipmentString();
            System.out.println(String.format("Old AV Equipment: %s", equipment));
            String newEquip = waitForString("Please enter new AV equipment " +
                            "(separated by commas) or press enter to skip:",
                    true);
            if (newEquip.length() > 0) {
                curRoom.setAVEquipmentList(Arrays.asList(newEquip.split(",")));
                System.out.println("AV Equipment updated");
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

            System.out.println(String.format("%s %d updated", curBuilding.getName
                    (), room));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // SEARCH MENU METHODS

    /**
     * Lists all the rooms from each building and room number that contains
     * whiteboards.
     */
    public static void searchWithWhiteboard() {
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
                System.out.println("Rooms with whiteboard:");
                hasWhiteboard = building + ": " + hasWhiteboard;
                System.out.println(removeTrailingChar(hasWhiteboard));
                // Reset
                hasWhiteboard = "";
            } else {
                System.out.println("No rooms with whiteboard found in " +
                        curBuilding.getName());
            }
        }
    }

    /**
     * Lists all the rooms from each building and room number that contains
     * whiteboards.
     */
    public static void searchWithChalkboard() {
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
                System.out.println("Rooms with chalkboard:");
                hasChalkboard = building + ": " + hasChalkboard;
                System.out.println(removeTrailingChar(hasChalkboard));
                // Reset
                hasChalkboard = "";
            } else {
                System.out.println("No rooms with chalkboard found in " +
                        curBuilding.getName());
            }
        }
    }

    /**
     * Lists all the rooms from each building and room number that contains
     * the AV equipment that the user entered.
     */
    public static void searchForAV() {
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
                System.out.println("Rooms with AV:");
                roomsWithAV = building + ": " + roomsWithAV;
                System.out.println(removeTrailingChar(roomsWithAV));
                // Reset
                roomsWithAV = "";
            } else {
                System.out.println("No rooms with AV found in " + curBuilding
                        .getName());
            }
        }
    }

    // QUIT MENU METHODS

    /**
     * Saves the class data in the program so it could be loaded later. An
     * exception if the file cannot be saved.
     */
    public static void saveQuit() {
        // Save the object data in a binary file in a local directory
        try {
            // Create a new object file for the class data
            FileOutputStream file = new FileOutputStream("storage.obj");
            ObjectOutputStream outStream = new ObjectOutputStream (file);
            // Write the campus data
            outStream.writeObject(campus);
            outStream.close();
            System.out.println("Saved data...");
        } catch (IOException e){
            System.out.println("Error: Unable to write data to file.");
        }
        System.out.println("Quitting program...");
        System.exit(0);
    }

    /**
     * Quits program without saving.
     */
    public static void quitNoSave() {
        System.out.println("Quitting without saving...");
        System.exit(0);
    }
}
