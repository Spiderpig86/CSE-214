
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The <code>DownloadManagerController</code> class that gets implemented in
 * the <code>DownloadManagerGUI</code> class which controls variable values,
 * UI control functions, and other functions. Implements
 * <code>Initializable</code> to allow an initialize function to allow
 * variable values to created and set and set up the user interface.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */
public class DownloadManagerController implements Initializable {

    @FXML
    TextArea txtPremiumQueue;
    @FXML
    TextArea txtRegularQueue;
    @FXML
    TextArea txtServerLog;
    @FXML
    MenuItem mnuExit;
    @FXML
    TextArea txtSummary;

    // Instance fields for the simulation
    private static int serverNum = 0;
    private static int dlSpeed = 0;
    private static int duration = 0;
    private static double premiumProb = 0.0;
    private static double regularProb = 0.0;

    /**
     * Called to initialize the control that implements
     * <code>Initializable.</code>
     *
     * @param location
     *      Location used to resolve relative paths for the root object, or
     *      null if the location is not known.
     *
     * @param resources
     *      The resources used to localize the root object, or null if
     *      the root object was not localized.
     */
    @FXML
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
       load();
       hookFunctions();
    }

    /**
     * Sets up the user interface with instructions on how to use the program.
     */
    public void load() {
        displayHeading();
        txtSummary.setPromptText("Summary of download simulation.");
        txtServerLog.setEditable(false);
        txtSummary.setEditable(false);
        txtPremiumQueue.setEditable(false);
        txtRegularQueue.setEditable(false);
    }

    /**
     * Binds the function to a UI control.
     */
    public void hookFunctions() {
        mnuExit.setOnAction(e -> exit());
    }

    // GUI COMMANDS

    /**
     * Sets up the variables and environment for the download simulation by
     * asking for number of servers, download speed, duration, premium
     * probability and regular probability. The simulation will not start if
     * any of the fields are invalid and a timer that runs every second is
     * used to control what is output into the program.
     */
    public void getDataForSimulation() {
        serverNum = IntegerDialogGUI.showDialog("Please enter the number of " +
                "servers.");
        dlSpeed = IntegerDialogGUI.showDialog("Please enter the speed of the " +
                "downloads.");
        duration = IntegerDialogGUI.showDialog("Please enter the duration of the" +
                " simulation (seconds).");
        premiumProb = DoubleDialogGUI.showDialog("Please enter the probability " +
                "for premium downloads.");
        regularProb = DoubleDialogGUI.showDialog("Please enter the probability " +
                "for regular downloads.");

        // Checks if the user closed at least one of the dialogs
        if (serverNum == 0 || dlSpeed == 0 || duration == 0 || premiumProb ==
                0 || regularProb == 0) {
            txtServerLog.setText("Error: Unable to start simulation since the" +
                    " " +
                    "user cancelled the request.");
            return;
        }

        txtServerLog.setText("");

        DownloadScheduler dlScheduler = new DownloadScheduler(premiumProb,
                regularProb, serverNum, duration, dlSpeed);

        // This timer will make the output fields update every second with
        // new download information.
        Timer t = new Timer();
        try {
            t.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if (dlScheduler.getCurrentTime() <= duration)
                            startSimulation(dlScheduler);
                        else {
                            // Stop the simulation and output the summary
                                txtSummary.setText(dlScheduler
                                        .generateSummary());
                                t.cancel();
                            return;
                        }
                    }
                }, 0, 1000);

        } catch (java.lang.IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Outputs data to textfields for the download information at each
     * timestep and the items in the queue.
     *
     * @param dl
     *      The <code>DownloadScheduler</code> object that we will get the
     *      data from.
     */
    public void startSimulation(DownloadScheduler dl) {
        txtServerLog.appendText(dl.simulateGUI());
        txtPremiumQueue.setText(dl.generatePremiumQueueData());
        txtRegularQueue.setText(dl.generateRegularQueueData());
    }

    /**
     * Displays the heading of the program.
     */
    public void displayHeading() {
        txtServerLog.appendText("Hello and welcome to the download scheduler" +
                ".\nClick on run in the menu options to get started.\n");
    }

    /**
     * Exits the program.
     */
    public void exit() {
        System.exit(0);
    }
}
