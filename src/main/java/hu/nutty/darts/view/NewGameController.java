package hu.nutty.darts.view;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import hu.nutty.darts.controller.GameController;
import hu.nutty.darts.model.GameInterface;
import hu.nutty.darts.model.GameInterface.GameType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewGameController {

	private GameController gc;
	private DartsMainController dmc;
	private static ResourceBundle bundle;
	private Scene scene;
	private Stage stage;
	private GameInterface.GameType gameType = GameType._501;
	private String player1;
	private String player2;
	public static void setBundle(ResourceBundle _bundle) {
		bundle = _bundle;
	}

	public void setMain(GameController gc, DartsMainController dmc) {
		this.gc = gc;
		this.dmc = dmc;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	Label newGameLabel;
	@FXML
	Label gameTypeLabel;
	@FXML
	Label selectPlayerLabel;
	@FXML
	RadioButton radio301;
	@FXML
	RadioButton radio501;
	@FXML
	RadioButton radio1001;
	@FXML
	RadioButton radioCricket;
	@FXML
	ComboBox<String> player1Choice;
	@FXML
	ComboBox<String> player2Choice;
	@FXML
	Button okButton;
	@FXML
	Button cancelButton;
	@FXML
	Button createPlayerButton;

	ObservableList<String> playerNames = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		okButton.setText(bundle.getString("play"));
		cancelButton.setText(bundle.getString("cancel"));
		createPlayerButton.setText(bundle.getString("createnewplayer"));
		newGameLabel.setText(bundle.getString("newgame"));
		gameTypeLabel.setText(bundle.getString("gametype"));
		selectPlayerLabel.setText(bundle.getString("newgame"));
		radioCricket.setText(bundle.getString("cricket"));
		player1Choice.setPromptText(bundle.getString("select1"));
		player2Choice.setPromptText(bundle.getString("select2"));
		okButton.defaultButtonProperty().bind(okButton.focusedProperty());
		createPlayerButton.defaultButtonProperty().bind(createPlayerButton.focusedProperty());
		cancelButton.defaultButtonProperty().bind(cancelButton.focusedProperty());
		refreshList();

	}

	public void refreshList() {
		player1Choice.getItems().clear();
		player2Choice.getItems().clear();
		playerNames = GameController.getPlayerNames();
		for (String player : playerNames) {
			player1Choice.getItems().add(player);
			player2Choice.getItems().add(player);
		}
	}

	@FXML
	private void handleCreateNewPlayerButton() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("hu/nutty/darts/view/CreatePlayerView.fxml"));

			BorderPane createPlayer;

			createPlayer = (BorderPane) loader.load();

			CreatePlayerController controller = loader.getController();
			controller.passReferences(gc, stage, scene, this);
			Scene createPlayerScene = new Scene(createPlayer);
			stage.setScene(createPlayerScene);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void handleRadioButtons() {
		if (radio301.isSelected()) {
			gameType = GameInterface.GameType._301;
		}
		if (radio501.isSelected()) {
			gameType = GameInterface.GameType._501;
		}
		if (radio1001.isSelected()) {
			gameType = GameInterface.GameType._1001;
		}
		if (radioCricket.isSelected()) {
			gameType = GameInterface.GameType.cricket;
		}
	}

	@FXML
	private void handleComboBoxes() {
		player1 = player1Choice.getValue();
		player2 = player2Choice.getValue();
	}

	@FXML
	private void handleOkButton() {
		if (player1Choice.getValue() != null && player2Choice.getValue() != null) {
			if (!player1.equals(player2)) {
				gc.newGameSelectedItems(player1, player2, gameType);
				dmc.initializeTableValues();
				gc.getDmc().refreshStats();
				stage.close();
			} else
				AlertBox.display(bundle.getString("error"), bundle.getString("same"));
		} else
			AlertBox.display(bundle.getString("error"), bundle.getString("notselected"));
	}

	@FXML
	private void handleCancelButton() {
		stage.close();
	}

}
