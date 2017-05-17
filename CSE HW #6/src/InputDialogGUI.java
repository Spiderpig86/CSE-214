import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A dialog that allows for string input for the value that requires an
 * string.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */
public class InputDialogGUI {
     // Return value for the string
    private static String value = "";

    /**
     * Displays a modal dialog that allows the user to input
     * a string and returns it.
     *
     * @param command
     *      The text that the label will be set to.
     *
     * @return
     *      Returns the value input by the user.
     */
    public static String showDialog(String command, boolean edit) {
        // Reset string value
        value = "";

        Stage window = new Stage();
        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle("Input File");
        window.setMinWidth(300);

        Label label = new Label();
        label.setText(command);
        TextField txtString = new TextField();
        txtString.setPromptText("Enter a the required string.");
        Button confirm = new Button();
        confirm.setText("Confirm");
        confirm.setOnAction(e-> {
            if (txtString.getLength() == 0 && !edit) {
                label.setText("Please enter a nonempty string.");
            } else {
                value = txtString.getText();
                window.close();
            }
        });

        // Create the dialog layout.
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, txtString, confirm);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return value;
    }
}
