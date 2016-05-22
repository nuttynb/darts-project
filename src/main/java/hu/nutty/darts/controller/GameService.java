package hu.nutty.darts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hu.nutty.darts.model.Game;
import hu.nutty.darts.model.Player;
import hu.nutty.darts.model.Throw;
import hu.nutty.darts.model.n01;
import javafx.collections.ObservableList;

/**
 * A játékos dobásait vezérlő osztály.
 * 
 * @author nutty
 *
 */
public class GameService {
	private Game game;
	private static GameController gc;
	private static Logger logger = LoggerFactory.getLogger(GameService.class);

	private double actualDartsAvg;
	private double actual3DartsAvg;
	private double actualFirstNine;
	private double savedDartsAvg;
	private double saved3DartsAvg;
	private double savedFirstNine;

	private int actualTons;
	private int actualTonForty;
	private int actualTonEighty;
	private int actualHighOut;
	private int actualBestDarts;
	private int actualDartsThrown;
	private int dartsThrownInLeg;
	private int actualSumScore;
	private int actualFirstNineSumScore;
	private int actualFirstNineThrown;
	private int savedSumScore;
	private int savedFirstNineSumScore;
	private int savedFirstNineThrown;
	private int savedTons;
	private int savedTonForty;
	private int savedTonEighty;
	private int savedDartsThrown;
	private int savedHighOut;
	private int savedBestDarts;
	private boolean checkoutZone = false;
	private boolean playerWon = false;

	/**
	 * Konstruktor.
	 * 
	 * @param game
	 *            a vezérlendő játék
	 */
	public GameService(Game game) {
		super();
		this.game = game;
	}

	/**
	 * A main GameController referenciájának átadása.
	 * 
	 * @param _gc
	 *            main class referencia
	 */
	public static void setMain(GameController _gc) {
		gc = _gc;
	}

	private void initialize(int playerToThrow) {
		Player player = game.getPlayers().get(playerToThrow);
		this.actualDartsAvg = player.getActualDartsAvg();
		this.actual3DartsAvg = player.getActual3DartsAvg();
		this.actualFirstNine = player.getActualFirstNine();
		this.savedDartsAvg = player.getSavedDartsAvg();
		this.saved3DartsAvg = player.getSaved3DartsAvg();
		this.savedFirstNine = player.getSavedFirstNine();
		this.actualTons = player.getActualTons();
		this.actualTonForty = player.getActualTonForty();
		this.actualTonEighty = player.getActualTonEighty();
		this.savedTons = player.getSavedTons();
		this.savedTonForty = player.getSavedTonForty();
		this.savedTonEighty = player.getSavedTonEighty();
		this.actualHighOut = player.getActualHighOut();
		this.actualBestDarts = player.getActualBestDarts();
		this.savedHighOut = player.getSavedHighOut();
		this.savedBestDarts = player.getSavedBestDarts();
		this.actualDartsThrown = player.getActualDartsThrown();
		this.savedDartsThrown = player.getSavedDartsThrown();
		this.dartsThrownInLeg = player.getDartsThrownInLeg();
		this.actualSumScore = player.getActualSumScore();
		this.savedSumScore = player.getSavedSumScore();
		this.checkoutZone = false;
		this.actualFirstNineSumScore = player.getActualFirstNineSumScore();
		this.actualFirstNineThrown = player.getActualFirstNineThrown();
		this.savedFirstNineSumScore = player.getSavedFirstNineSumScore();
		this.savedFirstNineThrown = player.getSavedFirstNineThrown();
	}

	private void setPlayerStatistics(int playerToThrow) {
		Player player = game.getPlayers().get(playerToThrow);
		player.setActualDartsAvg(actualDartsAvg);
		player.setActual3DartsAvg(actual3DartsAvg);
		player.setActualFirstNine(actualFirstNine);
		player.setSavedDartsAvg(savedDartsAvg);
		player.setSaved3DartsAvg(saved3DartsAvg);
		player.setSavedFirstNine(savedFirstNine);
		player.setActualTons(actualTons);
		player.setActualTonForty(actualTonForty);
		player.setActualTonEighty(actualTonEighty);
		player.setSavedTons(savedTons);
		player.setSavedTonForty(savedTonForty);
		player.setSavedTonEighty(savedTonEighty);
		player.setActualHighOut(actualHighOut);
		player.setActualBestDarts(actualBestDarts);
		player.setSavedHighOut(savedHighOut);
		player.setSavedBestDarts(savedBestDarts);
		player.setActualDartsThrown(actualDartsThrown);
		player.setSavedDartsThrown(savedDartsThrown);
		player.setDartsThrownInLeg(dartsThrownInLeg);
		player.setActualSumScore(actualSumScore);
		player.setSavedSumScore(savedSumScore);
		player.setCheckoutZone(checkoutZone);
		player.setActualFirstNineSumScore(actualFirstNineSumScore);
		player.setActualFirstNineThrown(actualFirstNineThrown);
		player.setSavedFirstNineSumScore(savedFirstNineSumScore);
		player.setSavedFirstNineThrown(savedFirstNineThrown);

	}

	/**
	 * Statisztika kiszámolása.
	 */
	public void calculateStats() {
		if (dartsThrownInLeg <= 9) {
			actualFirstNine = (double) actualFirstNineSumScore / (double) actualFirstNineThrown * 3.0;
			actualFirstNine = Math.floor(actualFirstNine * 100) / 100;
			savedFirstNine = (double) savedFirstNineSumScore / (double) savedFirstNineThrown * 3.0;
			savedFirstNine = Math.floor(savedFirstNine * 100) / 100;
		}
		actualDartsAvg = (double) actualSumScore / (double) actualDartsThrown;
		actual3DartsAvg = actualDartsAvg * 3.0;
		actualDartsAvg = Math.floor(actualDartsAvg * 100) / 100;
		actual3DartsAvg = Math.floor(actual3DartsAvg * 100) / 100;
		savedDartsAvg = (double) savedSumScore / (double) savedDartsThrown;
		saved3DartsAvg = savedDartsAvg * 3.0;
		savedDartsAvg = Math.floor(savedDartsAvg * 100) / 100;
		saved3DartsAvg = Math.floor(saved3DartsAvg * 100) / 100;
	}

	/**
	 * Újabb dobott értéket ad hozzá a listához, közben a statisztikát
	 * módosítja.
	 * 
	 * @param score
	 *            a dobott érték a körben
	 * @param player
	 *            melyik játékos dobta (index)
	 */
	public void addThrow(int score, int player) {
		playerWon = false;
		ObservableList<Throw> throwList = game.getPlayers().get(player).getThrowList();
		initialize(player);
		Integer lastToGo = throwList.get(throwList.size() - 1).getToGo();
		if (score <= 180 && score <= lastToGo && score >= 0) {
			throwList.get(throwList.size() - 1).setScore((lastToGo - score) != 1 ? (score) : 0);
			throwList.add(new Throw(null, (lastToGo - score) != 1 ? (lastToGo - score) : lastToGo));
			logger.info(game.getPlayers().get(player).getNickname() + " threw "
					+ ((lastToGo - score) != 1 ? (score) : 0) + " score.");
			dartsThrownInLeg += 3;
			actualDartsThrown += 3;
			savedDartsThrown += 3;
			actualSumScore += score;
			savedSumScore += score;
			if (dartsThrownInLeg <= 9) {
				actualFirstNineThrown += 3;
				savedFirstNineThrown += 3;
				actualFirstNineSumScore += score;
				savedFirstNineSumScore += score;
			}
			if (score == 180) {
				actualTonEighty++;
				savedTonEighty++;
			} else if (score >= 140) {
				actualTonForty++;
				savedTonForty++;
			} else if (score >= 100) {
				actualTons++;
				savedTons++;
			}
			calculateStats();
			Integer newLastToGo = throwList.get(throwList.size() - 1).getToGo();
			if (newLastToGo <= 158 || newLastToGo == 170 || newLastToGo == 167 || newLastToGo == 164
					|| newLastToGo == 161 || newLastToGo == 160) {
				checkoutZone = true;
				if (gc != null && newLastToGo == 0 || (newLastToGo < 40 && newLastToGo % 2 == 1)) {
					checkoutZone = false;
					gc.getDmc().clearCheckout(game.getPlayers().get(player).getNickname());
				}
				if (checkoutZone && gc != null)
					gc.getDmc().displayCheckoutTable(newLastToGo);
			}

			if (newLastToGo == 0) {
				playerWon = true;
				if (actualBestDarts > dartsThrownInLeg)
					actualBestDarts = dartsThrownInLeg;
				if (savedBestDarts < dartsThrownInLeg)
					savedBestDarts = dartsThrownInLeg;
				if (actualHighOut < lastToGo)
					actualHighOut = lastToGo;
				if (savedHighOut < lastToGo)
					savedHighOut = lastToGo;
				if (gc != null) {
					gc.getDmc().playerWonLeg(game.getPlayers().get(player).getNickname(), dartsThrownInLeg);
					setGameResult(game.getPlayers().get(player).getNickname());
				}
			}
		} else if (gc != null) {
			gc.getDmc().invalidThrow();
			logger.warn(
					"Invalid throw by " + game.getPlayers().get(player).getNickname() + " with " + score + " score.");
		}
		setPlayerStatistics(player);
		if (playerWon)
			game.getPlayers().get(player).resetThrows();

	}

	/**
	 * Beállítja a meccs állását: legek, szettek.
	 * 
	 * @param winner
	 *            aki nyerte az adott leget (becenév)
	 */
	public void setGameResult(String winner) {
		n01 n01Game = (n01) game;
		logger.info(winner + " won the leg.");
		if (winner.equals(game.getPlayers().get(0).getNickname())) {
			n01Game.setPlayer1Legs(n01Game.getPlayer1Legs() + 1);
		}
		if (winner.equals(game.getPlayers().get(1).getNickname())) {
			n01Game.setPlayer2Legs(n01Game.getPlayer2Legs() + 1);
		}
		logger.info("Score is: " + n01Game.getPlayer1Legs() + " - " + n01Game.getPlayer2Legs());
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
