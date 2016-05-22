package hu.nutty.darts.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * A beállításokat tartalmazó osztály, perzisztáláshoz.
 * 
 * @author nutty
 *
 */
@javax.xml.bind.annotation.XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Settings {
	private static Settings instance = null;
	@XmlElement(required = true)
	private boolean firstStart;
	@XmlElement(required = true)
	private boolean modenaTheme;
	@XmlElement(required = true)
	private boolean caspianTheme;
	@XmlElement(required = true)
	private boolean aquaTheme;
	@XmlElement(required = true)
	private boolean hungarianLang;
	@XmlElement(required = true)
	private boolean englishLang;

	/**
	 * Private metódus singleton létrehozásához.
	 */
	private Settings() {
		this.firstStart = true;
		this.modenaTheme = true;
		this.englishLang = true;
		this.caspianTheme = false;
		this.aquaTheme = false;
		this.hungarianLang = false;
	}

	/**
	 * Lazy singleton, egy példányt fog visszaadni.
	 * 
	 * @return ugyanazt a settings példányt adja vissza
	 */
	public static Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}
		return instance;
	}

	/**
	 * Visszaadja, hogy elsőnek indítottuk el a programot, vagy sem.
	 * 
	 * @return első indítás
	 */
	public boolean isFirstStart() {
		return firstStart;
	}

	/**
	 * Beállítja az első indítást.
	 * 
	 * @param firstStart
	 *            első indítás
	 */
	public void setFirstStart(boolean firstStart) {
		this.firstStart = firstStart;
	}

	/**
	 * Modena téma be van-e állítva.
	 * 
	 * @return beállított téma
	 */
	public boolean isModenaTheme() {
		return modenaTheme;
	}

	/**
	 * Modena téma beállítása.
	 * 
	 * @param modenaTheme
	 *            beállított téma
	 */
	public void setModenaTheme(boolean modenaTheme) {
		this.modenaTheme = modenaTheme;
	}

	/**
	 * Caspian téma be van-e állítva.
	 * 
	 * @return beállított téma
	 */
	public boolean isCaspianTheme() {
		return caspianTheme;
	}

	/**
	 * Caspian téma beállítása.
	 * 
	 * @param caspianTheme
	 *            beállított téma
	 */
	public void setCaspianTheme(boolean caspianTheme) {
		this.caspianTheme = caspianTheme;
	}

	/**
	 * AquaFX téma be van-e állítva.
	 * 
	 * @return beállított téma
	 */
	public boolean isAquaTheme() {
		return aquaTheme;
	}

	/**
	 * AquaFX téma beállítása.
	 * 
	 * @param aquaTheme
	 *            beállított téma
	 */
	public void setAquaTheme(boolean aquaTheme) {
		this.aquaTheme = aquaTheme;
	}

	/**
	 * Magyar nyelv be van-e állítva.
	 * 
	 * @return beállított nyelv
	 */
	public boolean isHungarianLang() {
		return hungarianLang;
	}

	/**
	 * Beállítja a nyelvet magyarra.
	 * 
	 * @param hungarianLang
	 *            magyar
	 */
	public void setHungarianLang(boolean hungarianLang) {
		this.hungarianLang = hungarianLang;
	}

	/**
	 * Angol nyelv be van-e állítva.
	 * 
	 * @return beállított nyelv
	 */
	public boolean isEnglishLang() {
		return englishLang;
	}

	/**
	 * Beállítja a nyelvet angolra.
	 * 
	 * @param englishLang
	 *            angol
	 */
	public void setEnglishLang(boolean englishLang) {
		this.englishLang = englishLang;
	}

}
