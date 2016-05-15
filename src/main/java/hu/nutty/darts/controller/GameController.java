package hu.nutty.darts.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;

import com.aquafx_project.AquaFx;

import hu.nutty.darts.model.Game;
import hu.nutty.darts.model.GameInterface;
import hu.nutty.darts.model.Player;
import hu.nutty.darts.model.XMLUtil;
import hu.nutty.darts.model.n01;
import hu.nutty.darts.view.AlertBox;
import hu.nutty.darts.view.ConfirmBox;
import hu.nutty.darts.view.CreatePlayerController;
import hu.nutty.darts.view.DartsMainController;
import hu.nutty.darts.view.NewGameController;
import hu.nutty.darts.view.RootPaneController;
import hu.nutty.darts.view.SavedStatisticsController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameController extends Application {

	private Stage primaryStage;
	private BorderPane rootPane;
	private Locale locale;
	private ResourceBundle bundle;
	private Game game;
	private Player player1;
	private Player player2;
	private DartsMainController dmc;
	List<Player> players = new ArrayList<>();

	public final static String PLAYERFOLDER = "darts_files/players/";
	public final static String SETTINGSFOLDER = "darts_files/settings/";
	public final static String SNAPSHOTS = "darts_files/snapshots/";

	public void savePlayerToXML(Player player) {
		try {
			XMLUtil.toXML(player, new FileOutputStream(new File(PLAYERFOLDER + player.getNickname() + ".xml")));
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
		}
	}
	public void setGameResult(String winner){
		n01 n01Game = (n01) game;
		if (winner.equals(player1.getNickname()))
			n01Game.setPlayer1Legs(n01Game.getPlayer1Legs()+1);
		if (winner.equals(player2.getNickname()))
			n01Game.setPlayer2Legs(n01Game.getPlayer2Legs()+1);
			
	}

	public void newGameSelectedItems(String player1, String player2, GameInterface.GameType gameType) {
		try {
			this.player1 = XMLUtil.fromXML(Player.class,
					new FileInputStream(new File(PLAYERFOLDER + player1 + ".xml")));

			this.player2 = XMLUtil.fromXML(Player.class,
					new FileInputStream(new File(PLAYERFOLDER + player2 + ".xml")));
			this.player1.setGameType(gameType);
			this.player2.setGameType(gameType);
			this.player1.initializeStats();
			this.player2.initializeStats();
			players.clear();
			players.add(this.player1);
			players.add(this.player2);
			switch (gameType) {
			case _301:
				game = new n01(2, players, gameType);
				break;
			case _501:
				game = new n01(2, players, gameType);
				break;
			case _1001:
				game = new n01(2, players, gameType);
				break;
			case cricket:
				game = new n01(2, players, gameType);
				break;
			default:
				break;
			}
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
		}

	}

	public void createFolder(String folder) {
		File fileFolders = new File(folder);

		if (!fileFolders.exists() || !fileFolders.isDirectory()) {
			System.out.println("folder not exist");
			boolean success = fileFolders.mkdirs();
			System.out.println("folders created");
			if (!success) {
				AlertBox.display(bundle.getString("error"), bundle.getString("folder"));
				System.exit(1);
			}
		}
	}

	public static ObservableList<String> getPlayerNames() {
		return XMLUtil.getExistingPlayerNames(PLAYERFOLDER);
	}

	public static ObservableList<Player> getAllPlayers() {
		ObservableList<String> allPlayerNames = getPlayerNames();
		ObservableList<Player> allPlayers = FXCollections.observableArrayList();
		Player player;
		try {
			for (String playerName : allPlayerNames) {
				player = XMLUtil.fromXML(Player.class,
						new FileInputStream(new File(PLAYERFOLDER + playerName + ".xml")));
				allPlayers.add(player);
			}
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
		}
		return allPlayers;
	}

	@Override
	public void start(Stage primaryStage) {
		locale = Locale.getDefault();
		bundle = ResourceBundle.getBundle("hu.nutty.darts.MessagesBundle.MessagesBundle", locale);
		RootPaneController.setBundle(bundle);
		CreatePlayerController.setBundle(bundle);
		NewGameController.setBundle(bundle);
		DartsMainController.setBundle(bundle);
		SavedStatisticsController.setBundle(bundle);
		DartsMainController.setMain(this);
		SavedStatisticsController.setMain(this);
		Player.setMain(this);
		Game.setMain(this);
		createFolder(PLAYERFOLDER);
		createFolder(SETTINGSFOLDER);
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Darts");
		// AquaFx.style();
		// setUserAgentStylesheet(STYLESHEET_MODENA);
		setUserAgentStylesheet(STYLESHEET_CASPIAN);
		createRootPane();
		showDartsMainOverview();
		createNewGameView();

	}

	public static void main(String[] args) {
		launch(args);
	}

	private void createRootPane() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("hu/nutty/darts/view/RootPane.fxml"));
			rootPane = (BorderPane) loader.load();
			RootPaneController controller = loader.getController();
			controller.setGameController(this);
			Scene scene = new Scene(rootPane);
			// scene.getStylesheets().add("hu/nutty/darts/view/theme.css");
			primaryStage.setMaximized(true);
			primaryStage.setMinWidth(800);
			primaryStage.setMinHeight(600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Doxy Darts");
			primaryStage.setOnCloseRequest(e -> {
				e.consume();
				exitProgram();
			});
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showDartsMainOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("hu/nutty/darts/view/DartsMainView.fxml"));
			BorderPane mainview = (BorderPane) loader.load();
			dmc = loader.getController();
			rootPane.setCenter(mainview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void exitProgram() {
		boolean answer = ConfirmBox.display(bundle.getString("exit"), bundle.getString("exitmessage"));
		if (answer){
			if (player1 != null && player2 != null){
			savePlayerToXML(player1);
			savePlayerToXML(player2);
			}
			primaryStage.close();
		}
	}

	public void createNewGameView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("hu/nutty/darts/view/NewGameView.fxml"));

			BorderPane newGame = (BorderPane) loader.load();
			NewGameController controller = loader.getController();
			controller.setMain(this, dmc);

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("New Game");
			stage.centerOnScreen();
			stage.setResizable(false);
			Scene scene = new Scene(newGame);
			controller.setScene(scene);
			controller.setStage(stage);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getPlayerFolder() {
		return PLAYERFOLDER;
	}

	public String getSettingsFolder() {
		return SETTINGSFOLDER;
	}

	public DartsMainController getDmc() {
		return dmc;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public BorderPane getRootPane() {
		return rootPane;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

}
