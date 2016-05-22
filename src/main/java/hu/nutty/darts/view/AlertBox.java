package hu.nutty.darts.view;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class AlertBox {

	public static void display(String title, String message) {
		/*
		 * Stage window = new Stage();
		 * 
		 * //Block events to other windows
		 * window.initModality(Modality.APPLICATION_MODAL);
		 * window.setTitle(title); window.setMinWidth(250);
		 * 
		 * Label label = new Label(); label.setText(message); Button closeButton
		 * = new Button("OK");
		 * closeButton.defaultButtonProperty().bind(closeButton.focusedProperty(
		 * )); closeButton.setOnAction(e -> window.close());
		 * 
		 * VBox layout = new VBox(10); layout.getChildren().addAll(label,
		 * closeButton); layout.setAlignment(Pos.CENTER);
		 * 
		 * //Display window and wait for it to be closed before returning Scene
		 * scene = new Scene(layout); window.setScene(scene);
		 * window.showAndWait();
		 */
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}

}