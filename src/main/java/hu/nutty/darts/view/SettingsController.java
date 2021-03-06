package hu.nutty.darts.view;

import java.util.ResourceBundle;
import hu.nutty.darts.controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class SettingsController {
	private static GameController gc;
	private static ResourceBundle bundle;
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
	private Label langLabel;
	@FXML
	private Label themesLabel;

	@FXML
	private void initialize() {
		hunRadio.setText(bundle.getString("hungarian"));
		engRadio.setText(bundle.getString("english"));
		langLabel.setText(bundle.getString("languages"));
		themesLabel.setText(bundle.getString("themes"));
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

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private void handleOkButton() {
		gc.settingsChosen(engRadio.isSelected(), hunRadio.isSelected(), modenaRadio.isSelected(),
				caspianRadio.isSelected(), aquaRadio.isSelected());
		stage.close();
	}

	@FXML
	private void handleCancelButton() {
		stage.close();
	}

}
