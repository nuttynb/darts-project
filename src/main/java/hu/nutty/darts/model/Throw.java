package hu.nutty.darts.model;
/**
 * Egy dobást reprezentáló osztály.
 * Ennek segítségével megfelelően lehet megjeleníteni a táblázatban az értékeket.
 * @author nutty
 * 
 */
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
	/**
	 * Visszaadja az adott körbeli dobás értékét.
	 * @return a dobás pontszáma
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * Beállítja a körben dobott pontot.
	 * @param score dobás pontszáma
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	/**
	 * Visszaadja a hátralévő pontmennyiséget.
	 * @return annyi pont amennyi a kiszálláshoz kell
	 */
	public Integer getToGo() {
		return toGo;
	}
	/**
	 * Beállítja a hátralévő pontmennyiséget.
	 * @param toGo annyi pont amennyi a kiszálláshoz kell
	 */
	public void setToGo(Integer toGo) {
		this.toGo = toGo;
	}
	
}
