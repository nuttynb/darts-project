package hu.nutty.darts.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import hu.nutty.darts.controller.GameController;
import hu.nutty.darts.model.Player;
import hu.nutty.darts.model.Throw;
import hu.nutty.darts.model.n01;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;

public class DartsMainController {
	private static GameController gc;
	private static ResourceBundle bundle;
	private ResourceBundle checkoutBundle = ResourceBundle.getBundle("hu.nutty.darts.checkout_table", Locale.ENGLISH);
	private int actualPlayerThrowing = 0;

	@FXML
	private TableView<Throw> player1Table;
	@FXML
	private TableView<Throw> player2Table;
	@FXML
	private TableColumn<Throw, Integer> player1ToGo;
	@FXML
	private TableColumn<Throw, Integer> player1Score;
	@FXML
	private TableColumn<Throw, Integer> player2ToGo;
	@FXML
	private TableColumn<Throw, Integer> player2Score;
	@FXML
	private TextField scoreField;
	@FXML
	private Label checkout1Label;
	@FXML
	private Label checkout2Label;
	@FXML
	private Label player1Label;
	@FXML
	private Label player2Label;
	@FXML
	private Label averageLabel;
	@FXML
	private Label firstNineLabel;
	@FXML
	private Label statisticsLabel;
	@FXML
	private Label average2Label;
	@FXML
	private Label firstNine2Label;
	@FXML
	private Label statistics2Label;
	@FXML
	private Label p1ActualTonLabel;
	@FXML
	private Label p1LegsLabel;
	@FXML
	private Label p2LegsLabel;
	@FXML
	private Label p1SetsLabel;
	@FXML
	private Label p2SetsLabel;
	@FXML
	private Label p1ActualDartsAvgLabel;
	@FXML
	private Label p1Actual3DartsAvgLabel;
	@FXML
	private Label p2ActualDartsAvgLabel;
	@FXML
	private Label p2Actual3DartsAvgLabel;
	@FXML
	private Label p2ActualTonLabel;
	@FXML
	private Label p1ActualTonFortyLabel;
	@FXML
	private Label p2ActualTonFortyLabel;
	@FXML
	private Label p1ActualTonEightyLabel;
	@FXML
	private Label p2ActualTonEightyLabel;
	@FXML
	private Label p1HighOutLabel;
	@FXML
	private Label p2HighOutLabel;
	@FXML
	private Label p1BestDartsLabel;
	@FXML
	private Label p2BestDartsLabel;
	@FXML
	private Label p1FirstNineLabel;
	@FXML
	private Label p2FirstNineLabel;

	private ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

	public static void setMain(GameController _gc) {
		gc = _gc;
	}

	public static void setBundle(ResourceBundle _bundle) {
		bundle = _bundle;
	}

	@FXML
	private void initialize() {
		player1Table.getStylesheets().add("hu/nutty/darts/view/table.css");
		player2Table.getStylesheets().add("hu/nutty/darts/view/table.css");
		player1Table.setPlaceholder(new Label(""));
		player2Table.setPlaceholder(new Label(""));
		averageLabel.setText(bundle.getString("average"));
		firstNineLabel.setText(bundle.getString("firstnine"));
		statisticsLabel.setText(bundle.getString("statistics"));
		average2Label.setText(bundle.getString("average"));
		firstNine2Label.setText(bundle.getString("firstnine"));
		statistics2Label.setText(bundle.getString("statistics"));

		/*
		 * gc.getPrimaryStage().widthProperty().addListener(new
		 * ChangeListener<Number>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends Number>
		 * observableValue, Number oldWidth, Number newWidth) {
		 * fontTracking.set(Font.font(newWidth.doubleValue() / 50)); } });
		 * statisticsLabel.fontProperty().bind(fontTracking);
		 * statistics2Label.fontProperty().bind(fontTracking);
		 * 
		 * actualLabel.fontProperty().bind(fontTracking);
		 * scoreLabel.fontProperty().bind(fontTracking);
		 * actual2Label.fontProperty().bind(fontTracking);
		 * score2Label.fontProperty().bind(fontTracking);
		 * averageLabel.fontProperty().bind(fontTracking);
		 * average2Label.fontProperty().bind(fontTracking);
		 * savedLabel.fontProperty().bind(fontTracking);
		 * saved2Label.fontProperty().bind(fontTracking);
		 */

	}

	public void initializeTableValues() {
		player1ToGo.setCellValueFactory(new PropertyValueFactory<>("toGo"));
		player1Score.setCellValueFactory(new PropertyValueFactory<>("score"));
		player1Table.setItems(gc.getGame().getPlayers().get(0).getThrowList());
		player2ToGo.setCellValueFactory(new PropertyValueFactory<>("toGo"));
		player2Score.setCellValueFactory(new PropertyValueFactory<>("score"));
		player2Table.setItems(gc.getGame().getPlayers().get(1).getThrowList());
		player1Label.setText(gc.getGame().getPlayers().get(0).getNickname());
		player2Label.setText(gc.getGame().getPlayers().get(1).getNickname());

	}

	public void refreshStats() {
		n01 n01Game = (n01) gc.getGame();
		p1LegsLabel.setText(String.valueOf(n01Game.getPlayer1Legs()));
		p2LegsLabel.setText(String.valueOf(n01Game.getPlayer2Legs()));
		p1SetsLabel.setText(String.valueOf(n01Game.getPlayer1Sets()));
		p2SetsLabel.setText(String.valueOf(n01Game.getPlayer2Sets()));
		p1ActualTonLabel.setText(String.valueOf(gc.getPlayer1().getActualTons()));
		p2ActualTonLabel.setText(String.valueOf(gc.getPlayer2().getActualTons()));
		p1ActualTonFortyLabel.setText(String.valueOf(gc.getPlayer1().getActualTonForty()));
		p1ActualTonEightyLabel.setText(String.valueOf(gc.getPlayer1().getActualTonEighty()));
		p2ActualTonFortyLabel.setText(String.valueOf(gc.getPlayer2().getActualTonForty()));
		p2ActualTonEightyLabel.setText(String.valueOf(gc.getPlayer2().getActualTonEighty()));
		p1ActualDartsAvgLabel.setText(String.valueOf(gc.getPlayer1().getActualDartsAvg()));
		p1Actual3DartsAvgLabel.setText(String.valueOf(gc.getPlayer1().getActual3DartsAvg()));
		p2ActualDartsAvgLabel.setText(String.valueOf(gc.getPlayer2().getActualDartsAvg()));
		p2Actual3DartsAvgLabel.setText(String.valueOf(gc.getPlayer2().getActual3DartsAvg()));
		p1HighOutLabel.setText(String.valueOf(gc.getPlayer1().getActualHighOut()));
		p2HighOutLabel.setText(String.valueOf(gc.getPlayer2().getActualHighOut()));
		p1BestDartsLabel.setText(String.valueOf(gc.getPlayer1().getActualBestDarts()));
		p2BestDartsLabel.setText(String.valueOf(gc.getPlayer2().getActualBestDarts()));
		p1FirstNineLabel.setText(String.valueOf(gc.getPlayer1().getActualFirstNine()));
		p2FirstNineLabel.setText(String.valueOf(gc.getPlayer2().getActualFirstNine()));

	}

	public void invalidThrow() {
		actualPlayerThrowing--;
	}

	public void playerWonLeg(String name, int dartsThrown) {
		AlertBox.display(bundle.getString("end"),
				name + " " + bundle.getString("won") + " " + dartsThrown + " " + bundle.getString("dartsthrown"));
		// gc.getGame().getPlayers().get(0).resetThrows();
		gc.getPlayer1().resetThrows();
		// gc.getGame().getPlayers().get(1).resetThrows();
		gc.getPlayer2().resetThrows();
		initializeTableValues();
	}

	public void setScore() {
		actualPlayerThrowing = actualPlayerThrowing % 2;
		try {
			if (gc.getGame() != null) {
				int possibleScore = Integer.parseInt(scoreField.getText());
				gc.getGame().getPlayers().get(actualPlayerThrowing).addThrow(possibleScore);
				actualPlayerThrowing++;
				int tableSize = player1Table.getItems().size() - 1;
				player1Table.scrollTo(tableSize);
				tableSize = player2Table.getItems().size() - 1;
				player2Table.scrollTo(tableSize);
				refreshStats();

			} else
				AlertBox.display(bundle.getString("error"), bundle.getString("noplayers"));
		} catch (NumberFormatException nfe) {
			AlertBox.display(bundle.getString("error"), bundle.getString("number"));
		}

		scoreField.clear();

	}

	public void displayCheckoutTable(int scoreToGo) {
		if (actualPlayerThrowing == 0)
			checkout1Label.setText(checkoutBundle.getString(String.valueOf(scoreToGo)));
		else
			checkout2Label.setText(checkoutBundle.getString(String.valueOf(scoreToGo)));
	}

	public void keyListener(KeyEvent event) {
		actualPlayerThrowing = actualPlayerThrowing % 2;
		switch (event.getCode()) {
		case F1:
			gc.getGame().getPlayers().get(actualPlayerThrowing).addThrow(0);
			break;
		case F2:
			gc.getGame().getPlayers().get(actualPlayerThrowing).addThrow(26);
			break;
		case F3:
			gc.getGame().getPlayers().get(actualPlayerThrowing).addThrow(41);
			break;
		case F4:
			gc.getGame().getPlayers().get(actualPlayerThrowing).addThrow(45);
			break;
		case F5:
			gc.getGame().getPlayers().get(actualPlayerThrowing).addThrow(60);
			break;
		case F6:
			gc.getGame().getPlayers().get(actualPlayerThrowing).addThrow(81);
			break;
		default:
			actualPlayerThrowing--;
			break;
		}
		actualPlayerThrowing++;
		int tableSize = player1Table.getItems().size() - 1;
		player1Table.scrollTo(tableSize);
		tableSize = player2Table.getItems().size() - 1;
		player2Table.scrollTo(tableSize);
	}

}
