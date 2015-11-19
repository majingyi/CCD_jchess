package jchess;

import java.io.Serializable;
import java.util.Locale;

import jchess.util.Constants;
import jchess.util.Player;

/**
 * Class representing game settings available for the current player
 */
public class Settings implements Serializable {

	private static final long	serialVersionUID	= -9058658425673782782L;

	private static Locale			locale						= Locale.US;
	private int								timeForGame				= 0;

	// tell us if player choose time 4 game or it's infinity
	private boolean						timeLimitSet;
	private boolean						upsideDown;

	public enum gameModes {
		newGame, loadGame
	}

	public gameModes	gameMode		= null;
	public Player			playerWhite	= null;
	public Player			playerBlack	= null;

	public enum gameTypes {
		local
	}

	public gameTypes	gameType;
	public boolean		renderLabels	= true;

	public Settings() {
		this.playerWhite = new Player(Constants.EMPTY_STRING, Player.colors.white);
		this.playerBlack = new Player(Constants.EMPTY_STRING, Player.colors.black);
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

	public static Locale getLocale() {
		return locale;
	}

	public boolean isUpsideDown() {
		return upsideDown;
	}

	public void setUpsideDown(boolean upsideDown) {
		this.upsideDown = upsideDown;
	}

	public void setTimeLimetSet(boolean timeLimit) {
		this.timeLimitSet = timeLimit;
	}

	public void setTimeForGame(int limit) {
		this.timeForGame = limit;
	}

	public boolean isTimeLimitSet() {
		return timeLimitSet;
	}
}