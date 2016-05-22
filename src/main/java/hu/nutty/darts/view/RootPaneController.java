package hu.nutty.darts.view;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import hu.nutty.darts.controller.GameController;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RootPaneController {
	private GameController gc;
	private static ResourceBundle bundle;

	public void setGameController(GameController gc) {
		this.gc = gc;
	}

	public static void setBundle(ResourceBundle _bundle) {
		bundle = _bundle;
	}

	@FXML
	private MenuBar menuBar;
	@FXML
	private Menu fileMenu;
	@FXML
	private Menu helpMenu;
	@FXML
	private MenuItem newGameItem;
	@FXML
	private MenuItem exitItem;
	@FXML
	private MenuItem snapshotItem;
	@FXML
	private MenuItem showStatsItem;
	@FXML
	private MenuItem settingsItem;
	@FXML
	private MenuItem aboutItem;

	@FXML
	public void initialize() {
		fileMenu.setText(bundle.getString("filemenu"));
		helpMenu.setText(bundle.getString("helpmenu"));
		aboutItem.setText(bundle.getString("aboutmenu"));
		newGameItem.setText(bundle.getString("newgamemenu"));
		exitItem.setText(bundle.getString("exitmenu"));
		snapshotItem.setText(bundle.getString("snapshot"));
		showStatsItem.setText(bundle.getString("showstatsmenu"));
		settingsItem.setText(bundle.getString("settingsmenu"));
		snapshotItem.setDisable(true);
	}

	public void setSnapshotEnable() {
		snapshotItem.setDisable(false);
	}

	@FXML
	private void openNewGameView() {
		gc.createNewGameView();
	}

	@FXML
	private void handleExitItem() {
		gc.exitProgram();
	}

	@SuppressWarnings("static-access")
	@FXML
	public void takeSnapshot() {
		gc.createFolder(gc.SNAPSHOTS);
		WritableImage image = gc.getRootPane().snapshot(new SnapshotParameters(), null);
		File file = new File(gc.SNAPSHOTS + gc.getPlayer1().getNickname() + "_vs_" + gc.getPlayer2().getNickname() + "_"
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + ".png");

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException e) {
		}
	}

	@FXML
	public void handleShowStats() {
		try {
			if (gc.getPlayer1() != null && gc.getPlayer2() != null) {
				gc.savePlayerToXML(gc.getPlayer1());
				gc.savePlayerToXML(gc.getPlayer2());
			}
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("hu/nutty/darts/view/SavedStatisticsView.fxml"));
			BorderPane stats = (BorderPane) loader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(bundle.getString("statistics"));
			stage.centerOnScreen();
			Scene scene = new Scene(stats);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleAbout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("hu/nutty/darts/view/AboutView.fxml"));
			BorderPane about = (BorderPane) loader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(bundle.getString("about"));
			stage.centerOnScreen();
			stage.setResizable(false);
			AboutController controller = loader.getController();
			controller.setStage(stage);
			Scene scene = new Scene(about);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void settingsOnClicked() {
		gc.createSettingsView();
	}
}
