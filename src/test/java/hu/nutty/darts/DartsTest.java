package hu.nutty.darts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import hu.nutty.darts.controller.GameController;
import hu.nutty.darts.controller.GameService;
import hu.nutty.darts.model.GameInterface;
import hu.nutty.darts.model.Player;
import hu.nutty.darts.model.n01;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DartsTest {
	private static Player wizard;
	private static Player power;
	private static List<Player> players;
	private static n01 game;
	private static GameService gs;
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
		players.add(wizard);
		players.add(power);
		game = new n01(2, players, GameInterface.GameType._501);
		gs = new GameService(game);
		gs.addThrow(181,0);
		gs.addThrow(180,0);
		gs.addThrow(180,1);
		gs.addThrow(180,0);
		gs.addThrow(0,1);
		gs.addThrow(101,0);
		gs.addThrow(100,1);
	}

	@Test
	public void test1_FirstPlayerToGo() {
		assertEquals("", 40,  (long)wizard.getThrowList().get(wizard.getThrowList().size()-1).getToGo());
	}
	@Test
	public void test2_SecondPlayerToGo() {
		assertEquals("", 221,  (long)power.getThrowList().get(power.getThrowList().size()-1).getToGo());
	}
	@Test
	public void test3_FirstPlayerScore() {
		assertEquals("", 101,  (long)wizard.getThrowList().get(wizard.getThrowList().size()-2).getScore());
	}
	@Test
	public void test4_SecondPlayerScore() {
		assertEquals("", 100,  (long)power.getThrowList().get(power.getThrowList().size()-2).getScore());
	}
	@Test
	public void test5_LegResult() {
		gs.setGameResult("The Wizard");
		assertEquals("", 1,  game.getPlayer1Legs());
		assertEquals("", 0, game.getPlayer2Legs());
	}

}
