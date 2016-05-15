package hu.nutty.darts.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import hu.nutty.darts.controller.GameController;
import hu.nutty.darts.model.GameInterface.GameType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@javax.xml.bind.annotation.XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Player {
	private GameType gameType = GameType._501;
	private static GameController gc;
	@XmlElement(required = true)
	private String name;
	@XmlElement(required = true)
	private String nickname;
	@XmlElement(required = true)
	private String email;
	@XmlElement(required = true)
	private String level;

	private ObservableList<Throw> throwList;

	@XmlElement(required = true)
	private double savedDartsAvg;
	@XmlElement(required = true)
	private double saved3DartsAvg;
	@XmlElement(required = true)
	private double savedFirstNine;

	@XmlElement(required = true)
	private int savedTons;
	@XmlElement(required = true)
	private int savedTonForty;
	@XmlElement(required = true)
	private int savedTonEighty;
	@XmlElement(required = true)
	private int savedHighOut;
	@XmlElement(required = true)
	private int savedBestDarts;

	private double actualDartsAvg;
	private double actual3DartsAvg;
	private double actualFirstNine;

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
	@XmlElement(required = true)
	private int savedFirstNineThrown;
	@XmlElement(required = true)
	private int savedFirstNineSumScore;
	@XmlElement(required = true)
	private int savedSumScore;
	@XmlElement(required = true)
	private int savedDartsThrown;
	private boolean checkoutZone = false;

	public Player() {
		super();
	}

	public Player(String name, String nickname, String email, String level) {
		super();
		initializeStats();
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.level = level;
		throwList = FXCollections.observableArrayList();

	}

	public static void setMain(GameController _gc) {
		gc = _gc;
	}

	public void initializeStats() {
		this.actualDartsAvg = 0;
		this.actual3DartsAvg = 0;
		this.actualFirstNine = 0;
		this.actualTons = 0;
		this.actualTonForty = 0;
		this.actualTonEighty = 0;
		this.actualHighOut = 0;
		this.actualBestDarts = 0;
		this.actualDartsThrown = 0;
		this.dartsThrownInLeg = 0;
		this.actualSumScore = 0;
		this.actualDartsAvg = 0;
		this.actual3DartsAvg = 0;
		this.checkoutZone = false;
		this.actualFirstNineSumScore = 0;
		this.actualFirstNineThrown = 0;

		throwList = FXCollections.observableArrayList();
		resetThrows();

	}

	public void resetThrows() {
		dartsThrownInLeg = 0;
		checkoutZone = false;
		throwList.clear();
		switch (gameType) {
		case _301:
			throwList.add(new Throw(null, 301));
			break;
		case _501:
			throwList.add(new Throw(null, 501));
			break;
		case _1001:
			throwList.add(new Throw(null, 1001));
			break;
		default:
			break;
		}
	}

	public ObservableList<Throw> getThrowList() {
		return throwList;
	}

	private void calculateStats() {
		if (dartsThrownInLeg <= 9){
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

	public void addThrow(int score) {
		// Integer lastScore = throwList.get(throwList.size() - 1).getScore();

		Integer lastToGo = throwList.get(throwList.size() - 1).getToGo();
		if (score <= 180 && score <= lastToGo && score >= 0) {
			throwList.get(throwList.size() - 1).setScore((lastToGo - score) != 1 ? (score) : 0);
			throwList.add(new Throw(null, (lastToGo - score) != 1 ? (lastToGo - score) : lastToGo));
			dartsThrownInLeg += 3;
			actualDartsThrown += 3;
			savedDartsThrown += 3;
			actualSumScore += score;
			savedSumScore += score;
			if (dartsThrownInLeg <= 9){
				actualFirstNineThrown+=3;
				savedFirstNineThrown+=3;
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
				if (newLastToGo == 0 || (newLastToGo < 40 && newLastToGo % 2 == 1))
					checkoutZone = false;
				if (checkoutZone)
					gc.getDmc().displayCheckoutTable(newLastToGo);
			}

			if (newLastToGo == 0) {
				if (actualBestDarts < dartsThrownInLeg)
					actualBestDarts = dartsThrownInLeg;
				if (savedBestDarts < dartsThrownInLeg)
					savedBestDarts = dartsThrownInLeg;
				if (actualHighOut < lastToGo)
					actualHighOut = lastToGo;
				if (savedHighOut < lastToGo)
					savedHighOut = lastToGo;
				
				gc.getDmc().playerWonLeg(nickname, dartsThrownInLeg);
				gc.setGameResult(nickname);
			}
		} else
			gc.getDmc().invalidThrow();
	}

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public double getActualDartsAvg() {
		return actualDartsAvg;
	}

	public void setActualDartsAvg(double actualDartsAvg) {
		this.actualDartsAvg = actualDartsAvg;
	}

	public double getActual3DartsAvg() {
		return actual3DartsAvg;
	}

	public void setActual3DartsAvg(double actual3DartsAvg) {
		this.actual3DartsAvg = actual3DartsAvg;
	}

	public double getActualFirstNine() {
		return actualFirstNine;
	}

	public void setActualFirstNine(double actualFirstNine) {
		this.actualFirstNine = actualFirstNine;
	}

	public int getActualTons() {
		return actualTons;
	}

	public void setActualTons(int actualTons) {
		this.actualTons = actualTons;
	}

	public int getActualTonForty() {
		return actualTonForty;
	}

	public void setActualTonForty(int actualTonForty) {
		this.actualTonForty = actualTonForty;
	}

	public int getActualTonEighty() {
		return actualTonEighty;
	}

	public void setActualTonEighty(int actualTonEighty) {
		this.actualTonEighty = actualTonEighty;
	}

	public int getActualHighOut() {
		return actualHighOut;
	}

	public void setActualHighOut(int actualHighOut) {
		this.actualHighOut = actualHighOut;
	}

	public int getActualBestDarts() {
		return actualBestDarts;
	}

	public void setActualBestDarts(int actualBestDarts) {
		this.actualBestDarts = actualBestDarts;
	}

	public boolean isCheckoutZone() {
		return checkoutZone;
	}

	public void setCheckoutZone(boolean checkoutZone) {
		this.checkoutZone = checkoutZone;
	}

	public double getSavedDartsAvg() {
		return savedDartsAvg;
	}

	public void setSavedDartsAvg(double savedDartsAvg) {
		this.savedDartsAvg = savedDartsAvg;
	}

	public double getSaved3DartsAvg() {
		return saved3DartsAvg;
	}

	public void setSaved3DartsAvg(double saved3DartsAvg) {
		this.saved3DartsAvg = saved3DartsAvg;
	}

	public double getSavedFirstNine() {
		return savedFirstNine;
	}

	public void setSavedFirstNine(double savedFirstNine) {
		this.savedFirstNine = savedFirstNine;
	}

	public int getSavedTons() {
		return savedTons;
	}

	public void setSavedTons(int savedTons) {
		this.savedTons = savedTons;
	}

	public int getSavedTonForty() {
		return savedTonForty;
	}

	public void setSavedTonForty(int savedTonForty) {
		this.savedTonForty = savedTonForty;
	}

	public int getSavedTonEighty() {
		return savedTonEighty;
	}

	public void setSavedTonEighty(int savedTonEighty) {
		this.savedTonEighty = savedTonEighty;
	}

	public int getSavedHighOut() {
		return savedHighOut;
	}

	public void setSavedHighOut(int savedHighOut) {
		this.savedHighOut = savedHighOut;
	}

	public int getSavedBestDarts() {
		return savedBestDarts;
	}

	public void setSavedBestDarts(int savedBestDarts) {
		this.savedBestDarts = savedBestDarts;
	}

}
