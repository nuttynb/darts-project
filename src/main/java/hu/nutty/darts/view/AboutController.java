package hu.nutty.darts.view;

import java.util.ResourceBundle;

import hu.nutty.darts.controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AboutController {
	private static GameController gc;
	private static ResourceBundle bundle;
	@FXML
	private Button okButton;
	@FXML
	private ImageView logoView;
	private Image logoImage;
	public static void setMain(GameController _gc) {
		gc = _gc;
	}

	public static void setBundle(ResourceBundle _bundle) {
		bundle = _bundle;
	}
	@FXML
	private void initialize(){
		logoImage = new Image(this.getClass().getClassLoader().getResourceAsStream("hu/nutty/darts/dartsicon.png"));
		logoView.setImage(logoImage);
		
	}

}
