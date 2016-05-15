package hu.nutty.darts.view;

import java.util.ResourceBundle;

import hu.nutty.darts.controller.GameController;
import hu.nutty.darts.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SavedStatisticsController {
	private static GameController gc;
	private static ResourceBundle bundle;
	@FXML
	private TableView<Player> playersTable;
	@FXML
	private TableColumn<Player, String> nameColumn;
	@FXML
	private TableColumn<Player, String> nicknameColumn;
	@FXML
	private TableColumn<Player, String> emailColumn;
	@FXML
	private TableColumn<Player, String> levelColumn;
	@FXML
	private TableColumn<Player, Double> darts3AvgColumn;
	@FXML
	private TableColumn<Player, Double> dartsAvgColumn;
	@FXML
	private TableColumn<Player, Double> firstNineColumn;
	@FXML
	private TableColumn<Player, Integer> tonsColumn;
	@FXML
	private TableColumn<Player, Integer> tonFortyColumn;
	@FXML
	private TableColumn<Player, Integer> tonEightyColumn;
	@FXML
	private TableColumn<Player, Integer> highOutColumn;
	@FXML
	private TableColumn<Player, Integer> bestDartsColumn;
	
	public static void setMain(GameController _gc) {
		gc = _gc;
	}

	public static void setBundle(ResourceBundle _bundle) {
		bundle = _bundle;
	}
	
	@FXML
	private void initialize() {
		initializeTableValues();
	}
	
	public void initializeTableValues() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
		darts3AvgColumn.setCellValueFactory(new PropertyValueFactory<>("saved3DartsAvg"));
		dartsAvgColumn.setCellValueFactory(new PropertyValueFactory<>("savedDartsAvg"));
		firstNineColumn.setCellValueFactory(new PropertyValueFactory<>("savedFirstNine"));
		tonsColumn.setCellValueFactory(new PropertyValueFactory<>("savedTons"));
		tonFortyColumn.setCellValueFactory(new PropertyValueFactory<>("savedTonForty"));
		tonEightyColumn.setCellValueFactory(new PropertyValueFactory<>("savedTonEighty"));
		highOutColumn.setCellValueFactory(new PropertyValueFactory<>("savedHighOut"));
		bestDartsColumn.setCellValueFactory(new PropertyValueFactory<>("savedBestDarts"));
		
		playersTable.setItems(GameController.getAllPlayers());
	}

}
