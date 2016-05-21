package hu.nutty.darts.view;

import java.util.ResourceBundle;

import hu.nutty.darts.controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AboutController {
	private static GameController gc;
	private static ResourceBundle bundle;
	private Stage stage;
	@FXML
	private Button okButton;
	@FXML
	private ImageView logoView;
	@FXML
	private Label aboutLabel;
	private Image logoImage;
	public static void setMain(GameController _gc) {
		gc = _gc;
	}

	public static void setBundle(ResourceBundle _bundle) {
		bundle = _bundle;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	@FXML
	private void initialize(){
		logoImage = new Image(this.getClass().getClassLoader().getResourceAsStream("hu/nutty/darts/dartsicon.png"));
		logoView.setImage(logoImage);
		aboutLabel.setText(bundle.getString("about"));
		okButton.defaultButtonProperty().bind(okButton.focusedProperty());
	}
	@FXML
	private void handleOkButton(){
		stage.close();
	}

}
