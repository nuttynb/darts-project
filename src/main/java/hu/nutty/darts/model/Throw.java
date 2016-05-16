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
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getToGo() {
		return toGo;
	}
	public void setToGo(Integer toGo) {
		this.toGo = toGo;
	}
	
}
