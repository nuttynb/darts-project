package hu.nutty.darts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import hu.nutty.darts.controller.GameController;
import hu.nutty.darts.model.Game;
import hu.nutty.darts.model.GameInterface;
import hu.nutty.darts.model.Player;
import hu.nutty.darts.model.n01;

public class DartsTest {
	private static Player wizard;
	private static Player power;
	private static List<Player> players;
	private static n01 game;
	private static GameController gc;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gc = new GameController();
		wizard = new Player("Simon Whitlock", "The Wizard", "test@test.hu", "professional");
		power = new Player("Phil Taylor", "The Power", "test2@test.hu", "professional");
		wizard.setGameType(GameInterface.GameType._501);
		power.setGameType(GameInterface.GameType._501);
		wizard.initializeStats();
		power.initializeStats();
		players = new ArrayList<>();
		gc.setPlayer1(wizard);
		gc.setPlayer2(power);
		players.add(wizard);
		players.add(power);
		game = new n01(2, players, GameInterface.GameType._501);
		gc.setGame(game);
		wizard.addThrow(181);
		wizard.addThrow(180);
		power.addThrow(180);
		wizard.addThrow(180);
		power.addThrow(0);
		wizard.addThrow(141);
		gc.setGameResult("The Wizard");
	}

	@Test
	public void testToGo() {
		assertEquals("", 0,  (long)wizard.getThrowList().get(wizard.getThrowList().size()-1).getToGo());
		assertEquals("", 321,  (long)power.getThrowList().get(power.getThrowList().size()-1).getToGo());
	}
	@Test
	public void testLegResult() {
		assertEquals("", 1,  game.getPlayer1Legs());
		assertEquals("", 0, game.getPlayer2Legs());
	}

}
