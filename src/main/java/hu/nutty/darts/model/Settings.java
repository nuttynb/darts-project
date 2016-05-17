package hu.nutty.darts.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

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
	
	private Settings() {
		this.firstStart = true;
		this.modenaTheme = true;
		this.englishLang = true;
		this.caspianTheme = false;
		this.aquaTheme = false;
		this.hungarianLang = false;
	}

	public static Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}
		return instance;
	}

	public boolean isFirstStart() {
		return firstStart;
	}

	public void setFirstStart(boolean firstStart) {
		this.firstStart = firstStart;
	}

	public boolean isModenaTheme() {
		return modenaTheme;
	}

	public void setModenaTheme(boolean modenaTheme) {
		this.modenaTheme = modenaTheme;
	}

	public boolean isCaspianTheme() {
		return caspianTheme;
	}

	public void setCaspianTheme(boolean caspianTheme) {
		this.caspianTheme = caspianTheme;
	}

	public boolean isAquaTheme() {
		return aquaTheme;
	}

	public void setAquaTheme(boolean aquaTheme) {
		this.aquaTheme = aquaTheme;
	}

	public boolean isHungarianLang() {
		return hungarianLang;
	}

	public void setHungarianLang(boolean hungarianLang) {
		this.hungarianLang = hungarianLang;
	}

	public boolean isEnglishLang() {
		return englishLang;
	}

	public void setEnglishLang(boolean englishLang) {
		this.englishLang = englishLang;
	}
	
}
