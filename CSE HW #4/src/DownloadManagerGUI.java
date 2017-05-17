import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by slim1 on 10/17/2016.
 */
public class DownloadManagerGUI extends Application {

    // GUI
    private Stage window;

    BorderPane borderPane;

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
     * @param primaryStage
     *      The parameter that serves as the window of the program
     *      and users can set a custom title. Application scenes
     *      are then set here.
     *
     * @throws Exception
     *      Throws Exception since this method is threaded.
     */
    public void start(Stage primaryStage) {
        this.window = primaryStage;
        window.setTitle("Download Manager"); // Sets the title of the
        // application.
        window.setOnCloseRequest(e -> System.exit(0));

        initLayout(window);
    }

    /**
     * Constructs a loader for the form which sets the layout of all the
     * UI controls. It also sets the scene and shows the window.
     *
     * @param stage
     *      Passes the stage object so the stage's scene could be set.
     */
    public void initLayout(Stage stage) {
        try {
            // Loader that will load the UI elements and layout.
            FXMLLoader loader = new FXMLLoader();
            // Sets the location of where the loader should load the resources from.
            loader.setLocation(DownloadManagerGUI.class.getResource
                    ("downloadgui.fxml"));
            // Sets borderPAne to the base layout from the FXML file
            borderPane = (BorderPane) loader.load();

            // Display the scene
            Scene scene = new Scene(borderPane);
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
