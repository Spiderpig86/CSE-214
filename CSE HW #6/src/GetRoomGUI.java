import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

/**
 * GUI class for the <code>SBGetARoom</code> program.
 *
 * @author: Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class GetRoomGUI extends Application {

    @FXML
    SplitPane splitView;

    public static void start(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        window.setTitle("SB Get A Room");
        window.setOnCloseRequest(e-> System.exit(1));

        try {
            // Load the FXML file to create the layout
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GetRoomGUI.class.getResource("getroom.fxml"));

            splitView = loader.load();

            Scene scene = new Scene(splitView);
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
