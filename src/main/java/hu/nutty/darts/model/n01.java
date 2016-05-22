package hu.nutty.darts.model;

import java.util.List;

/**
 * Egy "01"-re végződő, hivatalos játékformát leíró modell osztály.
 * 
 * @author nutty
 *
 */
public class n01 extends Game {

	// private boolean doubleIn;
	// private boolean doubleOut;
	private int player1Legs;
	private int player2Legs;
	private int player1Sets;
	private int player2Sets;

	/**
	 * Beállító konstruktor.
	 * 
	 * @param numberOfPlayers
	 *            játékosok száma
	 * @param players
	 *            játékosok listája
	 * @param gameType
	 *            játék típusa
	 */
	public n01(int numberOfPlayers, List<Player> players, GameType gameType) {
		super(numberOfPlayers, players, gameType);
		initialize();
	}

	/**
	 * Inicializáló metódus.
	 */
	public void initialize() {
		// this.doubleIn = false;
		// this.doubleOut = true;
	}

	/**
	 * Beállítja az első játékos nyert legjeinek illetve szettjeinek eredményét.
	 * 
	 * @param leg
	 *            legek száma
	 */
	public void setPlayer1Legs(int leg) {
		player1Legs = leg;
		if (player1Legs % 3 == 0) {
			player1Legs = 0;
			player2Legs = 0;
			player1Sets++;
		}
	}

	/**
	 * Beállítja a második játékos nyert legjeinek illetve szettjeinek
	 * eredményét.
	 * 
	 * @param leg
	 *            legek száma
	 */
	public void setPlayer2Legs(int leg) {
		player2Legs = leg;
		if (player2Legs % 3 == 0) {
			player1Legs = 0;
			player2Legs = 0;
			player2Sets++;
		}
	}

	public int getPlayer1Legs() {
		return player1Legs;
	}

	public int getPlayer2Legs() {
		return player2Legs;
	}

	public int getPlayer1Sets() {
		return player1Sets;
	}

	public int getPlayer2Sets() {
		return player2Sets;
	}

}
