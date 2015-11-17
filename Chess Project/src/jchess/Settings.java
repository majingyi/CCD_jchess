package jchess;

import java.io.Serializable;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jchess.core.Logging;
import jchess.resources.i18n.Language;
import jchess.util.Constants;
import jchess.util.Player;

/**
 * Class representing game settings available for the current player
 */
public class Settings implements Serializable {

	private static final long	serialVersionUID	= -9058658425673782782L;

	private static Locale			locale						= Locale.US;
	private int								timeForGame;
	private boolean						runningGameClock;

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

	// TODO move to ImageFactory
	public static Icon getIcon(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("jchess.resources.i18n.main"); //$NON-NLS-1$
		String imageName = bundle.getString(key);

		URL url = null;
		Icon img = null;
		try {
			String imageLink = "resources/" + imageName; //$NON-NLS-1$
			url = JChessApp.class.getResource(imageLink);
			img = new ImageIcon(url);
		} catch (Exception e) {
			Logging.log(Language.getString("Settings.2"), e); //$NON-NLS-1$
		}

		return img;
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