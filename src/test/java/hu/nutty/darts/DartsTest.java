package hu.nutty.darts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import hu.nutty.darts.controller.GameController;
import hu.nutty.darts.controller.GameService;
import hu.nutty.darts.model.GameInterface;
import hu.nutty.darts.model.Player;
import hu.nutty.darts.model.Settings;
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
		gc.setSettings(Settings.getInstance());
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
	public void test01_FirstPlayerToGo() {
		assertEquals("Failure! First player last ToGo is incorrect.", 40,  (long)wizard.getThrowList().get(wizard.getThrowList().size()-1).getToGo());
	}
	@Test
	public void test02_SecondPlayerToGo() {
		assertEquals("Failure! Second player last ToGo is incorrect.", 221,  (long)power.getThrowList().get(power.getThrowList().size()-1).getToGo());
	}
	@Test
	public void test03_FirstPlayerScore() {
		assertEquals("Failure! First player last Score is incorrect.", 101,  (long)wizard.getThrowList().get(wizard.getThrowList().size()-2).getScore());
	}
	@Test
	public void test04_SecondPlayerScore() {
		assertEquals("Failure! Second player last Score is incorrect.", 100,  (long)power.getThrowList().get(power.getThrowList().size()-2).getScore());
	}
	@Test
	public void test05_FirstPlayerAvg() {
		assertEquals("Failure! First player average is incorrect.", 51.22,  (double)wizard.getActualDartsAvg(),0.01);
	}
	@Test
	public void test06_FirstPlayerAvg() {
		assertEquals("Failure! Second player average is incorrect.", 31.11,  (double)power.getActualDartsAvg(),0.01);
	}
	@Test
	public void test07_FirstPlayer3Avg() {
		assertEquals("Failure! First player 3 dart average is incorrect.", 153.66,  (double)wizard.getActual3DartsAvg(),0.01);
	}
	@Test
	public void test08_FirstPlayer3Avg() {
		assertEquals("Failure! Second player 3 dart average is incorrect.", 93.33,  (double)power.getActual3DartsAvg(),0.01);
	}
	
	@Test
	public void test09_LegResult() {
		gs.setGameResult("The Wizard");
		assertEquals(1,  game.getPlayer1Legs());
		assertEquals(0, game.getPlayer2Legs());
		gs.setGameResult("The Power");
		assertEquals(1, game.getPlayer2Legs());
		gs.setGameResult("The Wizard");
		gs.setGameResult("The Wizard");
		assertEquals(0,  game.getPlayer1Legs());
		assertEquals(0, game.getPlayer2Legs());
		assertEquals(1,  game.getPlayer1Sets());
		assertEquals(0, game.getPlayer2Sets());
	}
	@Test
	public void test10_doubleOutCheck() {
		gs.addThrow(39,0);
		assertEquals("Failure! First player last ToGo is incorrect.", 40,  (long)wizard.getThrowList().get(wizard.getThrowList().size()-1).getToGo());
	}

}
