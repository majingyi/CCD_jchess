package jchess.core.util;

import java.io.Serializable;
import java.util.Locale;

/**
 * Class representing game settings available for the current player
 * 
 * TODO documentation
 */
public class Settings implements Serializable {

	private static final long	serialVersionUID	= -9058658425673782782L;

	private static Locale			locale						= Locale.US;
	private static int				timeForGame				= 0;

	private static boolean		timeLimitSet			= false;

	// TODO check
	public enum gameModes {
		newGame, loadGame
	}

	private static gameModes	gameMode					= null;
	private static boolean		renderLabels			= true;
	private static String			whitePlayersName	= null;
	private static String			blackPlayersName	= null;

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

}