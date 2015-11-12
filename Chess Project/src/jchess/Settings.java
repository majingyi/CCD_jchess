package jchess;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;

import jchess.ui.JChessAboutBox;
import jchess.util.Constants;
import jchess.util.Player;

/**
 * Class representing game settings available for the current player
 */
public class Settings implements Serializable {

	private static final long	serialVersionUID	= -9058658425673782782L;

	private static Locale			locale						= Locale.US;
	public int								timeForGame;
	public boolean						runningChat;
	public boolean						runningGameClock;

	// tell us if player choose time 4 game or it's infinity
	public boolean						timeLimitSet;
	public boolean						upsideDown;

	public enum gameModes {

		newGame, loadGame
	}

	public gameModes	gameMode;
	public Player			playerWhite;
	public Player			playerBlack;

	public enum gameTypes {

		local, network
	}

	public gameTypes	gameType;
	public boolean		renderLabels	= true;

	public Settings() {
		// temporally
		this.playerWhite = new Player(Constants.EMPTY_STRING, "white");
		this.playerBlack = new Player(Constants.EMPTY_STRING, "black");
		this.timeLimitSet = false;

		gameMode = gameModes.newGame;
	}

	/**
	 * Method to get game time set by player
	 * 
	 * @return timeFofGame int with how long the game will leasts
	 */
	public int getTimeForGame() {
		return this.timeForGame;
	}

	public static void setLocale(Locale localization) {
		locale = localization;
	}

	public static String lang(String key) {
		String result = "";

		Locale.setDefault(locale);
		ResourceBundle.clearCache();
		ResourceBundle bundle = ResourceBundle.getBundle("jchess.resources.i18n.main");

		try {
			result = bundle.getString(key);
		} catch (java.util.MissingResourceException exc) {
			// ignore
		}

		if (result.equals("")) {
			bundle = ResourceBundle.getBundle("jchess.resources.JChessView");
		}

		try {
			result = bundle.getString(key);
		} catch (java.util.MissingResourceException exc) {
			// ignore
		}

		if (result.equals("")) {
			bundle = ResourceBundle.getBundle("jchess.resources.JChessAboutBox");
		}

		try {
			result = bundle.getString(key);
		} catch (java.util.MissingResourceException exc) {
			result = key;
		}

		return result;
	}

	public static Locale getLocale() {
		return locale;
	}

	public static Icon getIcon(String key) {
		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(jchess.JChessApp.class).getContext()
				.getResourceMap(JChessAboutBox.class);
		return resourceMap.getIcon(key);
	}
}
