package hu.nutty.darts.model;

public class Throw {
	
	Integer score;
	Integer toGo;

	
	public Throw() {
		this.score = null;
		this.toGo = null;
	}
	
	public Throw(Integer score, Integer toGo) {
		super();
		this.score = score;
		this.toGo = toGo;
	}

	public Integer getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Integer getToGo() {
		return toGo;
	}
	public void setToGo(int toGo) {
		this.toGo = toGo;
	}
	
}
