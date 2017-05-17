package TripProg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TripPlannerGUI extends Application {

    // The window for the program.
    private Stage window;
    // A panel that is split somewhere in between to create
    // 2 sections.
    private SplitPane rootLayout;

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
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.window = primaryStage;
        primaryStage.setTitle("Trip Planner");

        initLayout(primaryStage);

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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TripPlannerGUI.class.getResource("TripPlanner.fxml"));
            rootLayout = (SplitPane) loader.load();

            // Show the scene
            Scene scene = new Scene(rootLayout);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
