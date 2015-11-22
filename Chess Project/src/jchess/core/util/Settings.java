package jchess.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.Properties;

import jchess.ui.Theme;
import jchess.ui.lang.Language;

/**
 * Class representing game settings available for the current player
 * 
 * TODO documentation
 */
public class Settings implements Serializable {

	private static final String	ACTIVE_THEME_KEY	= "active_theme";

	private static final long		serialVersionUID	= -9058658425673782782L;

	private static Locale				locale						= Locale.US;
	private static int					timeForGame				= 0;

	private static boolean			timeLimitSet			= false;

	// TODO check
	public enum gameModes {
		newGame, loadGame
	}

	private static gameModes	gameMode					= null;
	private static boolean		renderLabels			= true;
	private static String			whitePlayersName	= null;
	private static String			blackPlayersName	= null;

	private static Properties	properties				= null;

	// prevent from instantiation
	private Settings() {

	}

	/**
	 * Method to get game time set by player
	 * 
	 * @return timeFofGame int with how long the game will leasts
	 */
	public static int getTimeForGame() {
		return timeForGame;
	}

	public static void setLocale(Locale localization) {
		locale = localization;
	}

	public static Locale getLocale() {
		return locale;
	}

	public static void setTimeLimetSet(boolean timeLimit) {
		timeLimitSet = timeLimit;
	}

	public static void setTimeForGame(int limit) {
		timeForGame = limit;
	}

	public static boolean isTimeLimitSet() {
		return timeLimitSet;
	}

	public static boolean getRenderLabels() {
		return renderLabels;
	}

	public static void setGameMode(gameModes gMode) {
		gameMode = gMode;
	}

	public static gameModes getGameMode() {
		return gameMode;
	}

	public static void setWhitePlayersName(String name) {
		whitePlayersName = name;
	}

	public static void setBlackPlayersName(String name) {
		blackPlayersName = name;
	}

	public static String getBlackPlayersName() {
		return blackPlayersName;
	}

	public static String getWhitePlayersName() {
		return whitePlayersName;
	}

	private static void loadConfigFile() {
		properties = new Properties();
		File configFile = new File(Utils.getJarPath() + File.separator + "config.txt"); //$NON-NLS-1$

		if (configFile.exists()) {
			try {
				properties.load(new FileInputStream(configFile));
			} catch (java.io.IOException exc) {
				Logging.log(Language.getString("GUI.5"), exc); //$NON-NLS-1$
			}
		}
	}

	public static void storeConfigFile() {
		try {
			File outFile = new File(Utils.getJarPath() + File.separator + "config.txt"); //$NON-NLS-1$
			properties.store(new FileOutputStream(outFile), null);
		} catch (FileNotFoundException e) {
			Logging.log(Language.getString("GUI.7"), e); //$NON-NLS-1$
		} catch (IOException e) {
			Logging.log(Language.getString("GUI.8"), e); //$NON-NLS-1$
		}
	}

	public static void setProperty(String key, String value) {
		if (properties == null) {
			loadConfigFile();
		}
		properties.put(key, value);
	}

	public static String getActiveTheme() {
		String theme = getProperty(ACTIVE_THEME_KEY);
		return theme == null ? Theme.DEFAULT_THEME : theme;
	}

	private static String getProperty(String key) {
		if (properties == null) {
			loadConfigFile();
		}
		return (String) properties.get(key);
	}

	public static void setActiveTheme(String theme) {
		setProperty(ACTIVE_THEME_KEY, theme);
	}
}