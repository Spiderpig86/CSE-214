import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

/**
 * This is the UI layout of the class which creates the window.
 */
public class DecisionTreeGUI extends Application {

    // GUI
    @FXML
    SplitPane splitView;

    /**
     * Standard main method for Java applications.
     * @param args
     *      Args used by the program for the main method.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The main entry point for all JavaFX programs
     *
     * @param window
     *      The parameter that serves as the window of the program
     *      and users can set a custom title. Application scenes
     *      are then set here.
     *
     * @throws Exception
     *      Throws Exception since this method is threaded.
     */
    @Override
    public void start(Stage window) {
        window.setTitle("Decision Tree Classifier");
        window.setOnCloseRequest(e-> System.exit(1));

        try {
            // Load the FXML file to create the layout
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DecisionTreeGUI.class.getResource
                    ("decisiontreegui.fxml"));

            splitView = loader.load();

            Scene scene = new Scene(splitView);
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
