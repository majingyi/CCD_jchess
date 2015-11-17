package jchess;

import java.io.Serializable;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jchess.core.Logging;
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

	public gameModes	gameMode		= null;
	public Player			playerWhite	= null;
	public Player			playerBlack	= null;

	public enum gameTypes {
		local
	}

	public gameTypes	gameType;
	public boolean		renderLabels	= true;

	public Settings() {
		// temporally
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

	public static Icon getIcon(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("jchess.resources.i18n.main");
		String imageName = bundle.getString(key);

		URL url = null;
		Icon img = null;
		try {
			String imageLink = "resources/" + imageName;
			url = JChessApp.class.getResource(imageLink);
			img = new ImageIcon(url);
		} catch (Exception e) {
			Logging.log("some error loading image!", e);
		}

		return img;
	}
}
