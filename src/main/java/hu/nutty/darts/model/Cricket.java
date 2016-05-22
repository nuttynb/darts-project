package hu.nutty.darts.model;

import java.util.List;

/**
 * Egy krikett típusú játékhoz szükséges modell osztály. Jelenleg nem tartalmaz
 * semmit.
 * 
 * @author nutty
 *
 */
public class Cricket extends Game {
	/**
	 * Konstruktor, ami továbbadja az ősosztálynak a paramétereket.
	 * 
	 * @param numberOfPlayers
	 *            játékosok száma
	 * @param players
	 *            játékosok listája
	 */
	public Cricket(int numberOfPlayers, List<Player> players) {
		super(numberOfPlayers, players, GameType.cricket);
	}

	public void initialize() {

	}
}
