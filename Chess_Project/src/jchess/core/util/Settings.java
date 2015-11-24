package jchess.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

	private static String				whitePlayersName	= null;
	private static String				blackPlayersName	= null;

	private static Properties		properties				= null;

	private static List<Locale>	supportedLocales	= null;

	// prevent from instantiation
	private Settings() {
	}

	static {
		supportedLocales = new ArrayList<Locale>();
		supportedLocales.add(Locale.US);
		supportedLocales.add(Locale.GERMANY);
	}

	/**
	 * Method to get game time set by player
	 * 
	 * @return timeFofGame int with how long the game will leasts
	 */
	public static int getTimeForGame() {
		return timeForGame;
	}

	public static void setLocale(Locale localization) throws Exception {
		if (supportedLocales.contains(localization)) {
			locale = localization;
		} else {
			throw new Exception("Locale " + localization + " not supported.");
		}
	}

	public static Locale getLocale() {
		return locale;
	}

	public static void setTimeLimetSet(boolean timeLimit) {
		timeLimitSet = timeLimit;
	}

	public static void setTimeForGame(int limit) throws Exception {
		if (limit >= 0) {
			timeForGame = limit;
		} else {
			throw new Exception("Time limit needs to be greater or equal than 0.");
		}
	}

	public static boolean isTimeLimitSet() {
		return timeLimitSet;
	}

	public static void setWhitePlayersName(String name) throws Exception {
		if ((name != null) && (name.length() > 0)) {
			whitePlayersName = name;
		} else {
			throw new Exception("Players name is not allowed to be empty string.");
		}
	}

	public static void setBlackPlayersName(String name) throws Exception {
		if ((name != null) && (name.length() > 0)) {
			blackPlayersName = name;
		} else {
			throw new Exception("Players name is not allowed to be empty string.");
		}
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