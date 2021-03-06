package hu.nutty.darts.view;

import java.util.Locale;
import java.util.ResourceBundle;
import hu.nutty.darts.controller.GameController;
import hu.nutty.darts.model.Player;
import hu.nutty.darts.model.Throw;
import hu.nutty.darts.model.n01;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.util.converter.IntegerStringConverter;

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
	private BooleanProperty firstPlayerToThrowFirst = new SimpleBooleanProperty(true);
	@FXML
	private Circle p1ToThrowFirst;
	@FXML
	private Circle p2ToThrowFirst;
	// private ObjectProperty<Font> fontTracking = new
	// SimpleObjectProperty<Font>(Font.getDefault());

	public static void setMain(GameController _gc) {
		gc = _gc;
	}

	public static void setBundle(ResourceBundle _bundle) {
		bundle = _bundle;
	}

	@FXML
	public void initialize() {
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
		clearCheckout(null);
		firstPlayerToThrowFirst.set(true);
		firstPlayerToThrowFirst.addListener((v, oldValue, newValue) -> {
			if (newValue) {
				p1ToThrowFirst.setVisible(true);
				p2ToThrowFirst.setVisible(false);
			} else {
				p1ToThrowFirst.setVisible(false);
				p2ToThrowFirst.setVisible(true);
			}
		});
		player1Table.setEditable(true);
		player2Table.setEditable(true);
		setTableColumnEditable(player1Score, gc.getPlayer1());
		setTableColumnEditable(player2Score, gc.getPlayer2());
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

	private void setTableColumnEditable(TableColumn<Throw, Integer> tc, Player player) {

		tc.setCellFactory(TextFieldTableCell.<Throw, Integer> forTableColumn(new IntegerStringConverter()));
		tc.setOnEditCommit(new EventHandler<CellEditEvent<Throw, Integer>>() {
			@Override
			public void handle(CellEditEvent<Throw, Integer> t) {
				int modifiedRow = t.getTablePosition().getRow();

				ObservableList<Throw> newThrowList = FXCollections.observableArrayList(player.getThrowList());
				if (newThrowList.size() - 1 == modifiedRow) {
					t.getRowValue().setScore(null);
					t.getTableView().refresh();
				} else if (t.getNewValue() > 180) {
					t.getRowValue().setScore(t.getOldValue());
					t.getTableView().refresh();
				} else {
					int actualToGo = newThrowList.get(modifiedRow).getToGo();
					newThrowList.remove(modifiedRow);
					newThrowList.add(modifiedRow, new Throw(t.getNewValue(), actualToGo));
					player.initializeStats();
					for (Throw throw1 : newThrowList) {
						if (throw1.getScore() != null) {
							if (tc == player1Score)
								gc.getGs().addThrow(throw1.getScore(), 0);
							if (tc == player2Score)
								gc.getGs().addThrow(throw1.getScore(), 1);
						}
					}
				}
				clearCheckout(player.getNickname());
				initializeTableValues();
				refreshStats();
			}
		});
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
		if (gc.getPlayer1().getActualBestDarts() != Integer.MAX_VALUE)
			p1BestDartsLabel.setText(String.valueOf(gc.getPlayer1().getActualBestDarts()));
		else
			p1BestDartsLabel.setText("0");
		if (gc.getPlayer2().getActualBestDarts() != Integer.MAX_VALUE)
			p2BestDartsLabel.setText(String.valueOf(gc.getPlayer2().getActualBestDarts()));
		else
			p2BestDartsLabel.setText("0");
		p1FirstNineLabel.setText(String.valueOf(gc.getPlayer1().getActualFirstNine()));
		p2FirstNineLabel.setText(String.valueOf(gc.getPlayer2().getActualFirstNine()));
	}

	public void invalidThrow() {
		actualPlayerThrowing--;
	}

	public void clearCheckout(String nickname) {
		if (nickname == null) {
			checkout1Label.setText("");
			checkout2Label.setText("");
		}
		if (gc.getPlayer1() != null && gc.getPlayer1().getNickname().equals(nickname))
			checkout1Label.setText("");
		if (gc.getPlayer2() != null && gc.getPlayer2().getNickname().equals(nickname))
			checkout2Label.setText("");
	}

	public void playerWonLeg(String name, int dartsThrown) {
		AlertBox.display(bundle.getString("end"),
				name + " " + bundle.getString("won") + " " + dartsThrown + " " + bundle.getString("dartsthrown"));
		gc.getPlayer1().resetThrows();
		gc.getPlayer2().resetThrows();
		clearCheckout(null);
		if (firstPlayerToThrowFirst.get()) {
			actualPlayerThrowing = 0;
			firstPlayerToThrowFirst.set(false);
		} else {
			actualPlayerThrowing = 1;
			firstPlayerToThrowFirst.set(true);
		}
		initializeTableValues();
	}

	public void setScore() {
		actualPlayerThrowing = actualPlayerThrowing % 2;
		try {
			if (gc.getGs() != null) {
				int possibleScore = Integer.parseInt(scoreField.getText());
				gc.getGs().addThrow(possibleScore, actualPlayerThrowing);
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
			gc.getGs().addThrow(0, actualPlayerThrowing);
			break;
		case F2:
			gc.getGs().addThrow(26, actualPlayerThrowing);
			break;
		case F3:
			gc.getGs().addThrow(41, actualPlayerThrowing);
			break;
		case F4:
			gc.getGs().addThrow(45, actualPlayerThrowing);
			break;
		case F5:
			gc.getGs().addThrow(60, actualPlayerThrowing);
			break;
		case F6:
			gc.getGs().addThrow(81, actualPlayerThrowing);
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

	public int getActualPlayerThrowing() {
		return actualPlayerThrowing;
	}

	public void setActualPlayerThrowing(int actualPlayerThrowing) {
		this.actualPlayerThrowing = actualPlayerThrowing;
	}

}
