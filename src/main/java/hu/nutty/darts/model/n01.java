package hu.nutty.darts.model;

import java.util.List;

public class n01 extends Game {

	private boolean doubleIn;
	private boolean doubleOut;
	private int player1Legs;
	private int player2Legs;
	private int player1Sets;
	private int player2Sets;
	
	public n01(int numberOfPlayers, List<Player> players, GameType gameType) {
		super(numberOfPlayers, players, gameType);
		initialize();
	}
	
	public void initialize() {
		this.doubleIn = false;
		this.doubleOut = true;
	
	}
	public void setPlayer1Legs(int leg){
		player1Legs++;
		if (player1Legs % 3 == 0) {
			player1Legs = 0;
			player2Legs = 0;
			player1Sets++;
		}
	}
	public void setPlayer2Legs(int leg){
		player2Legs++;
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
