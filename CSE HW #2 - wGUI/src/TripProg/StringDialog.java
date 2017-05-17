package TripProg;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A dialog that allows for string input for the location or activity of
 * a <code>TripStop</code>.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */
public class StringDialog {

    // Variable that holds the return value.
    private static String value = "";

    /**
     * Shows the modal dialog and asks for user input for the location
     * or activity. It then returns the value of the string.
     *
     * @param command
     *      The message that the label will have.
     *
     * @return
     *      The location and activiy for the <code>TripStop</code> object.
     */
    public static String showDialog(String command) {
        Stage window = new Stage();
        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle("Input Distance");
        window.setMinWidth(300);

        Label label = new Label();
        label.setText(command);
        TextField txtDistance = new TextField();
        txtDistance.setPromptText("String value goes here");
        Button confirm = new Button();
        confirm.setText("Confirm");
        confirm.setOnAction(e-> {
            value = txtDistance.getText();
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, txtDistance, confirm);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return value;
    }
}
