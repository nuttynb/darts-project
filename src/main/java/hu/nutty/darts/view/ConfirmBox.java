package hu.nutty.darts.view;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.util.Optional;

import javafx.geometry.*;

public class ConfirmBox {

    //Create variable
    static boolean answer;

    public static boolean display(String title, String message) {
        /*Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);
    	
        //Create two buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
      
        yesButton.defaultButtonProperty().bind(yesButton.focusedProperty());
        yesButton.setFocusTraversable(false);
        noButton.defaultButtonProperty().bind(noButton.focusedProperty());
        noButton.requestFocus();

        //Clicking will set answer and close window
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);

        //Add buttons
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        
     Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        //Make sure to return answer*/
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle(title);
    	alert.setHeaderText(null);
    	alert.setContentText(message);

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		answer = true;
    	} else {
    		 answer = false;
    	}
        return answer;
    }

}