package hu.nutty.darts.model;

/**
 * Egy dartsot általánosan leíró interfész.
 * 
 * @author nutty
 *
 */
public interface GameInterface {
	/**
	 * Játéktípusok halmaza.
	 * 
	 * @author nutty
	 *
	 */
	enum GameType {
		_301, _501, _1001, cricket
	}

	/**
	 * Inicializáló metódus.
	 */
	void initialize();
}
