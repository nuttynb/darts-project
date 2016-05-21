package hu.nutty.darts.model;

import java.util.List;

import hu.nutty.darts.controller.GameController;
import hu.nutty.darts.model.GameInterface.GameType;

public abstract class Game implements GameInterface{
	
	private GameType gameType;
	private int numberOfPlayers;
	private static GameController gc;
	
	List<Player> players;

	public Game(int numberOfPlayers, List<Player> players, GameType gameType) {
		super();
		this.numberOfPlayers = numberOfPlayers;
		this.players = players;
		this.gameType = gameType;
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	

}
