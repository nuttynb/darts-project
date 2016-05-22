package hu.nutty.darts.model;

import java.util.List;
import hu.nutty.darts.controller.GameController;
import hu.nutty.darts.model.GameInterface.GameType;

/**
 * Egy darts játék absztrakt tagjait leíró és beállító osztály. Ez az osztály
 * további funkciókkal való bővítéshez szükséges. Absztrakt osztály, csak az ezt
 * kiterjesztő osztályokat páldányosítjuk.
 * 
 * @author nutty
 *
 */
public abstract class Game implements GameInterface {

	private GameType gameType;
	private int numberOfPlayers;

	List<Player> players;

	/**
	 * 3 paraméteres konstruktor.
	 * 
	 * @param numberOfPlayers
	 *            játékosok száma
	 * @param players
	 *            játékosok egy listája
	 * @param gameType
	 *            játék típusa
	 */
	public Game(int numberOfPlayers, List<Player> players, GameType gameType) {
		super();
		this.numberOfPlayers = numberOfPlayers;
		this.players = players;
		this.gameType = gameType;
	}

	/**
	 * Visszaadja a Dartsban játszó játékosok listáját.
	 * 
	 * @return Játékosok listája.
	 */
	public List<Player> getPlayers() {
		return players;
	}

}
