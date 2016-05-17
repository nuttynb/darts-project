package hu.nutty.darts.view;

import java.util.ResourceBundle;

import hu.nutty.darts.controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class SettingsController {
	private static GameController gc;
	private static ResourceBundle bundle;
	private Scene scene;
	private Stage stage;
	@FXML
	private RadioButton hunRadio;
	@FXML
	private RadioButton engRadio;
	@FXML
	private RadioButton caspianRadio;
	@FXML
	private RadioButton modenaRadio;
	@FXML
	private RadioButton aquaRadio;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	@FXML
	private void initialize() {
		hunRadio.setSelected(gc.getSettings().isHungarianLang());
		engRadio.setSelected(gc.getSettings().isEnglishLang());
		modenaRadio.setSelected(gc.getSettings().isModenaTheme());
		caspianRadio.setSelected(gc.getSettings().isCaspianTheme());
		aquaRadio.setSelected(gc.getSettings().isAquaTheme());	
	}
	
	public static void setMain(GameController _gc) {
		gc = _gc;
	}

	public static void setBundle(ResourceBundle _bundle) {
		bundle = _bundle;
	}
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	@FXML
	private void handleOkButton(){
		gc.settingsChosen(engRadio.isSelected(), hunRadio.isSelected(), modenaRadio.isSelected(), caspianRadio.isSelected(), aquaRadio.isSelected());
		stage.close();
	}
	

}
