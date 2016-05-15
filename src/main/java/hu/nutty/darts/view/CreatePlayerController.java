package hu.nutty.darts.view;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.validator.routines.EmailValidator;

import hu.nutty.darts.controller.GameController;
import hu.nutty.darts.model.Player;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreatePlayerController {
	@FXML
	private TextField nameField;
	@FXML
	private TextField nickNameField;
	@FXML
	private TextField levelField;
	@FXML
	private TextField emailField;
	@FXML
	private Button createButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Label createPlayerLabel;
	@FXML
	private Label invalidEmailLabel;

	private GameController gc;
	private static ResourceBundle bundle;
	private Scene scene;
	private Stage stage;
	private NewGameController ngc;
	private boolean isValidEmail = true;
	
	public static void setBundle(ResourceBundle _bundle) {
		bundle = _bundle;
	}

	public void passReferences(GameController gc, Stage stage, Scene scene, NewGameController ngc) {
		this.gc = gc;
		this.scene = scene;
		this.stage = stage;
		this.ngc = ngc;
	}

	@FXML
	private void initialize() {
		nameField.setPromptText(bundle.getString("name"));
		nickNameField.setPromptText(bundle.getString("nickname"));
		levelField.setPromptText(bundle.getString("level"));
		emailField.setPromptText(bundle.getString("email"));
		nameField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
		nickNameField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
		levelField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
		emailField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
		createButton.setText(bundle.getString("create"));
		cancelButton.setText(bundle.getString("cancel"));
		createPlayerLabel.setText(bundle.getString("createnewplayer"));
		createButton.defaultButtonProperty().bind(createButton.focusedProperty());
		cancelButton.defaultButtonProperty().bind(cancelButton.focusedProperty());
		emailField.textProperty().addListener((v, oldValue, newValue) -> {
			if (!isValidEmailAddress(newValue)){
				invalidEmailLabel.setText(bundle.getString("notvalid"));
				isValidEmail = false;
			} else {
				invalidEmailLabel.setText("");
				isValidEmail = true;
			}
		});

	}
	public static boolean isValidEmailAddress(String email) {
		  return EmailValidator.getInstance().isValid(email);
		}

	@FXML
	private void handleCreateButton() {
		if (!nameField.getText().isEmpty() && !nickNameField.getText().isEmpty() && !levelField.getText().isEmpty()
				&& !emailField.getText().isEmpty()) {
			if ( isValidEmail ){
			Player player = new Player(nameField.getText(), nickNameField.getText(), emailField.getText(),
					levelField.getText());
			gc.savePlayerToXML(player);
			ngc.refreshList();
			stage.setScene(scene);
			}else AlertBox.display(bundle.getString("error"), bundle.getString("notvalid"));
		} else
			AlertBox.display(bundle.getString("error"), bundle.getString("field"));
	}

	@FXML
	private void handleCancelButton() {
		stage.setScene(scene);
	}

}
