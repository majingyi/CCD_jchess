package jchess.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import jchess.core.util.Player.PlayerColor;
import jchess.ui.Theme;
import jchess.ui.lang.Language;

/**
 * @author Maurice
 * 
 * Class representing game settings available for the current player
 * 
 */
public class Settings implements Serializable {

	private static final String											ACTIVE_THEME_KEY	= "active_theme";

	private static final long												serialVersionUID	= -9058658425673782782L;

	private static Locale														locale						= Locale.US;
	private static int															timeForGame				= 0;

	private static boolean													timeLimitSet			= false;

	private static Map<Player.PlayerColor, String>	playerNames				= new HashMap<Player.PlayerColor, String>();

	private static Properties												properties				= null;
	private static List<Locale>											supportedLocales	= null;

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

	/**
	 * Sets a localization.
	 * 
	 * Method checks, that the given localization is supported by the program, and throws an exception if not.
	 * 
	 * @param localization
	 * @throws Exception
	 */
	public static void setLocale(Locale localization) throws Exception {
		if (supportedLocales.contains(localization)) {
			locale = localization;
		} else {
			throw new Exception("Locale " + localization + " not supported.");
		}
	}

	/**
	 * Get currently selected locale.
	 * 
	 * @return
	 */
	public static Locale getLocale() {
		return locale;
	}

	/**
	 * Set, if a time limit was specified for the game.
	 * 
	 * @param timeLimit
	 */
	public static void setTimeLimitSet(boolean timeLimit) {
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

	/**
	 * Sets the name for the player of given color.
	 * 
	 * @param name
	 * @param color
	 * @throws Exception
	 */
	public static void addPlayerName(String name, PlayerColor color) throws Exception {
		if ((name != null) && (name.length() > 0)) {
			playerNames.put(color, name);
		} else {
			throw new Exception("Players name is not allowed to be empty string.");
		}
	}

	/**
	 * Set name for the white player
	 * 
	 * @param color
	 * @deprecated Use addPlayerName instead.
	 */
	@Deprecated
	public static void setWhitePlayersName(String name) throws Exception {
		addPlayerName(name, PlayerColor.WHITE);
	}

	/**
	 * Set name for the black player
	 * 
	 * @param color
	 * @deprecated Use addPlayerName instead.
	 */
	@Deprecated
	public static void setBlackPlayersName(String name) throws Exception {
		addPlayerName(name, PlayerColor.BLACK);
	}

	public static String getPlayerNameForColor(PlayerColor color) {
		return playerNames.get(color);
	}

	/**
	 * 
	 * @return name of the black player
	 * @deprecated Use getPlayerNameForColor instead.
	 */
	@Deprecated
	public static String getBlackPlayersName() {
		return getPlayerNameForColor(PlayerColor.BLACK);
	}

	/**
	 * 
	 * @return name of the white player
	 * @deprecated Use getPlayerNameForColor instead.
	 */
	@Deprecated
	public static String getWhitePlayersName() {
		return getPlayerNameForColor(PlayerColor.WHITE);
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

	/**
	 * Triggers the actual writing of the config file.
	 */
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

	/**
	 * A freely defined property can be added here. 
	 * This property will be saved in the properties file.
	 * 
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key, String value) {
		if (properties == null) {
			loadConfigFile();
		}
		properties.put(key, value);
	}

	/**
	 * 
	 * @return The currently selected theme.
	 */
	public static String getActiveTheme() {
		String theme = getProperty(ACTIVE_THEME_KEY);
		return theme == null ? Theme.DEFAULT_THEME : theme;
	}

	/**
	 * Gets the property from the properties file, which is identified by the given key.
	 * 
	 * If this property does not exist, null is returned.
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		if (properties == null) {
			loadConfigFile();
		}
		return (String) properties.get(key);
	}

	public static void setActiveTheme(String theme) {
		setProperty(ACTIVE_THEME_KEY, theme);
	}
}