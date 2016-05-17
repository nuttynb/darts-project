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
import hu.nutty.darts.model.Settings;
import hu.nutty.darts.model.XMLUtil;
import hu.nutty.darts.model.n01;
import hu.nutty.darts.view.AboutController;
import hu.nutty.darts.view.AlertBox;
import hu.nutty.darts.view.ConfirmBox;
import hu.nutty.darts.view.CreatePlayerController;
import hu.nutty.darts.view.CricketMainController;
import hu.nutty.darts.view.DartsMainController;
import hu.nutty.darts.view.NewGameController;
import hu.nutty.darts.view.RootPaneController;
import hu.nutty.darts.view.SavedStatisticsController;
import hu.nutty.darts.view.SettingsController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
	private CricketMainController cmc;
	private RootPaneController rpc;
	private Settings settings = Settings.getInstance();
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

	public void saveSettingsToXML(Settings settings) {
		try {
			XMLUtil.toXML(settings, new FileOutputStream(new File(SETTINGSFOLDER + "settings.xml")));
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
		}
	}

	public Settings loadSettings() throws JAXBException, FileNotFoundException {
		return XMLUtil.fromXML(Settings.class, new FileInputStream(new File(SETTINGSFOLDER + "settings.xml")));
	}

	public Settings loadDefaultSettings() throws JAXBException {
		return XMLUtil.fromXML(Settings.class,
				getClass().getClassLoader().getResourceAsStream("hu/nutty/darts/defaultsettings.xml"));
	}

	public void settingsChosen(boolean englishLang, boolean hungarianLang, boolean modenaTheme, boolean caspianTheme,
			boolean aquaTheme) {
		settings.setFirstStart(false);
		settings.setEnglishLang(englishLang);
		settings.setHungarianLang(hungarianLang);
		settings.setModenaTheme(modenaTheme);
		settings.setCaspianTheme(caspianTheme);
		settings.setAquaTheme(aquaTheme);
		refreshAfterSettings();
		if (player1 != null && player2 != null) {
			dmc.initializeTableValues();
			dmc.refreshStats();
		}
		saveSettingsToXML(settings);
	}

	public void setGameResult(String winner) {
		n01 n01Game = (n01) game;
		if (winner.equals(player1.getNickname()))
			n01Game.setPlayer1Legs(n01Game.getPlayer1Legs() + 1);
		if (winner.equals(player2.getNickname()))
			n01Game.setPlayer2Legs(n01Game.getPlayer2Legs() + 1);

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
				showDartsMainOverview();
				break;
			case _501:
				game = new n01(2, players, gameType);
				showDartsMainOverview();
				break;
			case _1001:
				game = new n01(2, players, gameType);
				showDartsMainOverview();
				break;
			case cricket:
				game = new n01(2, players, gameType);
				showCricketMainOverview();
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
		SettingsController.setMain(this);
		DartsMainController.setMain(this);
		SavedStatisticsController.setMain(this);
		Player.setMain(this);
		Game.setMain(this);
		AboutController.setMain(this);
		createFolder(PLAYERFOLDER);
		createFolder(SETTINGSFOLDER);
		this.primaryStage = primaryStage;
		this.primaryStage.getIcons()
				.add(new Image(this.getClass().getClassLoader().getResourceAsStream("hu/nutty/darts/dartsicon.png")));
		File settingsxml = new File(SETTINGSFOLDER + "settings.xml");
		SettingsController.setBundle(bundle);
		if (!settingsxml.exists()) {
			try {
				settings = loadDefaultSettings();
				saveSettingsToXML(settings);

			} catch (JAXBException e) {
				e.printStackTrace();
			}
			createSettingsView();
		} else
			try {
				settings = loadSettings();
			} catch (FileNotFoundException | JAXBException e) {
				e.printStackTrace();
			}
		RootPaneController.setBundle(bundle);
		NewGameController.setBundle(bundle);
		if (rootPane == null)
			createRootPane();
		refreshAfterSettings();
		createNewGameView();

	}

	public void refreshAfterSettings() {

		if (settings.isEnglishLang()) {
			locale = Locale.ENGLISH;
			bundle = ResourceBundle.getBundle("hu.nutty.darts.MessagesBundle.MessagesBundle", locale);
		} else if (settings.isHungarianLang()) {
			locale = Locale.forLanguageTag("hu");
			bundle = ResourceBundle.getBundle("hu.nutty.darts.MessagesBundle.MessagesBundle", locale);
		}
		if (settings.isModenaTheme()) {
			setUserAgentStylesheet(STYLESHEET_MODENA);
		} else if (settings.isCaspianTheme()) {
			setUserAgentStylesheet(STYLESHEET_CASPIAN);
		} else if (settings.isAquaTheme()) {
			AquaFx.style();
		}
		RootPaneController.setBundle(bundle);
		CreatePlayerController.setBundle(bundle);
		NewGameController.setBundle(bundle);
		DartsMainController.setBundle(bundle);
		SavedStatisticsController.setBundle(bundle);
		SettingsController.setBundle(bundle);
		AboutController.setBundle(bundle);
		if (rootPane == null)
			createRootPane();
		else
			rpc.initialize();
		showDartsMainOverview();

	}

	public static void main(String[] args) {
		launch(args);
	}

	private void createRootPane() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("hu/nutty/darts/view/RootPane.fxml"));
			rootPane = (BorderPane) loader.load();
			rpc = loader.getController();
			rpc.setGameController(this);
			Scene scene = new Scene(rootPane);
			// scene.getStylesheets().add("hu/nutty/darts/view/theme.css");
			primaryStage.setMinWidth(800);
			primaryStage.setMinHeight(600);
			primaryStage.setMaximized(true);
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

	public void showCricketMainOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("hu/nutty/darts/view/CricketMainView.fxml"));
			BorderPane mainview = (BorderPane) loader.load();
			cmc = loader.getController();
			rootPane.setCenter(mainview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void exitProgram() {
		boolean answer = ConfirmBox.display(bundle.getString("exit"), bundle.getString("exitmessage"));
		if (answer) {
			if (player1 != null && player2 != null) {
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
			controller.setMain(this);

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(bundle.getString("newgame"));
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

	public void createSettingsView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("hu/nutty/darts/view/SettingsView.fxml"));
			BorderPane settingsPane = (BorderPane) loader.load();
			SettingsController controller = loader.getController();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(bundle.getString("settings"));
			stage.centerOnScreen();
			stage.setResizable(false);
			Scene scene = new Scene(settingsPane);
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

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

}
