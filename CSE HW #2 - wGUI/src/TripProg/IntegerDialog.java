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
 * A dialog that allows for numeric input for the distance of
 * a <code>TripStop</code>.
 *
 * @author: Stanley Lim
 *      Email: Some Email
 *      Stony Brook ID: Some ID
 */
public class IntegerDialog {

    // Variable that holds the distance.
    private static int distance = 0;

    /**
     * Displays a modal dialog that allows the user to input
     * an integer and returns it.
     *
     * @param command
     *      The text that the label will be set to.
     *
     * @return
     *      Returns the distance input by the user.
     */
    public static int showDialog(String command) {
        Stage window = new Stage();
        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle("Input Distance");
        window.setMinWidth(300);

        Label label = new Label();
        label.setText(command);
        TextField txtDistance = new TextField();
        txtDistance.setPromptText("Distance in miles goes here");
        Button confirm = new Button();
        confirm.setText("Confirm");
        confirm.setOnAction(e-> {
            if (isNumeric(txtDistance.getText())) {
                distance = Integer.parseInt(txtDistance.getText());
                window.close();
            } else {
                label.setText("Please enter a VALID distance.");
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, txtDistance, confirm);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return distance;
    }

    /**
     * Returns true if the string output is numeric and false
     * if it isn't.
     *
     * @param str
     *      The String for the method to analyze.
     *
     * @return
     *      True if the string contains only numbers and false if not.
     */
    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

}
