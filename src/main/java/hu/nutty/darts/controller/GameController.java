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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A modellosztályok és a UI közötti vezérlő osztály.
 * 
 * @author nutty
 *
 */
public class GameController extends Application {

	private Stage primaryStage;
	private BorderPane rootPane;
	private Locale locale;
	private ResourceBundle bundle;
	private DartsMainController dmc;
	@SuppressWarnings("unused")
	private CricketMainController cmc;
	private GameService gs;
	private RootPaneController rpc;
	private Settings settings = Settings.getInstance();
	private static Logger logger = LoggerFactory.getLogger(GameController.class);
	List<Player> players = new ArrayList<>();

	public final static String PLAYERFOLDER = "darts_files/players/";
	public final static String LOGFOLDER = "darts_files/logs/";
	public final static String SETTINGSFOLDER = "darts_files/settings/";
	public final static String SNAPSHOTS = "darts_files/snapshots/";

	private void saveSettingsToXML(Settings settings) {
		try {
			XMLUtil.toXML(settings, new FileOutputStream(new File(SETTINGSFOLDER + "settings.xml")));
			logger.info("Settings successfully saved.");
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
			logger.error("Settings failed to save: " + e.getMessage());
		}
	}

	private Settings loadSettings() throws JAXBException, FileNotFoundException {
		logger.info("Loading settings...");
		return XMLUtil.fromXML(Settings.class, new FileInputStream(new File(SETTINGSFOLDER + "settings.xml")));
	}

	private Settings loadDefaultSettings() throws JAXBException {
		logger.info("Loading default settings...");
		return XMLUtil.fromXML(Settings.class,
				getClass().getClassLoader().getResourceAsStream("hu/nutty/darts/defaultsettings.xml"));
	}

	private void refreshAfterSettings() {

		if (settings.isEnglishLang()) {
			locale = Locale.ENGLISH;
			bundle = ResourceBundle.getBundle("hu.nutty.darts.MessagesBundle.MessagesBundle", locale);
		} else {
			if (settings.isHungarianLang()) {
				locale = Locale.forLanguageTag("hu");
				bundle = ResourceBundle.getBundle("hu.nutty.darts.MessagesBundle.MessagesBundle", locale);
			}
		}
		if (settings.isModenaTheme()) {
			setUserAgentStylesheet(STYLESHEET_MODENA);
		} else {
			if (settings.isCaspianTheme()) {
				setUserAgentStylesheet(STYLESHEET_CASPIAN);
			} else {
				if (settings.isAquaTheme()) {
					AquaFx.style();
				}
			}
		}
		RootPaneController.setBundle(bundle);
		CreatePlayerController.setBundle(bundle);
		NewGameController.setBundle(bundle);
		DartsMainController.setBundle(bundle);
		SavedStatisticsController.setBundle(bundle);
		SettingsController.setBundle(bundle);
		AboutController.setBundle(bundle);
		if (rootPane == null) {
			createRootPane();
		} else {
			rpc.initialize();
		}
		showDartsMainOverview();

	}

	/**
	 * Beállítja a UI-on, hogy mostmár lehet képernyőképet készíteni.
	 */
	public void setSnapshotEnable() {
		rpc.setSnapshotEnable();
	}

	/**
	 * A paraméterként megadott játékos perzisztálása.
	 * 
	 * @param player
	 *            kiírandó játékos
	 */
	public void savePlayerToXML(Player player) {
		try {
			XMLUtil.toXML(player, new FileOutputStream(new File(PLAYERFOLDER + player.getNickname() + ".xml")));
			logger.info(player.getNickname() + " saved to XML.");
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
			logger.error(player.getNickname() + "failed to save: " + e.getMessage());
		}
	}

	/**
	 * Beállítja a UI-ról érkező adatokkal a program beállításait.
	 * 
	 * @param englishLang
	 *            angol nyelv
	 * @param hungarianLang
	 *            magyar nyelv
	 * @param modenaTheme
	 *            modena téma
	 * @param caspianTheme
	 *            caspian téma
	 * @param aquaTheme
	 *            AquaFX téma
	 */
	public void settingsChosen(boolean englishLang, boolean hungarianLang, boolean modenaTheme, boolean caspianTheme,
			boolean aquaTheme) {
		settings.setFirstStart(false);
		settings.setEnglishLang(englishLang);
		settings.setHungarianLang(hungarianLang);
		settings.setModenaTheme(modenaTheme);
		settings.setCaspianTheme(caspianTheme);
		settings.setAquaTheme(aquaTheme);
		refreshAfterSettings();
		if (getPlayer1() != null && getPlayer2() != null) {
			dmc.initializeTableValues();
			dmc.refreshStats();
			rpc.setSnapshotEnable();
		}
		saveSettingsToXML(settings);
	}

	/**
	 * Inicializál egy új játékot az UI-ról érkező adatokkal.
	 * 
	 * @param player1name
	 *            első importálandó játékos nickneve (.xml neve)
	 * @param player2name
	 *            második importálandó játékos nickneve (.xml neve)
	 * @param gameType
	 *            játék típusa
	 */
	public void newGameSelectedItems(String player1name, String player2name, GameInterface.GameType gameType) {
		try {
			logger.info("Loading first player...");
			Player player1 = XMLUtil.fromXML(Player.class,
					new FileInputStream(new File(PLAYERFOLDER + player1name + ".xml")));
			logger.info("First player successfully loaded.");
			logger.info("Loading second player...");
			Player player2 = XMLUtil.fromXML(Player.class,
					new FileInputStream(new File(PLAYERFOLDER + player2name + ".xml")));
			logger.info("Second player successfully loaded.");
			player1.setGameType(gameType);
			player2.setGameType(gameType);
			player1.initializeStats();
			player2.initializeStats();
			players.clear();
			players.add(player1);
			players.add(player2);
			Game game = null;
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
			gs = new GameService(game);
			dmc.initialize();
			logger.info("A new " + gameType + " game started between " + player1name + " and " + player2name + ".");
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
			logger.error("Failed to start game: " + e.getMessage());
		}

	}

	/**
	 * Ez a metódus hozza létre a beadott útvonalon lévő mappákat.
	 * 
	 * @param folder
	 *            mappák útvonala amit létre szeretnénk hozni
	 */
	public void createFolder(String folder) {
		File fileFolders = new File(folder);

		if (!fileFolders.exists() || !fileFolders.isDirectory()) {
			logger.info("Folders do not exist.");
			boolean success = fileFolders.mkdirs();
			logger.info("Folders created.");
			if (!success) {
				AlertBox.display(bundle.getString("error"), bundle.getString("folder"));
				logger.error("Failed to create folders. Exitting program...");
				System.exit(1);
			}
		}
	}

	/**
	 * Visszaadja a játékosok beceneveit egy ObservableListben.
	 * 
	 * @return becenevek listája
	 */
	public static ObservableList<String> getPlayerNames() {
		return XMLUtil.getExistingPlayerNames(PLAYERFOLDER);
	}

	/**
	 * Betölti az összes játékost egy listába.
	 * 
	 * @return játékosok listája
	 */
	public static ObservableList<Player> getAllPlayers() {
		return XMLUtil.getAllPlayers(PLAYERFOLDER);
	}

	@Override
	/**
	 * JAVAFX specifikus indítás. Beállítjuk az ablak(ok)at, valamint az
	 * alapértelmezett localization nyelvet.
	 */
	public void start(Stage primaryStage) {
		locale = Locale.getDefault();
		bundle = ResourceBundle.getBundle("hu.nutty.darts.MessagesBundle.MessagesBundle", locale);
		SettingsController.setMain(this);
		DartsMainController.setMain(this);
		SavedStatisticsController.setMain(this);
		GameService.setMain(this);
		AboutController.setMain(this);
		createFolder(PLAYERFOLDER);
		createFolder(SETTINGSFOLDER);
		createFolder(LOGFOLDER);
		this.primaryStage = primaryStage;
		this.primaryStage.getIcons()
				.add(new Image(this.getClass().getClassLoader().getResourceAsStream("hu/nutty/darts/dartsicon.png")));
		File settingsxml = new File(SETTINGSFOLDER + "settings.xml");
		SettingsController.setBundle(bundle);
		if (!settingsxml.exists()) {
			try {
				settings = loadDefaultSettings();
				logger.info("Default settings successfully loaded.");
				saveSettingsToXML(settings);

			} catch (JAXBException e) {
				logger.error("Default settings failed to load: " + e.getMessage());
				e.printStackTrace();
			}
			createSettingsView();
		} else {
			try {
				settings = loadSettings();
				logger.info("Settings loaded.");
			} catch (FileNotFoundException | JAXBException e) {
				logger.error("Settings failed to load: " + e.getMessage());
				e.printStackTrace();
			}
		}
		RootPaneController.setBundle(bundle);
		NewGameController.setBundle(bundle);
		if (rootPane == null) {
			createRootPane();
		}
		refreshAfterSettings();
		createNewGameView();

	}

	/**
	 * Main metódus.
	 * 
	 * @param args
	 *            paraméterként beadott argumentumok tömbje
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * A program kilépésénél lefutó metódus. A menüből kilépve illetve az
	 * ablakon lévő kilépés gombnál is. Elmenti a játékosokat.
	 */
	public void exitProgram() {
		boolean answer = ConfirmBox.display(bundle.getString("exit"), bundle.getString("exitmessage"));
		if (answer) {
			if (getPlayer1() != null && getPlayer2() != null) {
				savePlayerToXML(getPlayer1());
				savePlayerToXML(getPlayer2());
			}
			logger.info("Exitting program...");
			primaryStage.close();
		}
	}

	private void createRootPane() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("hu/nutty/darts/view/RootPane.fxml"));
			rootPane = (BorderPane) loader.load();
			rpc = loader.getController();
			rpc.setGameController(this);
			Scene scene = new Scene(rootPane);
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

	private void showDartsMainOverview() {
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

	private void showCricketMainOverview() {
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

	/**
	 * Betölti az FXML-ből az Új játék ablakának beállításait, új ablakot hoz
	 * létre.
	 */
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

	/**
	 * Betölti az FXML-ből a Beállítások ablakának tulajdonságait, új ablakot
	 * hoz létre.
	 */
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
			controller.setStage(stage);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Game getGame() {
		return gs.getGame();
	}

	/**
	 * Beállítja a játékot.
	 * 
	 * @param game
	 *            játék
	 */
	public void setGame(Game game) {
		gs.setGame(game);
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
		return this.primaryStage;
	}

	public BorderPane getRootPane() {
		return this.rootPane;
	}

	/**
	 * Visszaadja az első játékost, ha van olyan.
	 * 
	 * @return első játékos
	 */
	public Player getPlayer1() {
		if (gs != null) {
			return gs.getGame().getPlayers().get(0);
		} else {
			return null;
		}
	}

	/**
	 * Visszaadja a második játékost, ha van olyan.
	 * 
	 * @return második játékos
	 */
	public Player getPlayer2() {
		if (gs != null) {
			return gs.getGame().getPlayers().get(1);
		} else {
			return null;
		}
	}

	/**
	 * Beállítja az első játékost.
	 * 
	 * @param player1
	 *            első játékos
	 */
	public void setPlayer1(Player player1) {
		gs.getGame().getPlayers().add(player1);
	}

	/**
	 * Beállítja a második játékost.
	 * 
	 * @param player2
	 *            második játékos
	 */
	public void setPlayer2(Player player2) {
		gs.getGame().getPlayers().add(player2);
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public GameService getGs() {
		return this.gs;
	}

}
